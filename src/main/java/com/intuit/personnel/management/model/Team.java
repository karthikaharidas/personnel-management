package com.intuit.personnel.management.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the team database table.
 * 
 */
@Entity
@NamedQuery(name="Team.findAll", query="SELECT t FROM Team t")
public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="team_id")
	private int teamId;

	private String name;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="team_manager")
	private Employee employee;

	//bi-directional many-to-one association to Teammember
	@OneToMany(mappedBy="team")
	private List<Teammember> teammembers;

	public Team() {
	}

	public int getTeamId() {
		return this.teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Teammember> getTeammembers() {
		return this.teammembers;
	}

	public void setTeammembers(List<Teammember> teammembers) {
		this.teammembers = teammembers;
	}

	public Teammember addTeammember(Teammember teammember) {
		getTeammembers().add(teammember);
		teammember.setTeam(this);

		return teammember;
	}

	public Teammember removeTeammember(Teammember teammember) {
		getTeammembers().remove(teammember);
		teammember.setTeam(null);

		return teammember;
	}

}