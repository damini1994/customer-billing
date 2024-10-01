package com.company.customer.controller;

import com.company.customer.constants.CustomerConstants;
import com.company.customer.dto.ResponseDto;
import com.company.customer.entity.Customer;
import com.company.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/customers", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/add")
    @Operation(summary = "Add a new customer", description = "Adds a new customer to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid customer data provided.")
    })
    public ResponseEntity<ResponseDto> addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CustomerConstants.STATUS_201, CustomerConstants.MESSAGE_201));
    }


    @GetMapping("/due-bills")
    @Operation(summary = "Get customers with due bills", description = "Fetches a list of customers with bills due.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of customers."),
            @ApiResponse(responseCode = "204", description = "No customers with due bills found.")
    })
    public ResponseEntity<List<Customer>> getCustomersWithDueBills(
            @RequestParam(value = "status", defaultValue = "Due") String status) {
        List<Customer> customers = customerService.getCustomersWithDueBills(status);
        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers);
    }
}
