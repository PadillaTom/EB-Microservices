/**
 * 
 */
package com.padillatomas.loans.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.padillatomas.loans.config.LoansServiceConfig;
import com.padillatomas.loans.model.Customer;
import com.padillatomas.loans.model.Loans;
import com.padillatomas.loans.model.Properties;
import com.padillatomas.loans.repository.LoansRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eazy Bytes
 *
 */

@RestController
@RequiredArgsConstructor
public class LoansController {

	private final LoansRepository loansRepository;
	private final LoansServiceConfig loansServiceConfig;

	@PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestBody Customer customer) {
		List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		if (loans != null) {
			return loans;
		} else {
			return null;
		}
	}

	@GetMapping("/loans/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(
				loansServiceConfig.getMsg(),
				loansServiceConfig.getBuildVersion(),
				loansServiceConfig.getMailDetails(),
				loansServiceConfig.getActiveBranches()
		);
		return ow.writeValueAsString(properties);
	}

}
