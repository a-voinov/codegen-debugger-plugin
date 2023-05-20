package av.debugger.codegen;

import av.debugger.codegen.codemodel.ClassModel;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.Nullable;
import av.debugger.codegen.codemodel.FieldModel;

import java.beans.Introspector;
import java.util.stream.Collectors;

import static av.debugger.codegen.codemodel.FieldExtraType.ARRAY;
import static av.debugger.codegen.codemodel.FieldExtraType.ARRAY_LIST_JAVA_UTIL_ARRAYS;

public final class CodeGenerator {
    private static final Logger log = Logger.getInstance(CodeGenerator.class);

    private static final String STRING = "String";
    private static final String CHAR = "char";
    private static final String BOXED_CHAR = "Character";
    private static final String FLOAT = "float";
    private static final String BOXED_SHORT = "Short";
    private static final String SHORT = "short";
    private static final String BOXED_FLOAT = "Float";
    private static final String LONG = "long";
    private static final String BOXED_LONG = "Long";
    private static final String NULL = "null";

    private CodeGenerator() {
    }

    @Nullable
    public static String generate(ClassModel classModel) {
        return finalize(generateImpl(classModel, null));
    }

    @Nullable
    private static String generateImpl(ClassModel classModel) {
        return generateImpl(classModel, null);
    }

    @Nullable
    private static String generateImpl(ClassModel classModel, String varName) {
        if (classModel == null) {
            return null;
        }

        if (classModel.isUsedInCodegen()) {
            // variable is already generated, no need to generate constructor/setters again
            return "";
        }

        if (!classModel.hasDefaultConstructor()) {
            log.info("Object [" + classModel.getClassName() + "] must have a default constructor");
            return null;
        }

        final String className = classModel.getClassName();
        final String classVarName = varName == null ? classModel.getVariableName() : varName;
        final StringBuilder codeStringBuilder = new StringBuilder();
        codeStringBuilder.append(System.lineSeparator())
                .append(className)
                .append(" ")
                .append(classVarName)
                .append(" = new ")
                .append(className)
                .append("();")
                .append(System.lineSeparator());

        classModel.setUsedInCodegen(true);

        for (FieldModel field : classModel.getFields()) {
            if (!field.isPrimitiveField()) {
                if (field.getFieldExtraType() == ARRAY) {
                    processArray(field, classVarName, codeStringBuilder.append(System.lineSeparator()));
                } else if (field.getFieldExtraType() == ARRAY_LIST_JAVA_UTIL_ARRAYS) {
                    processArrayListJavaUtil(field, classVarName, codeStringBuilder.append(System.lineSeparator()));
                } else {
                    codeStringBuilder
                            .append(System.lineSeparator())
                            .append(generateImpl(field.getClassModel()))
                            .append(System.lineSeparator());
                    processObject(field, classVarName, codeStringBuilder);
                }
            } else {
                processPrimitive(field, classVarName, codeStringBuilder);
            }
        }

        return codeStringBuilder.toString();
    }

    private static void processArrayListJavaUtil(FieldModel field, String classVarName, StringBuilder codeStringBuilder) {
        if (!field.getValues().isEmpty() && !field.getValues().get(0).isPrimitiveField()) {
            processArrayListJavaUtilOfObjects(field, classVarName, codeStringBuilder);
        } else {
            processArrayListJavaUtilOfPrimitives(field, classVarName, codeStringBuilder);
        }
    }

    private static void processArrayListJavaUtilOfObjects(
            FieldModel field,
            String classVarName,
            StringBuilder codeStringBuilder
    ) {
        final StringBuilder setterBody = new StringBuilder();
        setterBody
                .append("Arrays.asList(")
                .append(field.getValues()
                                .stream()
                                .peek(el -> codeStringBuilder.append(generateImpl(el.getClassModel(), el.getName())))
                                .map(FieldModel::getName)
                                .collect(Collectors.joining(", ")));
        process(field, classVarName, codeStringBuilder, setterBody.append(")").toString());
        codeStringBuilder.append(System.lineSeparator());
    }

