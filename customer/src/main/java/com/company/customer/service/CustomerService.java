package com.company.customer.service;

import com.company.customer.entity.Customer;

import java.util.List;

public interface CustomerService {

    void addCustomer(Customer customer);

    List<Customer> getCustomersWithDueBills(String status);
}
