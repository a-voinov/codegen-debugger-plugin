package av.debugger.codegen.codemodel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FieldModel {
    private String name;
    private String value;
    private String setterName;
    private ClassModel classModel;
    private FieldExtraType fieldExtraType;
    private String type;
    private List<FieldModel> values = new ArrayList<>();

    public boolean hasSetter() {
        return setterName != null;
    }

    public boolean isPrimitiveField() {
        return classModel == null && fieldExtraType == null;
    }
}
