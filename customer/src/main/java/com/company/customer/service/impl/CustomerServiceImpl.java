package com.company.customer.service.impl;

import com.company.customer.entity.Billing;
import com.company.customer.entity.Customer;
import com.company.customer.repository.CustomerRepository;
import com.company.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${invoice.url}")
    private String billingServiceUrl;

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> getCustomersWithDueBills(String status) {
        ResponseEntity<List<Billing>> responseEntity = restTemplate.exchange(
                billingServiceUrl + status,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Billing>>() {}
        );

        List<Billing> dueBills = responseEntity.getBody();
        if (dueBills != null && !dueBills.isEmpty()) {
            List<Long> billIds = dueBills.stream()
                    .map(Billing::getBillId)
                    .collect(Collectors.toList());

            return customerRepository.findAllByBilling_BillIdIn(billIds);
        }

        return Collections.emptyList();
    }
}
