package com.intuit.personnel.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intuit.personnel.management.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer>{
	public List<Employee> findByManagerId(int managerId);
}
