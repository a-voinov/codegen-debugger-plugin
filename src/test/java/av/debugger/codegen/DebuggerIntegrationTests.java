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
import av.debugger.codegen.codemodel.ClassModel;
import av.debugger.codegen.pojo.provider.CompanyDataProvider;
import av.debugger.codegen.pojo.provider.PojoDataProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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

    private static ClassModel getClassModel(String fieldName) {
        return new JdiObjectAnalyzer().createClassModel((ObjectReference)debugFields.get(fieldName));
    }

    @ParameterizedTest(name = "{index} => {0}")
    @MethodSource
    public void integrationTest(ClassModel actual, ClassModel expected) {
        assertThat(actual).isNotNull().usingRecursiveComparison().isEqualTo(expected);
    }

    private static Stream<Arguments> integrationTest() {
        return Stream.of(
                Arguments.of(
                        getClassModel("simplePojo"),
                        PojoDataProvider.getSimpleSetterPojoClassModel()
                ),
                Arguments.of(
                        getClassModel("boxedPojo"),
                        PojoDataProvider.getBoxedSetterPojoClassModel()
                ),
                Arguments.of(
                        getClassModel("simpleArrayPojo"),
                        PojoDataProvider.getSimpleSetterArrayPojoClassModel()
                ),
                Arguments.of(
                        getClassModel("boxedArrayPojo"),
                        PojoDataProvider.getBoxedSetterArrayPojoClassModel()
                ),
                Arguments.of(
                        getClassModel("superSetterPojo"),
                        PojoDataProvider.getSuperSetterPojoClassModel()
                ),
                Arguments.of(
                        getClassModel("superSetterArrayPojo"),
                        PojoDataProvider.getSuperSetterArrayPojoClassModel()
                ),
                Arguments.of(
                        getClassModel("simpleSetterArrayListJavaUtilPojo"),
                        PojoDataProvider.getSimpleSetterArrayListJavaUtilClassModel()
                ),
                Arguments.of(
                        getClassModel("superSetterArrayListJavaUtilPojo"),
                        PojoDataProvider.getSuperSetterArrayListJavaUtilClassModel()
                ),
                Arguments.of(
                        getClassModel("testCompanyPojo"),
                        CompanyDataProvider.getTestCompanyPojoClassModel()
                )
        );
    }
}
