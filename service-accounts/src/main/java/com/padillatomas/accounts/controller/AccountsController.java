/**
 * 
 */
package com.padillatomas.accounts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.padillatomas.accounts.config.AccountsServiceConfig;
import com.padillatomas.accounts.model.Accounts;
import com.padillatomas.accounts.model.Customer;
import com.padillatomas.accounts.model.Properties;
import com.padillatomas.accounts.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AccountsController {

	private final AccountsRepository accountsRepository;
	private final AccountsServiceConfig accountsConfig;

	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}
	}

	@GetMapping("/account/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(
				accountsConfig.getMsg(),
				accountsConfig.getBuildVersion(),
				accountsConfig.getMailDetails(),
				accountsConfig.getActiveBranches()
				);
		return ow.writeValueAsString(properties);
	}


}
