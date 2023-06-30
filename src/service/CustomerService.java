package service;

import model.Customer;

import java.util.*;

public class CustomerService {

    private Map<String, Customer> customers = new HashMap<>();
    private static CustomerService customerService;

    private CustomerService() {}

    public static CustomerService getInstance() {
        if(customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }
    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String customerEmail) throws Exception {
        if(customers.containsKey(customerEmail)) {
            return customers.get(customerEmail);
        }else {
            throw new Exception("Email does not exist");
        }
    }

    public Collection<Customer> getAllCustomer() {
        return customers.values();
    }
}
