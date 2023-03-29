package homework;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final TreeMap<Customer, String> internalStorage
            = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return makeCopyIfNonNull(internalStorage.firstEntry()); // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return makeCopyIfNonNull(internalStorage.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        internalStorage.put(customer, data);
    }

    private static Map.Entry<Customer, String> makeCopyIfNonNull(Map.Entry<Customer, String> found) {
        if (found == null) {
            return null;
        }
        return new AbstractMap.SimpleImmutableEntry<>(found.getKey().copy(), found.getValue());
    }
}
