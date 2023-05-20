package av.debugger.codegen;

import av.debugger.codegen.pojo.*;
import av.debugger.codegen.pojo.company.TestCompanyPojo;
import av.debugger.codegen.pojo.provider.CompanyDataProvider;
import av.debugger.codegen.pojo.provider.PojoDataProvider;

public class PojoTest {
    public static void main(String[] args) {
        System.out.println("Place breakpoint here");
    }

    public static int breakpointLine() {
        return 10;
    }

    static SimpleSetterPojo simplePojo = PojoDataProvider.getSimpleSetterPojo();
    static SimpleSetterArrayPojo simpleArrayPojo = PojoDataProvider.getSimpleSetterArrayPojo();
    static BoxedSetterPojo boxedPojo = PojoDataProvider.getBoxedSetterPojo();
    static BoxedSetterArrayPojo boxedArrayPojo = PojoDataProvider.getBoxedSetterArrayPojo();
    static SuperSetterPojo superSetterPojo = PojoDataProvider.getSuperSetterPojo();
    static SuperSetterArrayPojo superSetterArrayPojo = PojoDataProvider.getSuperSetterArrayPojo();
    static SimpleSetterListPojo simpleSetterArrayListJavaUtilPojo = PojoDataProvider.getSimpleSetterArrayListJavaUtilPojo();
    static SuperSetterListPojo superSetterArrayListJavaUtilPojo = PojoDataProvider.getSuperSetterArrayListJavaUtilPojo();
    static TestCompanyPojo testCompanyPojo = CompanyDataProvider.getTestCompanyPojo();
}
