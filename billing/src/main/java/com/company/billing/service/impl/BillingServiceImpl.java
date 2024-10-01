package com.company.billing.service.impl;

import com.company.billing.dto.BillingDto;
import com.company.billing.entity.Billing;
import com.company.billing.exception.ResourceNotFoundException;
import com.company.billing.repository.BillingRepository;
import com.company.billing.service.BillingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BillingServiceImpl implements BillingService {

    private BillingRepository billingRepository;

    @Override
    public void addBilling(BillingDto billingDto) {
        billingRepository.save(createNewBilling(billingDto));
    }

    public List<Billing> getBillingsByStatus(String status) {
        return billingRepository.findByStatus(status);
    }

    public Billing getBillByBillId(Long billId) {
        Billing billing =  billingRepository.findByBillId(billId);

        if (billing == null) {
            throw new ResourceNotFoundException("Billing", "billId", billId.toString());
        }
        return billing;
    }

    private Billing createNewBilling(BillingDto billingDto) {
        Billing billing = new Billing();
        billing.setAmount(billingDto.getAmount());
        billing.setStatus(billingDto.getStatus());

        return billing;
    }
}
