package com.company.customer.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Billing extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Min(value = 1, message = "Bill ID must be a positive number")
	@Schema(description = "Unique identifier for the bill", example = "1", minimum = "1")
	private Long billId;

	@Column(nullable = false)
	@NotNull(message = "Amount is required")
	@Positive(message = "Amount must be positive")
	@Schema(description = "Amount of the bill", example = "150.0", minimum = "0.01")
	private Double amount;

	@Column(nullable = false)
	@NotNull(message = "Status is required")
	@Pattern(regexp = "^(Paid|Due)$", message = "Invalid status. Allowed values are 'Paid' and 'Due'")
	@Schema(description = "Status of the bill", example = "Paid", allowableValues = {"Paid", "Due"})
	private String status;

}
