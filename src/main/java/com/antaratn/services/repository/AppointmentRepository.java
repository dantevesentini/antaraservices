package com.antaratn.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.antaratn.services.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	Page<Appointment> findByCustomerId(Long postId, Pageable pageable);
	
}
