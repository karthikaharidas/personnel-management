package com.intuit.personnel.management.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the teammembers database table.
 * 
 */
@Entity
@Table(name="teammembers")
@NamedQuery(name="Teammember.findAll", query="SELECT t FROM Teammember t")
public class Teammember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="emp_id")
	private Employee employee;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;

	public Teammember() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}