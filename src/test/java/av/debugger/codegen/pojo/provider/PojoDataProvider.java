package av.debugger.codegen.pojo.provider;

import av.debugger.codegen.pojo.*;
import com.intellij.openapi.util.text.Strings;
import av.debugger.codegen.codemodel.ClassModel;
import av.debugger.codegen.codemodel.FieldModel;
import av.debugger.codegen.pojo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static av.debugger.codegen.codemodel.FieldExtraType.ARRAY;
import static av.debugger.codegen.codemodel.FieldExtraType.ARRAY_LIST_JAVA_UTIL_ARRAYS;

public final class PojoDataProvider extends AbstractDataProvider {

    private PojoDataProvider() {
    }

    public static SimpleSetterPojo getSimpleSetterPojo() {
        SimpleSetterPojo setterPojo = new SimpleSetterPojo();
        setterPojo.setShortField(Short.MAX_VALUE);
        setterPojo.setIntField(Integer.MAX_VALUE);
        setterPojo.setLongField(Long.MAX_VALUE);
        setterPojo.setFloatField(Float.MAX_VALUE);
        setterPojo.setDoubleField(Double.MAX_VALUE);
        setterPojo.setCharField('A');
        setterPojo.setStringField("testing...");
        return setterPojo;
    }

    public static ClassModel getSimpleSetterPojoClassModel() {
        ClassModel classModel = new ClassModel();
        classModel.setHasDefaultConstructor(true);
        classModel.setClassName("SimpleSetterPojo");
        classModel.setVariableName("simpleSetterPojo");

        FieldModel shortField = getFieldModel("shortField", "short", String.valueOf(Short.MAX_VALUE));
        FieldModel intField = getFieldModel("intField", "int", String.valueOf(Integer.MAX_VALUE));
        FieldModel longField = getFieldModel("longField", "long", String.valueOf(Long.MAX_VALUE));
        FieldModel floatField = getFieldModel("floatField", "float", String.valueOf(Float.MAX_VALUE));
        FieldModel doubleField = getFieldModel("doubleField", "double", String.valueOf(Double.MAX_VALUE));
        FieldModel charField = getFieldModel("charField", "char", "A");
        FieldModel stringField = getFieldModel("stringField", "String", "testing...");

        classModel.setFields(List.of(shortField, intField, longField, floatField, doubleField, charField, stringField));
        return classModel;
    }

    public static BoxedSetterPojo getBoxedSetterPojo() {
        BoxedSetterPojo setterPojo = new BoxedSetterPojo();
        setterPojo.setShortField(Short.MAX_VALUE);
        setterPojo.setIntegerField(Integer.MAX_VALUE);
        setterPojo.setLongField(Long.MAX_VALUE);
        setterPojo.setFloatField(Float.MAX_VALUE);
        setterPojo.setDoubleField(Double.MAX_VALUE);
        setterPojo.setCharacterField('A');
        return setterPojo;
    }

    public static ClassModel getBoxedSetterPojoClassModel() {
        ClassModel classModel = new ClassModel();
        classModel.setHasDefaultConstructor(true);
        classModel.setClassName("BoxedSetterPojo");
        classModel.setVariableName("boxedSetterPojo");

        FieldModel shortField = getFieldModel("shortField", "Short", String.valueOf(Short.MAX_VALUE));
        FieldModel intField = getFieldModel("integerField", "Integer", String.valueOf(Integer.MAX_VALUE));
        FieldModel longField = getFieldModel("longField", "Long", String.valueOf(Long.MAX_VALUE));
        FieldModel floatField = getFieldModel("floatField", "Float", String.valueOf(Float.MAX_VALUE));
        FieldModel doubleField = getFieldModel("doubleField", "Double", String.valueOf(Double.MAX_VALUE));
        FieldModel charField = getFieldModel("characterField", "Character", "A");

        classModel.setFields(List.of(shortField, intField, longField, floatField, doubleField, charField));
        return classModel;
    }

    public static SimpleSetterArrayPojo getSimpleSetterArrayPojo() {
        SimpleSetterArrayPojo pojo = new SimpleSetterArrayPojo();
        pojo.setShortArray(new short[]{1, 2, 3});
        pojo.setIntArray(new int[]{1, 2, 3});
        pojo.setLongArray(new long[]{1L, 2L, 3L});
        pojo.setFloatArray(new float[]{0.1f, 0.2f, 0.3f});
        pojo.setDoubleArray(new double[]{0.1, 0.2, 0.3});
        pojo.setCharArray(new char[]{'a', 'b', 'c'});
        pojo.setStringArray(new String[]{"alpha", "beta", "gamma", "delta"});
        return pojo;
    }

