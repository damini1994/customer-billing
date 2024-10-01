package com.company.customer.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;

@Entity
@Getter
@Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique identifier for the customer", example = "1")
	private Long customerId;

	@Column(nullable = false)
	@Schema(description = "Name of the customer", example = "John Doe")
	private String customerName;

	@Column(nullable = false)
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	@Schema(description = "Mobile number of the customer", example = "1234567890")
	private String mobileNumber;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customerBillId")
	@Schema(description = "Billing details associated with the customer")
	private Billing billing;
}
