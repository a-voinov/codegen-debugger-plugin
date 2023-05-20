package av.debugger.codegen;

import org.junit.jupiter.api.Test;
import av.debugger.codegen.codemodel.ClassModel;
import av.debugger.codegen.pojo.provider.CompanyDataProvider;
import av.debugger.codegen.pojo.provider.PojoDataProvider;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CodeGeneratorTests {

    @Test
    void generateStubWithSettersTest() {
        final List<String> actual = getActualList(PojoDataProvider.getSimpleSetterPojoClassModel());
        final List<String> expected = Arrays.asList(
            "SimpleSetterPojo simpleSetterPojo = new SimpleSetterPojo();",
            "simpleSetterPojo.setShortField(32767);",
            "simpleSetterPojo.setIntField(2147483647);",
            "simpleSetterPojo.setLongField(9223372036854775807L);",
            "simpleSetterPojo.setFloatField(3.4028235E38f);",
            "simpleSetterPojo.setDoubleField(1.7976931348623157E308);",
            "simpleSetterPojo.setCharField('A');",
            "simpleSetterPojo.setStringField(\"testing...\");"
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void generateStubWithBoxedSettersTest() {
        final List<String> actual = getActualList(PojoDataProvider.getBoxedSetterPojoClassModel());
        final List<String> expected = Arrays.asList(
                "BoxedSetterPojo boxedSetterPojo = new BoxedSetterPojo();",
                "boxedSetterPojo.setShortField(32767);",
                "boxedSetterPojo.setIntegerField(2147483647);",
                "boxedSetterPojo.setLongField(9223372036854775807L);",
                "boxedSetterPojo.setFloatField(3.4028235E38f);",
                "boxedSetterPojo.setDoubleField(1.7976931348623157E308);",
                "boxedSetterPojo.setCharacterField('A');"
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void generateStubWithArraySettersTest() {
        final List<String> actual = getActualList(PojoDataProvider.getSimpleSetterArrayPojoClassModel());
        final List<String> expected = Arrays.asList(
                "SimpleSetterArrayPojo simpleSetterArrayPojo = new SimpleSetterArrayPojo();",
                "simpleSetterArrayPojo.setShortArray(new short[]{1, 2, 3});",
                "simpleSetterArrayPojo.setIntArray(new int[]{1, 2, 3});",
                "simpleSetterArrayPojo.setLongArray(new long[]{1L, 2L, 3L});",
                "simpleSetterArrayPojo.setFloatArray(new float[]{0.1f, 0.2f, 0.3f});",
                "simpleSetterArrayPojo.setDoubleArray(new double[]{0.1, 0.2, 0.3});",
                "simpleSetterArrayPojo.setCharArray(new char[]{'a', 'b', 'c'});",
                "simpleSetterArrayPojo.setStringArray(new String[]{\"alpha\", \"beta\", \"gamma\", \"delta\"});"
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void generateStubWithBoxedArraySettersTest() {
        final List<String> actual = getActualList(PojoDataProvider.getBoxedSetterArrayPojoClassModel());
        final List<String> expected = Arrays.asList(
                "BoxedSetterArrayPojo boxedSetterArrayPojo = new BoxedSetterArrayPojo();",
                "boxedSetterArrayPojo.setShortArray(new Short[]{1, 2, 3});",
                "boxedSetterArrayPojo.setIntegerArray(new Integer[]{1, 2, 3});",
                "boxedSetterArrayPojo.setLongArray(new Long[]{1L, 2L, 3L});",
                "boxedSetterArrayPojo.setFloatArray(new Float[]{0.1f, 0.2f, 0.3f});",
                "boxedSetterArrayPojo.setDoubleArray(new Double[]{0.1, 0.2, 0.3});",
                "boxedSetterArrayPojo.setCharacterArray(new Character[]{'a', 'b', 'c'});"
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void generateStubWithObjectSettersTest() {
        final List<String> actual = getActualList(PojoDataProvider.getSuperSetterPojoClassModel());
        final List<String> expected = Arrays.asList(
                "SuperSetterPojo superSetterPojo = new SuperSetterPojo();",
                "NestedSetterPojo nestedSetterPojo = new NestedSetterPojo();",
                "nestedSetterPojo.setIntField(1);",
                "superSetterPojo.setNestedObject(nestedSetterPojo);",
                "superSetterPojo.setStringField(\"some field\");"
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void generateStubWithObjectArrayTest() {
        final List<String> actual = getActualList(PojoDataProvider.getSuperSetterArrayPojoClassModel());
        final List<String> expected = Arrays.asList(
                "SuperSetterArrayPojo superSetterArrayPojo = new SuperSetterArrayPojo();",
                "NestedSetterPojo nestedObjectArray_0 = new NestedSetterPojo();",
                "nestedObjectArray_0.setIntField(1);",
                "NestedSetterPojo nestedObjectArray_1 = new NestedSetterPojo();",
                "nestedObjectArray_1.setIntField(2);",
                "NestedSetterPojo nestedObjectArray_2 = new NestedSetterPojo();",
                "nestedObjectArray_2.setIntField(3);",
                "superSetterArrayPojo.setNestedObjectArray(new NestedSetterPojo[]{nestedObjectArray_0, nestedObjectArray_1, nestedObjectArray_2});",
                "superSetterArrayPojo.setStringField(\"some field\");"
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void generateStubWithPrimitiveArraysAsListSettersTest() {
        final List<String> actual = getActualList(PojoDataProvider.getSimpleSetterArrayListJavaUtilClassModel());

        final List<String> expected = Arrays.asList(
                "SimpleSetterListPojo simpleSetterListPojo = new SimpleSetterListPojo();",
                "simpleSetterListPojo.setShortList(Arrays.asList((short)1, (short)2, (short)3));",
                "simpleSetterListPojo.setIntegerList(Arrays.asList(1, 2, 3));",
                "simpleSetterListPojo.setLongList(Arrays.asList(1L, 2L, 3L));",
                "simpleSetterListPojo.setFloatList(Arrays.asList(0.1f, 0.2f, 0.3f));",
                "simpleSetterListPojo.setDoubleList(Arrays.asList(0.1, 0.2, 0.3));",
                "simpleSetterListPojo.setCharacterList(Arrays.asList('a', 'b', 'c'));",
                "simpleSetterListPojo.setStringList(Arrays.asList(\"alpha\", \"beta\", \"gamma\", \"delta\"));"
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void generateStubWithObjectArraysAsListSettersTest() {
        final List<String> actual = getActualList(PojoDataProvider.getSuperSetterArrayListJavaUtilClassModel());

        final List<String> expected = Arrays.asList(
                "SuperSetterListPojo superSetterListPojo = new SuperSetterListPojo();",
                "NestedSetterPojo pojoList_0 = new NestedSetterPojo();",
                "pojoList_0.setIntField(1);",
                "NestedSetterPojo pojoList_1 = new NestedSetterPojo();",
                "pojoList_1.setIntField(2);",
                "superSetterListPojo.setPojoList(Arrays.asList(pojoList_0, pojoList_1));"
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void oneObjectInsideOtherObjectsTest() {
        final List<String> actual = getActualList(CompanyDataProvider.getTestCompanyPojoClassModel());

        final List<String> expected = Arrays.asList(
                "TestCompanyPojo testCompanyPojo = new TestCompanyPojo();",
                "testCompanyPojo.setId(1);",
                "testCompanyPojo.setTimestamp(1684129640237L);",
                "testCompanyPojo.setValue(3.14);",
                "Company company = new Company();",
                "company.setName(\"Acme Corp\");",
                "Contact contacts_0 = new Contact();",
                "contacts_0.setFirstName(\"John\");",
                "contacts_0.setLastName(\"Doe\");",
                "Address addresses_0 = new Address();",
                "addresses_0.setStreet(\"123 Main St\");",
                "addresses_0.setCity(\"Anytown\");",
                "addresses_0.setState(\"CA\");",
                "addresses_0.setZipCode(\"12345\");",
                "Address addresses_1 = new Address();",
                "addresses_1.setStreet(\"456 Broad St\");",
                "addresses_1.setCity(\"Othertown\");",
                "addresses_1.setState(\"NY\");",
                "addresses_1.setZipCode(\"67890\");",
                "contacts_0.setAddresses(Arrays.asList(addresses_0, addresses_1));",
                "Contact contacts_1 = new Contact();",
                "contacts_1.setFirstName(\"Jane\");",
                "contacts_1.setLastName(\"Doe\");",
                "contacts_1.setAddresses(Arrays.asList(addresses_0));",
                "company.setContacts(Arrays.asList(contacts_0, contacts_1));",
                "testCompanyPojo.setCompany(company);"
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private static List<String> getActualList(ClassModel classModel) {
        return Arrays.asList(
                CodeGenerator.generate(classModel)
                        .replace(System.lineSeparator(), "")
                        .split("(?<=;)"));
    }
}
