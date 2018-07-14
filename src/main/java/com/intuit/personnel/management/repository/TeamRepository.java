package com.intuit.personnel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intuit.personnel.management.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team,Integer>{
}
