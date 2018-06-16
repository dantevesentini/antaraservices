package com.antaratn.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.antaratn.services.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
