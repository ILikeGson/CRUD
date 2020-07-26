package main.java.ZhenyaShvyrkov.javacore.chapter04.view;

import main.java.ZhenyaShvyrkov.javacore.chapter04.controller.CustomerController;
import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Customer;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class CustomerView {
    private CustomerController controller = new CustomerController();
    public void getRequest(){
        Scanner scanner = new Scanner(System.in);
        String request;
        while((request = scanner.nextLine()) != null && request.length() != 0){
            String [] customerData = controller.handleRequest(request);
            if(request.startsWith("-c")){
               response(controller.create(customerData));
            }
            else if(request.startsWith("-r")) {
                if(customerData.length==1) response(controller.read());
                else response(controller.read(Long.valueOf(customerData[1])));
            }
            else if(request.startsWith("-u")){
                response(controller.update(customerData));
            }
            else if(request.startsWith("-d")){
                if(customerData.length == 2) controller.deleteById(Long.valueOf(customerData[1]));
                else controller.delete(customerData);
            }
            else throw new UnsupportedOperationException();
        }
    }
    private static void response(Customer customer){
        System.out.println(customer);
    }
    private static void response(List<Customer> list){
        list.forEach(System.out::println);
    }
}