    public static ClassModel getSimpleSetterArrayPojoClassModel() {
        String[] valuesNumericInt = new String[]{"1", "2", "3"};
        FieldModel shortArrayField = getArrayFieldModel("shortArray", "short", valuesNumericInt);
        FieldModel intArrayField = getArrayFieldModel("intArray", "int", valuesNumericInt);
        FieldModel longArrayField = getArrayFieldModel("longArray", "long", valuesNumericInt);

        String[] valuesNumericDouble = {"0.1", "0.2", "0.3"};
        FieldModel floatArrayField = getArrayFieldModel("floatArray", "float", valuesNumericDouble);
        FieldModel doubleArrayField = getArrayFieldModel("doubleArray", "double", valuesNumericDouble);

        String[] valuesChar = {"a", "b", "c"};
        FieldModel charArrayField = getArrayFieldModel("charArray", "char", valuesChar);

        String[] valuesString = {"alpha", "beta", "gamma", "delta"};
        FieldModel stringArrayField = getArrayFieldModel("stringArray", "String", valuesString);

        ClassModel classModel = new ClassModel();
        classModel.setHasDefaultConstructor(true);
        classModel.setClassName("SimpleSetterArrayPojo");
        classModel.setVariableName("simpleSetterArrayPojo");
        classModel.setFields(List.of(
                shortArrayField,
                intArrayField,
                longArrayField,
                floatArrayField,
                doubleArrayField,
                charArrayField,
                stringArrayField)
        );
        return classModel;
    }

    public static BoxedSetterArrayPojo getBoxedSetterArrayPojo() {
        BoxedSetterArrayPojo pojo = new BoxedSetterArrayPojo();
        pojo.setShortArray(new Short[]{1, 2, 3});
        pojo.setIntegerArray(new Integer[]{1, 2, 3});
        pojo.setLongArray(new Long[]{1L, 2L, 3L});
        pojo.setFloatArray(new Float[]{0.1f, 0.2f, 0.3f});
        pojo.setDoubleArray(new Double[]{0.1, 0.2, 0.3});
        pojo.setCharacterArray(new Character[]{'a', 'b', 'c'});
        return pojo;
    }

    public static ClassModel getBoxedSetterArrayPojoClassModel() {
        String[] valuesNumericInt = new String[]{"1", "2", "3"};
        FieldModel shortArrayField = getArrayFieldModel("shortArray", "Short", valuesNumericInt);
        FieldModel integerArrayField = getArrayFieldModel("integerArray", "Integer", valuesNumericInt);
        FieldModel longArrayField = getArrayFieldModel("longArray", "Long", valuesNumericInt);

        String[] valuesNumericDouble = {"0.1", "0.2", "0.3"};
        FieldModel floatArrayField = getArrayFieldModel("floatArray", "Float", valuesNumericDouble);
        FieldModel doubleArrayField = getArrayFieldModel("doubleArray", "Double", valuesNumericDouble);

        String[] valuesChar = {"a", "b", "c"};
        FieldModel characterArrayField = getArrayFieldModel("characterArray", "Character", valuesChar);

        ClassModel classModel = new ClassModel();
        classModel.setHasDefaultConstructor(true);
        classModel.setClassName("BoxedSetterArrayPojo");
        classModel.setVariableName("boxedSetterArrayPojo");
        classModel.setFields(List.of(
                shortArrayField,
                integerArrayField,
                longArrayField,
                floatArrayField,
                doubleArrayField,
                characterArrayField)
        );
        return classModel;
    }

    public static SuperSetterPojo getSuperSetterPojo() {
        NestedSetterPojo nestedSetterPojo = new NestedSetterPojo();
        nestedSetterPojo.setIntField(1);

        SuperSetterPojo pojo = new SuperSetterPojo();
        pojo.setNestedObject(nestedSetterPojo);
        pojo.setStringField("some field");
        return pojo;
    }

    public static ClassModel getSuperSetterPojoClassModel() {
        ClassModel nestedClassModel = new ClassModel();
        nestedClassModel.setHasDefaultConstructor(true);
        nestedClassModel.setClassName("NestedSetterPojo");
        nestedClassModel.setVariableName("nestedSetterPojo");
        FieldModel intFieldModel = getFieldModel("intField", "int", "1");
        nestedClassModel.getFields().add(intFieldModel);
        FieldModel nestedClassField = getFieldModel("nestedObject", "NestedSetterPojo", null);
        nestedClassField.setClassModel(nestedClassModel);

        ClassModel superClassModel = new ClassModel();
        superClassModel.setHasDefaultConstructor(true);
        superClassModel.setClassName("SuperSetterPojo");
        superClassModel.setVariableName("superSetterPojo");
        superClassModel.getFields().add(nestedClassField);
        FieldModel stringFieldModel = getFieldModel("stringField", "String", "some field");
        superClassModel.getFields().add(stringFieldModel);
        return superClassModel;
    }

