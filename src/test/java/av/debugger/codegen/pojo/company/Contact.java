package av.debugger.codegen.pojo.company;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Contact {
    private String firstName;
    private String lastName;
    private List<Address> addresses;
}
