package com.company.customer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.company.customer.constants.CustomerConstants;
import com.company.customer.entity.Customer;
import com.company.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testAddCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerName("John Doe");

        mockMvc.perform(post("/api/customers/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(CustomerConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(CustomerConstants.MESSAGE_201));

        verify(customerService, times(1)).addCustomer(any(Customer.class));
    }

    @Test
    void testGetCustomersWithDueBills() throws Exception {
        Customer customer1 = new Customer();
        customer1.setCustomerId(1L);
        customer1.setCustomerName("Alice");

        Customer customer2 = new Customer();
        customer2.setCustomerId(2L);
        customer2.setCustomerName("Bob");

        List<Customer> mockCustomerList = Arrays.asList(customer1, customer2);
        when(customerService.getCustomersWithDueBills("Due")).thenReturn(mockCustomerList);

        mockMvc.perform(get("/api/customers/due-bills")
                .param("status", "Due")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).getCustomersWithDueBills("Due");
    }

    @Test
    void testGetCustomersWithDueBills_NoContent() throws Exception {
        when(customerService.getCustomersWithDueBills("Due")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/customers/due-bills")
                .param("status", "Due")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService, times(1)).getCustomersWithDueBills("Due");
    }
}