    private static void processArrayListJavaUtilOfPrimitives(
            FieldModel field,
            String classVarName,
            StringBuilder codeStringBuilder
    ) {
        final StringBuilder setterBody = new StringBuilder();
        setterBody
                .append("Arrays.asList(")
                .append(field.getValues()
                                .stream()
                                .map(CodeGenerator::wrapValueExplicit)
                                .collect(Collectors.joining(", ")));
        process(field, classVarName, codeStringBuilder, setterBody.append(")").toString());
        codeStringBuilder.append(System.lineSeparator());
    }

    private static void processArray(FieldModel field, String classVarName, StringBuilder codeStringBuilder) {
        if (!field.getValues().isEmpty() && !field.getValues().get(0).isPrimitiveField()) {
            processArrayOfObjects(field, classVarName, codeStringBuilder);
        } else {
            processArrayOfPrimitives(field, classVarName, codeStringBuilder);
        }
    }

    private static void processArrayOfObjects(FieldModel field, String classVarName, StringBuilder codeStringBuilder) {
        final StringBuilder setterBody = new StringBuilder();
        setterBody
                .append("new ")
                .append(field.getType())
                .append("[]{")
                .append(field.getValues().stream()
                            .peek(el -> codeStringBuilder.append(generateImpl(el.getClassModel(), el.getName())))
                            .map(FieldModel::getName)
                            .collect(Collectors.joining(", ")));
        process(field, classVarName, codeStringBuilder, setterBody.append("}").toString());
        codeStringBuilder.append(System.lineSeparator());
    }

    private static void processArrayOfPrimitives(FieldModel field, String classVarName, StringBuilder codeStringBuilder) {
        final StringBuilder setterBody = new StringBuilder();
        setterBody
                .append("new ")
                .append(field.getType())
                .append("[]{")
                .append(field.getValues()
                        .stream()
                        .map(CodeGenerator::wrapValue)
                        .collect(Collectors.joining(", ")));
        process(field, classVarName, codeStringBuilder, setterBody.append("}").toString());
        codeStringBuilder.append(System.lineSeparator());
    }

    private static void processObject(FieldModel field, String classVarName, StringBuilder codeStringBuilder) {
        process(field, classVarName, codeStringBuilder, Introspector.decapitalize(field.getClassModel().getClassName()));
    }

    private static void processPrimitive(FieldModel field, String classVarName, StringBuilder codeStringBuilder) {
        process(field, classVarName, codeStringBuilder, wrapValue(field));
    }

    private static void process(FieldModel field, String classVarName, StringBuilder codeStringBuilder, String setterBody) {
        if (field.hasSetter()) {
            codeStringBuilder
                    .append(classVarName)
                    .append(".")
                    .append(field.getSetterName())
                    .append("(")
                    .append(setterBody)
                    .append(");")
                    .append(System.lineSeparator());
            log.debug(field.toString());
        } else {
            log.info("Setter wasn't found for field [" + field.getName() + "]");
        }
    }

    private static String wrapValue(FieldModel fieldModel) {
        return wrapValue(fieldModel, false);
    }

    private static String wrapValueExplicit(FieldModel fieldModel) {
        return wrapValue(fieldModel, true);
    }

    private static String wrapValue(FieldModel fieldModel, boolean explicitField) {
        if (fieldModel.getValue() == null || fieldModel.getValue().equals(NULL)) {
            return NULL;
        }
        switch (fieldModel.getType()) {
            case STRING:
                return "\"" + fieldModel.getValue() + "\"";
            case CHAR:
            case BOXED_CHAR:
                return "'" + fieldModel.getValue() + "'";
            case FLOAT:
            case BOXED_FLOAT:
                return fieldModel.getValue() + "f";
            case LONG:
            case BOXED_LONG:
                return fieldModel.getValue() + "L";
            case SHORT:
            case BOXED_SHORT:
                return explicitField ? "(short)" + fieldModel.getValue() : fieldModel.getValue();
            default:
                return fieldModel.getValue();
        }
    }

    /**
     * Replace double line separators with single line separator.
     * Trim first and last line break, if exists.
     */
    private static String finalize(String input) {
        if (input != null) {
            final String br = System.lineSeparator();
            input = input.replaceAll("(" + br + "){2,}", br + br);
            final int start = input.startsWith(br) ? br.length() : 0;
            final int end = input.endsWith(br) ? input.lastIndexOf(br) : input.length();
            return input.substring(start, end);
        } else {
            return null;
        }
    }
}
