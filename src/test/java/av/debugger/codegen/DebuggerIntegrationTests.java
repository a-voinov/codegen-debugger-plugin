package av.debugger.codegen;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.request.ClassPrepareRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import av.debugger.codegen.codemodel.ClassModel;
import av.debugger.codegen.pojo.provider.CompanyDataProvider;
import av.debugger.codegen.pojo.provider.PojoDataProvider;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DebuggerIntegrationTests {
    private static VirtualMachine vm;
    private static Map<String, Value> debugFields;

    @BeforeAll
    public static void initTestVM() throws Exception {
        final Class<PojoTest> classToDebug = PojoTest.class;
        LaunchingConnector launchingConnector = Bootstrap.virtualMachineManager().defaultConnector();
        Map<String, Connector.Argument> env = launchingConnector.defaultArguments();
        env.get("main").setValue(classToDebug.getName());
        env.get("options").setValue("-cp \".\\build\\classes\\java\\test\"");
        vm = launchingConnector.launch(env);

        ClassPrepareRequest classPrepareRequest = vm.eventRequestManager().createClassPrepareRequest();
        classPrepareRequest.addClassFilter(classToDebug.getName());
        classPrepareRequest.enable();

        boolean eventLoop = true;
        ClassType classType = null;

        while (eventLoop) {
            EventSet eventSet = vm.eventQueue().remove(100);
            if (eventSet == null) continue;
            for (Event event : eventSet) {
                if (event instanceof ClassPrepareEvent) {
                    ClassPrepareEvent evt = (ClassPrepareEvent) event;
                    classType = (ClassType) evt.referenceType();
                    Location location = classType.locationsOfLine(PojoTest.breakpointLine()).get(0);
                    vm.eventRequestManager().createBreakpointRequest(location).enable();
                }
                if (event instanceof BreakpointEvent) {
                    event.request().disable();
                    debugFields = classType.getValues(classType.fields()).entrySet().stream()
                            .collect(HashMap::new, (m,e) -> m.put(e.getKey().name(), e.getValue()), Map::putAll);
                    eventLoop = false;
                    vm.suspend();
                    break;
                }
            }
            vm.resume();
        }
    }

    @AfterAll
    public static void dispose() {
        vm.dispose();
    }

    private ClassModel getClassModel(String fieldName) {
        return new JdiObjectAnalyzer().createClassModel((ObjectReference)debugFields.get(fieldName));
    }

    @Test
    public void simpleSetterTest() {
        final ClassModel actual = getClassModel("simplePojo");
        final ClassModel expected = PojoDataProvider.getSimpleSetterPojoClassModel();
        assertThat(actual).isNotNull().usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void boxedSetterTest() {
        final ClassModel actual = getClassModel("boxedPojo");
        final ClassModel expected = PojoDataProvider.getBoxedSetterPojoClassModel();
        assertThat(actual).isNotNull().usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void simpleSetterArrayTest() {
        final ClassModel actual = getClassModel("simpleArrayPojo");
        final ClassModel expected = PojoDataProvider.getSimpleSetterArrayPojoClassModel();
        assertThat(actual).isNotNull().usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void boxedSetterArrayTest() {
        final ClassModel actual = getClassModel("boxedArrayPojo");
        final ClassModel expected = PojoDataProvider.getBoxedSetterArrayPojoClassModel();
        assertThat(actual).isNotNull().usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void superSetterTest() {
        final ClassModel actual = getClassModel("superSetterPojo");
        final ClassModel expected = PojoDataProvider.getSuperSetterPojoClassModel();
        assertThat(actual).isNotNull().usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void superSetterArrayTest() {
        final ClassModel actual = getClassModel("superSetterArrayPojo");
        final ClassModel expected = PojoDataProvider.getSuperSetterArrayPojoClassModel();
        assertThat(actual).isNotNull().usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void simpleSetterArrayListJavaUtilTest() {
        final ClassModel actual = getClassModel("simpleSetterArrayListJavaUtilPojo");
        final ClassModel expected = PojoDataProvider.getSimpleSetterArrayListJavaUtilClassModel();
        assertThat(actual).isNotNull().usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void superSetterArrayListJavaUtilTest() {
        final ClassModel actual = getClassModel("superSetterArrayListJavaUtilPojo");
        final ClassModel expected = PojoDataProvider.getSuperSetterArrayListJavaUtilClassModel();
        assertThat(actual).isNotNull().usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void oneObjectInsideOtherObjectsTest() {
        final ClassModel actual = getClassModel("testCompanyPojo");
        final ClassModel expected = CompanyDataProvider.getTestCompanyPojoClassModel();
        assertThat(actual).isNotNull().usingRecursiveComparison().isEqualTo(expected);
    }
}
