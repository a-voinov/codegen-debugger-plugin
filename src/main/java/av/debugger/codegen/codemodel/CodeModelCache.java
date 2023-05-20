package av.debugger.codegen.codemodel;

import com.sun.jdi.ObjectReference;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassModel cache used to keep information about objects and fields to solve the following problems:
 * <ol>
 * <li>Objects creation which already stored in memory</li>
 * <li>TODO Collision of generated names</li>
 * </ol>
 */
public final class CodeModelCache {

    private static final Map<Long, ClassModel> CLASS_MODEL_BY_ID = new HashMap<>();

    /**
     * Stores ClassModel in cache
     * @param uniqueID unique ID of ObjectReference in VirtualMachine {@link ObjectReference#uniqueID()}
     * @param classModel generated {@link ClassModel}
     */
    public void put(long uniqueID, ClassModel classModel) {
        CLASS_MODEL_BY_ID.put(uniqueID, classModel);
    }

    @Nullable
    public ClassModel getById(long uniqueID) {
        return CLASS_MODEL_BY_ID.get(uniqueID);
    }
}
