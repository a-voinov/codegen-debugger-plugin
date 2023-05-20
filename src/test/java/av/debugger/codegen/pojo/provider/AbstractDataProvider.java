package av.debugger.codegen.pojo.provider;

import com.intellij.openapi.util.text.Strings;
import av.debugger.codegen.codemodel.FieldModel;

public abstract class AbstractDataProvider {

    protected static FieldModel getFieldModel(String name, String type, String value) {
        FieldModel fieldModel = new FieldModel();
        fieldModel.setName(name);
        fieldModel.setType(type);
        fieldModel.setSetterName("set" + Strings.capitalize(name));
        fieldModel.setValue(value);
        return fieldModel;
    }
}
