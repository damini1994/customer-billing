package com.company.billing.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.company.billing.dto.BillingDto;
import com.company.billing.entity.Billing;
import com.company.billing.exception.ResourceNotFoundException;
import com.company.billing.repository.BillingRepository;
import com.company.billing.service.impl.BillingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class BillingServiceImplTest {

    @Mock
    private BillingRepository billingRepository;

    @InjectMocks
    private BillingServiceImpl billingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBilling() {
        BillingDto billingDto = new BillingDto(1L, 200.0, "Paid");
        Billing billing = new Billing(1L, 200.0, "Paid");

        when(billingRepository.save(any(Billing.class))).thenReturn(billing);

        billingService.addBilling(billingDto);

        verify(billingRepository, times(1)).save(any(Billing.class));
    }

    @Test
    void testGetBillingsByStatus() {
        List<Billing> billingList = List.of(new Billing(1L, 100.0, "Paid"));

        when(billingRepository.findByStatus("Paid")).thenReturn(billingList);

        List<Billing> result = billingService.getBillingsByStatus("Paid");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Paid", result.get(0).getStatus());
        verify(billingRepository, times(1)).findByStatus("Paid");
    }

    @Test
    void testGetBillByBillIdFound() {
        Billing billing = new Billing(1L, 200.0, "Due");

        when(billingRepository.findByBillId(1L)).thenReturn(billing);

        Billing result = billingService.getBillByBillId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getBillId());
        assertEquals(200.0, result.getAmount());

        verify(billingRepository, times(1)).findByBillId(1L);
    }

    @Test
    void testGetBillByBillIdNotFound() {
        when(billingRepository.findByBillId(1L)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> billingService.getBillByBillId(1L)
        );

        verify(billingRepository, times(1)).findByBillId(1L);
    }
}
