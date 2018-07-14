package com.intuit.personnel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intuit.personnel.management.model.Employee;
import com.intuit.personnel.management.model.Teammember;

@Repository
public interface TeamMemberRepository extends JpaRepository<Teammember,Integer>{
	public Teammember findByEmployee(Employee employee);
}
