package av.debugger.codegen.pojo.company;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestCompanyPojo {
    private int id;
    private long timestamp;
    private double value;
    private Company company;
}