    public static SuperSetterArrayPojo getSuperSetterArrayPojo() {
        NestedSetterPojo nestedSetterPojo1 = new NestedSetterPojo();
        nestedSetterPojo1.setIntField(1);
        NestedSetterPojo nestedSetterPojo2 = new NestedSetterPojo();
        nestedSetterPojo2.setIntField(2);
        NestedSetterPojo nestedSetterPojo3 = new NestedSetterPojo();
        nestedSetterPojo3.setIntField(3);
        SuperSetterArrayPojo pojo = new SuperSetterArrayPojo();
        pojo.setNestedObjectArray(new NestedSetterPojo[]{nestedSetterPojo1, nestedSetterPojo2, nestedSetterPojo3});
        pojo.setStringField("some field");
        return pojo;
    }

    public static ClassModel getSuperSetterArrayPojoClassModel() {
        ClassModel nestedClassModel1 = new ClassModel();
        nestedClassModel1.setHasDefaultConstructor(true);
        nestedClassModel1.setClassName("NestedSetterPojo");
        nestedClassModel1.setVariableName("nestedSetterPojo");
        FieldModel intFieldModel1 = getFieldModel("intField", "int", "1");
        nestedClassModel1.getFields().add(intFieldModel1);
        FieldModel nestedClassField1 = getFieldModel("nestedObjectArray_0", "NestedSetterPojo", null);
        nestedClassField1.setClassModel(nestedClassModel1);

        ClassModel nestedClassModel2 = new ClassModel();
        nestedClassModel2.setHasDefaultConstructor(true);
        nestedClassModel2.setClassName("NestedSetterPojo");
        nestedClassModel2.setVariableName("nestedSetterPojo");
        FieldModel intFieldModel2 = getFieldModel("intField", "int", "2");
        nestedClassModel2.getFields().add(intFieldModel2);
        FieldModel nestedClassField2 = getFieldModel("nestedObjectArray_1", "NestedSetterPojo", null);
        nestedClassField2.setClassModel(nestedClassModel2);

        ClassModel nestedClassModel3 = new ClassModel();
        nestedClassModel3.setHasDefaultConstructor(true);
        nestedClassModel3.setClassName("NestedSetterPojo");
        nestedClassModel3.setVariableName("nestedSetterPojo");
        FieldModel intFieldModel3 = getFieldModel("intField", "int", "3");
        nestedClassModel3.getFields().add(intFieldModel3);
        FieldModel nestedClassField3 = getFieldModel("nestedObjectArray_2", "NestedSetterPojo", null);
        nestedClassField3.setClassModel(nestedClassModel3);

        FieldModel arrayField = new FieldModel();
        arrayField.setFieldExtraType(ARRAY);
        arrayField.setName("nestedObjectArray");
        arrayField.setSetterName("setNestedObjectArray");
        arrayField.setType("NestedSetterPojo");
        arrayField.setValues(List.of(nestedClassField1, nestedClassField2, nestedClassField3));

        ClassModel superClassModel = new ClassModel();
        superClassModel.setHasDefaultConstructor(true);
        superClassModel.setClassName("SuperSetterArrayPojo");
        superClassModel.setVariableName("superSetterArrayPojo");
        superClassModel.getFields().add(arrayField);
        FieldModel stringFieldModel = getFieldModel("stringField", "String", "some field");
        superClassModel.getFields().add(stringFieldModel);
        return superClassModel;
    }

    public static SimpleSetterListPojo getSimpleSetterArrayListJavaUtilPojo() {
        SimpleSetterListPojo pojo = new SimpleSetterListPojo();
        pojo.setShortList(Arrays.asList((short)1, (short)2, (short)3));
        pojo.setIntegerList(Arrays.asList(1, 2, 3));
        pojo.setLongList(Arrays.asList(1L, 2L, 3L));
        pojo.setFloatList(Arrays.asList(0.1f, 0.2f, 0.3f));
        pojo.setDoubleList(Arrays.asList(0.1, 0.2, 0.3));
        pojo.setCharacterList(Arrays.asList('a', 'b', 'c'));
        pojo.setStringList(Arrays.asList("alpha", "beta", "gamma", "delta"));
        return pojo;
    }

