package av.debugger.codegen.codemodel;

/**
 * Helper enum class to differentiate implementations of same purpose.
 * <br/>
 * For example, java/util/Arrays$ArrayList and java/util/ArrayList do one job,
 * but have different ways of being declared in code.
 *
 * <ol>
 * <li> java/util/Arrays$ArrayList: </li>
 *  contact.setAddresses(Arrays.asList(addresses_0, addresses_1));
 * <li> java/util/ArrayList: </li>
 *  List&#60Address&#62 addressList = new ArrayList&#60&#62();
 *  addressList.add(addresses_0);
 *  addressList.add(addresses_1);
 *  contact.setAddresses(addressList);
 * </ol>
 */
public enum FieldExtraType {
    ARRAY,
    ARRAY_LIST_JAVA_UTIL_ARRAYS
}
