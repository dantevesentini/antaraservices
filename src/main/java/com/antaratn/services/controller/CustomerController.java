package com.antaratn.services.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antaratn.services.exception.CustomerNotFoundException;
import com.antaratn.services.model.Customer;
import com.antaratn.services.repository.CustomerRepository;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;

	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@PostMapping("/customers")
	public Customer createCustomer(@Valid @RequestBody Customer customer) {
		return customerRepository.save(customer);
	}

	@GetMapping("/customers/{id}")
	public Customer getCustomerById(@PathVariable(value = "id") Long customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer", "id", customerId));
	}

	@PutMapping("/customers/{id}")
	public Customer updateCustomer(@PathVariable(value = "id") Long customerId,
			@Valid @RequestBody String identificationNumber, @Valid @RequestBody String name,
			@Valid @RequestBody String email, @Valid @RequestBody String password) {

		Customer customer = getCustomerById(customerId);

		customer.setIdentificationNumber(identificationNumber);
		customer.setName(name);
		customer.setEmail(email);
		customer.setPassword(password);

		return customerRepository.save(customer);

	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable(value = "id") Long customerId) {

		Customer customer = getCustomerById(customerId);
		customerRepository.delete(customer);

		return ResponseEntity.ok().build();

	}
}
