package av.debugger.codegen;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.Strings;
import com.sun.jdi.*;
import org.jetbrains.annotations.Nullable;
import av.debugger.codegen.codemodel.ClassModel;
import av.debugger.codegen.codemodel.FieldModel;
import av.debugger.codegen.codemodel.CodeModelCache;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;

import static av.debugger.codegen.codemodel.FieldExtraType.ARRAY;
import static av.debugger.codegen.codemodel.FieldExtraType.ARRAY_LIST_JAVA_UTIL_ARRAYS;

public final class JdiObjectAnalyzer {
    private static final Logger log = Logger.getInstance(JdiObjectAnalyzer.class);

    private final CodeModelCache classModelCache = new CodeModelCache();

    @Nullable
    public ClassModel createClassModel(ObjectReference ref) {
        if (ref == null ||
                ref instanceof ClassLoaderReference ||
                ref instanceof ClassObjectReference ||
                ref instanceof ModuleReference ||
                ref instanceof ThreadReference ||
                ref instanceof ThreadGroupReference
        ) {
            log.debug("Node [" + ref + "] is invalid object");
            return null;
        }

        final ClassModel cachedById = classModelCache.getById(ref.uniqueID());
        if (cachedById != null) {
            return cachedById;
        }

        final ReferenceType refType = ref.referenceType();
        final ClassModel classModel = new ClassModel();
        final String fullClassName = refType.name();
        classModel.setClassName(fullClassName.substring(fullClassName.lastIndexOf('.') + 1));
        classModel.setVariableName(Introspector.decapitalize(classModel.getClassName()));
        classModel.setHasDefaultConstructor(
                refType.methods().stream().filter(Method::isConstructor).anyMatch(JdiObjectAnalyzer::noArgs));

        classModelCache.put(ref.uniqueID(), classModel);

        final List<FieldModel> fieldModels = new ArrayList<>();
        for (Field field : refType.fields()) {
            fieldModels.add(createFieldModel(field.name(), ref.getValue(field)));
        }

        classModel.setFields(fieldModels);

        return classModel;
    }

    private FieldModel createFieldModel(String fieldName, Value value) {
        final FieldModel fieldModel = new FieldModel();
        fieldModel.setName(fieldName);
        if (value != null) {
            fieldModel.setType(getType(value.type().name()));
        }
        if (value instanceof ObjectReference) {
            if (isBoxedPrimitive(value)) {
                final ObjectReference valRef = (ObjectReference) value;
                final Field innerPrimitiveField = valRef.referenceType().fieldByName("value");
                fieldModel.setValue(valRef.getValue(innerPrimitiveField).toString());
            } else if (value instanceof StringReference) {
                fieldModel.setValue(value.toString().replace("\"", ""));
            } else if (value instanceof ArrayReference) {
                fieldModel.setFieldExtraType(ARRAY);
                addFieldValues(fieldName, (ArrayReference) value, fieldModel);
            } else {
                final ObjectReference valueRef = (ObjectReference) value;
                if (valueRef.referenceType().signature().equals("Ljava/util/Arrays$ArrayList;")) {
                    fieldModel.setFieldExtraType(ARRAY_LIST_JAVA_UTIL_ARRAYS);
                    final Field innerArray = valueRef.referenceType().fieldByName("a");
                    final Value innerArrayValue = valueRef.getValue(innerArray);
                    addFieldValues(fieldName, (ArrayReference) innerArrayValue, fieldModel);
                } else {
                    final ClassModel fieldClassModel = createClassModel(valueRef);
                    fieldModel.setClassModel(fieldClassModel);
                }
            }
        } else if (value instanceof PrimitiveValue) {
            fieldModel.setValue(value.toString());
        } else if (value == null) {
            fieldModel.setValue("null");
        } else {
            fieldModel.setValue("?");
            log.debug("Unexpected type of field " + fieldName);
        }
        fieldModel.setSetterName(getSetterName(fieldName));
        return fieldModel;
    }

    private void addFieldValues(String fieldName, ArrayReference arrayReference, FieldModel fieldModel) {
        int index = 0;
        for (Value value : arrayReference.getValues()) {
            fieldModel.getValues().add(createFieldModel(fieldName + "_" + index++, value));
        }
    }

    private static boolean isBoxedPrimitive(Value value) {
        switch (value.type().name()) {
            case "java.lang.Short":
            case "java.lang.Integer":
            case "java.lang.Long":
            case "java.lang.Float":
            case "java.lang.Double":
            case "java.lang.Character":
                return true;
            default:
                return false;
        }
    }

    private static boolean noArgs(Method method) {
        try {
            return method.arguments().size() == 0;
        } catch (AbsentInformationException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getType(String type) {
        return type.substring(type.lastIndexOf(".") + 1).replace("[]","");
    }

    private static String getSetterName(String fieldName) {
        return "set" + Strings.capitalize(fieldName);
    }
}
