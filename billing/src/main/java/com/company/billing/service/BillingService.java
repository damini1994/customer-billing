package com.company.billing.service;

import com.company.billing.dto.BillingDto;
import com.company.billing.entity.Billing;
import java.util.List;

public interface BillingService {

    void addBilling(BillingDto billingDto);

    List<Billing> getBillingsByStatus(String status);

    Billing getBillByBillId(Long billId);
}
