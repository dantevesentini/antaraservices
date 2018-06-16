package com.antaratn.services.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antaratn.services.exception.ResourceNotFoundException;
import com.antaratn.services.model.Appointment;
import com.antaratn.services.repository.AppointmentRepository;
import com.antaratn.services.repository.CustomerRepository;

@RestController
@RequestMapping("/api")
public class AppointmentController {

	@Autowired
	private AppointmentRepository AppointmentRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customers/{customerId}/appointments")
	public Page<Appointment> getAllAppointmentsBycustomerId(@PathVariable(value = "customerId") Long customerId,
			Pageable pageable) {
		return AppointmentRepository.findByCustomerId(customerId, pageable);
	}

	@PostMapping("/customers/{customerId}/appointments")
	public Appointment createAppointment(@PathVariable(value = "customerId") Long customerId,
			@Valid @RequestBody Appointment appointment) {
		return customerRepository.findById(customerId).map(customer -> {
			appointment.setCustomer(customer);
			return AppointmentRepository.save(appointment);
		}).orElseThrow(() -> new ResourceNotFoundException("customerId " + customerId + " not found"));
	}

	@PutMapping("/customers/{customerId}/appointments/{appointmentId}")
	public Appointment updateAppointment(@PathVariable(value = "customerId") Long customerId,
			@PathVariable(value = "appointmentId") Long appointmentId,
			@Valid @RequestBody Appointment appointmentRequest) {
		if (!customerRepository.existsById(customerId)) {
			throw new ResourceNotFoundException("customerId " + customerId + " not found");
		}

		return AppointmentRepository.findById(appointmentId).map(appointment -> {
			appointment.setPrice(appointmentRequest.getPrice());
			appointment.setScheduledDate(appointmentRequest.getScheduledDate());
			appointment.setTypeOfProcedure(appointmentRequest.getTypeOfProcedure());
			return AppointmentRepository.save(appointment);
		}).orElseThrow(() -> new ResourceNotFoundException("appointmentId " + appointmentId + "not found"));
	}

	@DeleteMapping("/customers/{customerId}/appointments/{AppointmentId}")
	public ResponseEntity<?> deleteAppointment(@PathVariable(value = "customerId") Long customerId,
			@PathVariable(value = "appointmentId") Long appointmentId) {
		if (!customerRepository.existsById(customerId)) {
			throw new ResourceNotFoundException("customerId " + customerId + " not found");
		}

		return AppointmentRepository.findById(appointmentId).map(Appointment -> {
			AppointmentRepository.delete(Appointment);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("appointmentId " + appointmentId + " not found"));
	}
}
