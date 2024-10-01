package com.company.billing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.company.billing.dto.BillingDto;
import com.company.billing.entity.Billing;
import com.company.billing.service.BillingService;

@ExtendWith(MockitoExtension.class)
public class BillingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BillingService billingService;

    @InjectMocks
    private BillingController billingController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(billingController).build();
    }

    @Test
    void testAddBilling() throws Exception {
        doNothing().when(billingService).addBilling(any(BillingDto.class));

        mockMvc.perform(post("/api/billings/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"amount\": 150.0, \"status\": \"Paid\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value("201"))
                .andExpect(jsonPath("$.statusMsg").value("Billing Info added successfully"));

        verify(billingService, times(1)).addBilling(any(BillingDto.class));
    }

    @Test
    void testGetBillingsByStatus() throws Exception {
        Billing billing1 = new Billing(1L, 150.0, "Paid");
        Billing billing2 = new Billing(2L, 200.0, "Pending");
        List<Billing> mockBillingList = Arrays.asList(billing1, billing2);

        when(billingService.getBillingsByStatus("Paid")).thenReturn(mockBillingList);

        mockMvc.perform(get("/api/billings/status/Paid")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(billingService, times(1)).getBillingsByStatus("Paid");
    }

    @Test
    void testGetBillingByBillId() throws Exception {
        Billing mockBilling = new Billing(1L, 150.0, "Paid");

        when(billingService.getBillByBillId(1L)).thenReturn(mockBilling);

        mockMvc.perform(get("/api/billings/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(billingService, times(1)).getBillByBillId(1L);
    }
}
