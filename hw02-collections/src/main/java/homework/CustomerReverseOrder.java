package homework;


import java.util.Stack;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    private Stack<Customer> internalStorage = new Stack<>();

    public void add(Customer customer) {
        internalStorage.add(customer);
    }

    public Customer take() {
        return internalStorage.pop();
    }
}
