package av.debugger.codegen.pojo;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class SimpleSetterListPojo {
    private List<Short> shortList = new ArrayList<>();
    private List<Integer> integerList = new ArrayList<>();
    private List<Long> longList = new ArrayList<>();
    private List<Float> floatList = new ArrayList<>();
    private List<Double> doubleList = new ArrayList<>();
    private List<Character> characterList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
}
