package av.debugger.codegen.pojo;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class SuperSetterListPojo {
    private List<NestedSetterPojo> pojoList = new ArrayList<>();
}
