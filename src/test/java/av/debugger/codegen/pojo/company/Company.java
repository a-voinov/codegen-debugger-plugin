package av.debugger.codegen.pojo.company;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Company {
    private String name;
    private List<Contact> contacts;
}
