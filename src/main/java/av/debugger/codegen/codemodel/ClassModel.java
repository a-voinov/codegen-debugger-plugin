package av.debugger.codegen.codemodel;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClassModel {
    private String className;
    private String variableName;
    @Getter(AccessLevel.NONE)
    private boolean hasDefaultConstructor;
    private List<FieldModel> fields = new ArrayList<>();

    private boolean usedInCodegen;

    public boolean hasDefaultConstructor() {
        return hasDefaultConstructor;
    }
}
