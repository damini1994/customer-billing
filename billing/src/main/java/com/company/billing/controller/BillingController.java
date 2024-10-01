package com.company.billing.controller;

import com.company.billing.constants.BillingConstants;
import com.company.billing.dto.BillingDto;
import com.company.billing.dto.ResponseDto;
import com.company.billing.entity.Billing;
import com.company.billing.service.BillingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/billings", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class BillingController {

    private BillingService billingService;

    @Operation(summary = "Add a new billing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Billing created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addBilling(@Valid @RequestBody BillingDto billingDto) {
        billingService.addBilling(billingDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(BillingConstants.STATUS_201, BillingConstants.MESSAGE_201));
    }


    @Operation(summary = "Get billings by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved billings"),
            @ApiResponse(responseCode = "404", description = "No billings found for the given status")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Billing>> getBillingsByStatus(@PathVariable("status") String status) {
        List<Billing> billingList = billingService.getBillingsByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(billingList);
    }

    @Operation(summary = "Get billing by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved billing"),
            @ApiResponse(responseCode = "404", description = "Billing not found for the given ID")
    })
    @GetMapping("/{billingId}")
    public ResponseEntity<Billing> getBillingByBillId(@PathVariable @NotNull(message = "Bill ID cannot be null") Long billingId) {
        Billing billing = billingService.getBillByBillId(billingId);
        return ResponseEntity.status(HttpStatus.OK).body(billing);
    }
}
