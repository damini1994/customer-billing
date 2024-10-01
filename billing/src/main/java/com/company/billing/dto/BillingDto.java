package com.company.billing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillingDto {

    @Min(value = 1, message = "Bill ID must be a positive number")
    @Schema(description = "The unique ID of the bill", example = "1")
    private Long billId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    @Schema(description = "The amount of the bill", example = "150.0")
    private Double amount;

    @NotNull(message = "Status is required")
    @Pattern(regexp = "^(Paid|Due)$", message = "Invalid status. Allowed values are 'Paid' and 'Due'")
    @Schema(description = "The status of the bill", allowableValues = {"Paid", "Due"}, example = "Paid")
    private String status;

}
