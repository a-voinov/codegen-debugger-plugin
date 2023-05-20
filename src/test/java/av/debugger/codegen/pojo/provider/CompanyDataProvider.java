package av.debugger.codegen.pojo.provider;

import av.debugger.codegen.codemodel.ClassModel;
import av.debugger.codegen.codemodel.FieldModel;
import av.debugger.codegen.pojo.company.Address;
import av.debugger.codegen.pojo.company.Company;
import av.debugger.codegen.pojo.company.Contact;
import av.debugger.codegen.pojo.company.TestCompanyPojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static av.debugger.codegen.codemodel.FieldExtraType.ARRAY_LIST_JAVA_UTIL_ARRAYS;

public final class CompanyDataProvider extends AbstractDataProvider {

    private CompanyDataProvider() {
    }

    public static TestCompanyPojo getTestCompanyPojo() {
        Address address1 = new Address();
        address1.setStreet("123 Main St");
        address1.setCity("Anytown");
        address1.setState("CA");
        address1.setZipCode("12345");

        Address address2 = new Address();
        address2.setStreet("456 Broad St");
        address2.setCity("Othertown");
        address2.setState("NY");
        address2.setZipCode("67890");

        Contact contact1 = new Contact();
        contact1.setFirstName("John");
        contact1.setLastName("Doe");
        contact1.setAddresses(Arrays.asList(address1, address2));

        Contact contact2 = new Contact();
        contact2.setFirstName("Jane");
        contact2.setLastName("Doe");
        contact2.setAddresses(Arrays.asList(address1));

        Company company = new Company();
        company.setName("Acme Corp");
        company.setContacts(Arrays.asList(contact1, contact2));

        TestCompanyPojo testPojo = new TestCompanyPojo();
        testPojo.setId(1);
        testPojo.setTimestamp(1684129640237L);
        testPojo.setValue(3.14);
        testPojo.setCompany(company);

        return testPojo;
    }

    public static ClassModel getTestCompanyPojoClassModel() {
        ClassModel classModel = new ClassModel();
        classModel.setHasDefaultConstructor(true);
        classModel.setClassName("TestCompanyPojo");
        classModel.setVariableName("testCompanyPojo");

        FieldModel companyFieldModel = getFieldModel("company", "Company", null);
        TestCompanyPojo testCompanyPojo = getTestCompanyPojo();
        companyFieldModel.setClassModel(getCompanyClassModel(testCompanyPojo.getCompany()));

        FieldModel idField = getFieldModel("id", "int", String.valueOf(testCompanyPojo.getId()));
        FieldModel timestampField = getFieldModel("timestamp", "long", String.valueOf(testCompanyPojo.getTimestamp()));
        FieldModel valueField = getFieldModel("value", "double", String.valueOf(testCompanyPojo.getValue()));

        classModel.setFields(List.of(idField, timestampField, valueField, companyFieldModel));
        return classModel;
    }

    private static ClassModel getAddressClassModel(Address address) {
        ClassModel classModel = new ClassModel();
        classModel.setHasDefaultConstructor(true);
        classModel.setClassName("Address");
        classModel.setVariableName("address");

        FieldModel streetField = getFieldModel("street", "String", address.getStreet());
        FieldModel cityField = getFieldModel("city", "String", address.getCity());
        FieldModel stateField = getFieldModel("state", "String", address.getState());
        FieldModel zipField = getFieldModel("zipCode", "String", address.getZipCode());

        classModel.setFields(List.of(streetField, cityField, stateField, zipField));
        return classModel;
    }

    private static ClassModel getContactClassModel(Contact contact) {
        ClassModel classModel = new ClassModel();
        classModel.setHasDefaultConstructor(true);
        classModel.setClassName("Contact");
        classModel.setVariableName("contact");

        FieldModel firstNameField = getFieldModel("firstName", "String", contact.getFirstName());
        FieldModel lastNameField = getFieldModel("lastName", "String", contact.getLastName());

        FieldModel addressesField = getFieldModel("addresses", "Arrays$ArrayList", null);
        addressesField.setFieldExtraType(ARRAY_LIST_JAVA_UTIL_ARRAYS);
        List<FieldModel> addressFields = new ArrayList<>();
        for (int i = 0; i < contact.getAddresses().size(); i++) {
            FieldModel addressFieldModel = getFieldModel("addresses_" + i, "Address", null);
            Address address = contact.getAddresses().get(i);
            addressFieldModel.setClassModel(getAddressClassModel(address));
            addressFields.add(addressFieldModel);
        }
        addressesField.setValues(addressFields);

        classModel.setFields(List.of(firstNameField, lastNameField, addressesField));
        return classModel;
    }

    private static ClassModel getCompanyClassModel(Company company) {
        ClassModel classModel = new ClassModel();
        classModel.setHasDefaultConstructor(true);
        classModel.setClassName("Company");
        classModel.setVariableName("company");

        FieldModel nameField = getFieldModel("name", "String", company.getName());

        FieldModel contactsField = getFieldModel("contacts", "Arrays$ArrayList", null);
        contactsField.setFieldExtraType(ARRAY_LIST_JAVA_UTIL_ARRAYS);
        List<FieldModel> contactFields = new ArrayList<>();

        // contact0 with both address0 and address1
        FieldModel contact0FieldModel = getFieldModel("contacts_0", "Contact", null);
        Contact contact0 = company.getContacts().get(0);
        contact0FieldModel.setClassModel(getContactClassModel(contact0));
        contactFields.add(contact0FieldModel);

        // contact1 with address0--------------
        ClassModel contact1ClassModel = new ClassModel();
        contact1ClassModel.setHasDefaultConstructor(true);
        contact1ClassModel.setClassName("Contact");
        contact1ClassModel.setVariableName("contact");

        Contact contact1 = company.getContacts().get(1);
        FieldModel contact1FirstNameField = getFieldModel("firstName", "String", contact1.getFirstName());
        FieldModel contact1LastNameField = getFieldModel("lastName", "String", contact1.getLastName());
        FieldModel contact1AddressesField = getFieldModel("addresses", "Arrays$ArrayList", null);
        contact1AddressesField.setFieldExtraType(ARRAY_LIST_JAVA_UTIL_ARRAYS);
        // set contact1AddressesField to Arrays.asList(address0)
        contact1AddressesField.setValues(List.of(contact0FieldModel
                                                         .getClassModel()
                                                         .getFields().get(2) // addresses
                                                         .getValues().get(0))); // addresses.get(0)
        contact1ClassModel.setFields(List.of(contact1FirstNameField, contact1LastNameField, contact1AddressesField));

        FieldModel contact1FieldModel = getFieldModel("contacts_1", "Contact", null);
        contact1FieldModel.setClassModel(contact1ClassModel);

        contactFields.add(contact1FieldModel);
        //-------------------------------------

        contactsField.setValues(contactFields);
        classModel.setFields(List.of(nameField, contactsField));

        return classModel;
    }
}