    public static ClassModel getSimpleSetterArrayListJavaUtilClassModel() {
        String[] valuesNumericInt = new String[]{"1", "2", "3"};
        FieldModel shortListField = getArrayListJavaUtilFieldModel("shortList", "Short", valuesNumericInt);
        FieldModel intListField = getArrayListJavaUtilFieldModel("integerList", "Integer", valuesNumericInt);
        FieldModel longListField = getArrayListJavaUtilFieldModel("longList", "Long", valuesNumericInt);

        String[] valuesNumericDouble = {"0.1", "0.2", "0.3"};
        FieldModel floatListField = getArrayListJavaUtilFieldModel("floatList", "Float", valuesNumericDouble);
        FieldModel doubleListField = getArrayListJavaUtilFieldModel("doubleList", "Double", valuesNumericDouble);

        String[] valuesChar = {"a", "b", "c"};
        FieldModel charListField = getArrayListJavaUtilFieldModel("characterList", "Character", valuesChar);

        String[] valuesString = {"alpha", "beta", "gamma", "delta"};
        FieldModel stringListField = getArrayListJavaUtilFieldModel("stringList", "String", valuesString);

        ClassModel classModel = new ClassModel();
        classModel.setHasDefaultConstructor(true);
        classModel.setClassName("SimpleSetterListPojo");
        classModel.setVariableName("simpleSetterListPojo");
        classModel.setFields(List.of(
                shortListField,
                intListField,
                longListField,
                floatListField,
                doubleListField,
                charListField,
                stringListField)
        );
        return classModel;
    }

    public static SuperSetterListPojo getSuperSetterArrayListJavaUtilPojo() {
        NestedSetterPojo nestedSetterPojo_1 = new NestedSetterPojo();
        nestedSetterPojo_1.setIntField(1);
        NestedSetterPojo nestedSetterPojo_2 = new NestedSetterPojo();
        nestedSetterPojo_2.setIntField(2);
        SuperSetterListPojo pojo = new SuperSetterListPojo();
        pojo.setPojoList(Arrays.asList(nestedSetterPojo_1, nestedSetterPojo_2));
        return pojo;
    }

    public static ClassModel getSuperSetterArrayListJavaUtilClassModel() {
        ClassModel nestedClassModel1 = new ClassModel();
        nestedClassModel1.setHasDefaultConstructor(true);
        nestedClassModel1.setClassName("NestedSetterPojo");
        nestedClassModel1.setVariableName("nestedSetterPojo");
        FieldModel intFieldModel1 = getFieldModel("intField", "int", "1");
        nestedClassModel1.getFields().add(intFieldModel1);
        FieldModel nestedClassField1 = getFieldModel("pojoList_0", "NestedSetterPojo", null);
        nestedClassField1.setClassModel(nestedClassModel1);

        ClassModel nestedClassModel2 = new ClassModel();
        nestedClassModel2.setHasDefaultConstructor(true);
        nestedClassModel2.setClassName("NestedSetterPojo");
        nestedClassModel2.setVariableName("nestedSetterPojo");
        FieldModel intFieldModel2 = getFieldModel("intField", "int", "2");
        nestedClassModel2.getFields().add(intFieldModel2);
        FieldModel nestedClassField2 = getFieldModel("pojoList_1", "NestedSetterPojo", null);
        nestedClassField2.setClassModel(nestedClassModel2);

        FieldModel listField = new FieldModel();
        listField.setFieldExtraType(ARRAY_LIST_JAVA_UTIL_ARRAYS);
        listField.setName("pojoList");
        listField.setSetterName("setPojoList");
        listField.setType("Arrays$ArrayList");
        listField.setValues(List.of(nestedClassField1, nestedClassField2));

        ClassModel classModel = new ClassModel();
        classModel.setHasDefaultConstructor(true);
        classModel.setClassName("SuperSetterListPojo");
        classModel.setVariableName("superSetterListPojo");
        classModel.getFields().add(listField);
        return classModel;
    }

    private static FieldModel getArrayListJavaUtilFieldModel(String name, String type, String... values) {
        FieldModel fieldModel = getFieldModel(name, "Arrays$ArrayList", null);
        fieldModel.setFieldExtraType(ARRAY_LIST_JAVA_UTIL_ARRAYS);
        fieldModel.setValues(arrayElements(fieldModel.getName(), type, values));
        return fieldModel;
    }

    private static FieldModel getArrayFieldModel(String name, String type, String... values) {
        FieldModel fieldModel = getFieldModel(name, type, null);
        fieldModel.setFieldExtraType(ARRAY);
        fieldModel.setValues(arrayElements(fieldModel.getName(), fieldModel.getType(), values));
        return fieldModel;
    }

    private static List<FieldModel> arrayElements(String arrayFieldName, String type, String... values) {
        List<FieldModel> arrayElements = new ArrayList<>();
        int index = 0;
        for (String value : values) {
            FieldModel fieldModel = new FieldModel();
            fieldModel.setName(arrayFieldName + "_" + index++);
            fieldModel.setSetterName("set" + Strings.capitalize(fieldModel.getName()));
            fieldModel.setValue(value);
            fieldModel.setType(type);
            arrayElements.add(fieldModel);
        }
        return arrayElements;
    }
}
