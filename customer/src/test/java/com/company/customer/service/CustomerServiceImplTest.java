package com.company.customer.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.company.customer.entity.Billing;
import com.company.customer.entity.Customer;
import com.company.customer.repository.CustomerRepository;
import com.company.customer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RestTemplate restTemplate;

    @Value("${invoice.url}")
    private String billingServiceUrl;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testAddCustomer() {
        Customer customer = new Customer();
        customer.setCustomerName("John Doe");

        customerService.addCustomer(customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testGetCustomersWithDueBills() {
        Billing billing1 = new Billing(1L, 200.0, "Paid");
        Billing billing2 = new Billing(2L, 100.0, "Paid");
        List<Billing> dueBills = Arrays.asList(billing1, billing2);

        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(ResponseEntity.ok(dueBills));

        Customer customer1 = new Customer();
        customer1.setCustomerId(1L);
        customer1.setCustomerName("Alice");

        Customer customer2 = new Customer();
        customer2.setCustomerId(2L);
        customer2.setCustomerName("Bob");

        when(customerRepository.findAllByBilling_BillIdIn(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> result = customerService.getCustomersWithDueBills("Pending");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getCustomerName());
        assertEquals("Bob", result.get(1).getCustomerName());

        verify(restTemplate, times(1)).exchange(any(String.class), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class));
        verify(customerRepository, times(1)).findAllByBilling_BillIdIn(Arrays.asList(1L, 2L));
    }

    @Test
    void testGetCustomersWithDueBills_NoDueBills() {
        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(ResponseEntity.ok(Collections.emptyList()));

        List<Customer> result = customerService.getCustomersWithDueBills("Pending");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(customerRepository, never()).findAllByBilling_BillIdIn(any());
    }
}
