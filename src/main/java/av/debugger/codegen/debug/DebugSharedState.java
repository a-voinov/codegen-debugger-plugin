package av.debugger.codegen.debug;

import com.sun.jdi.ObjectReference;

import java.util.HashMap;
import java.util.Map;

public final class DebugSharedState {
    private static DebugSharedState instance;

    private DebugSharedState() {
    }

    public static DebugSharedState getInstance() {
        if (instance == null) {
            instance = new DebugSharedState();
        }
        return instance;
    }

    private final Map<String, ObjectReference> referenceMap = new HashMap<>();

    public void cleanRefs() {
        referenceMap.clear();
    }

    public void putRef(String name, ObjectReference ref) {
        referenceMap.put(name, ref);
    }

    public ObjectReference get(String name) {
        return referenceMap.get(name);
    }
}
