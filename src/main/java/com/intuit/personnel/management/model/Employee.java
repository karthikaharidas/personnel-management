package com.intuit.personnel.management.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	public Employee(int empId, String name,String address, String title,BigDecimal vacationDaysRemaining, int teamId, int managerId) {
		super();
		this.empId = empId;
		this.name = name;
		this.address = address;
		this.title = title;
		this.teamId = teamId;
		this.managerId = managerId;
		this.vacationDaysRemaining = vacationDaysRemaining;
	}
	
	public Employee(int empId, String name,String address,  String title,BigDecimal vacationDaysRemaining) {
		super();
		this.empId = empId;
		this.name = name;
		this.address = address;
		this.title = title;
		this.vacationDaysRemaining = vacationDaysRemaining;
		
	}

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="emp_id")
	private int empId;

	private String address;

	private String email;

	@Temporal(TemporalType.DATE)
	@Column(name="hire_date")
	private Date hireDate;

	private String name;

	private String phone;

	private String title;

	@Column(name="vacation_days_remaining")
	private BigDecimal vacationDaysRemaining;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="Manager")
	private Employee manager;

	//bi-directional many-to-one association to Employee
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY,mappedBy="manager")
	private List<Employee> employees;

	//bi-directional many-to-one association to Team
	@OneToMany(mappedBy="employee")
	@JsonIgnore
	private List<Team> teams;

	//bi-directional many-to-one association to Teammember
	@OneToMany(fetch=FetchType.LAZY,mappedBy="employee")
	@JsonIgnore
	private List<Teammember> teammembers;
	
	private int teamId;
	
	private int managerId;
	

	public Employee() {
	}

	public int getEmpId() {
		return this.empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHireDate() {
		return this.hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getVacationDaysRemaining() {
		return this.vacationDaysRemaining;
	}

	public void setVacationDaysRemaining(BigDecimal vacationDaysRemaining) {
		this.vacationDaysRemaining = vacationDaysRemaining;
	}

	public Employee getEmployee() {
		return this.manager;
	}

	public void setEmployee(Employee mgr) {
		this.manager = mgr;
	}

	public List<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee addManager(Employee employee) {
		getEmployees().add(employee);
		employee.setEmployee(this);

		return employee;
	}

	public Employee removeManager(Employee employee) {
		getEmployees().remove(employee);
		employee.setEmployee(null);

		return employee;
	}

	public List<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public Team addTeam(Team team) {
		getTeams().add(team);
		team.setEmployee(this);

		return team;
	}

	public Team removeTeam(Team team) {
		getTeams().remove(team);
		team.setEmployee(null);

		return team;
	}

	public List<Teammember> getTeammembers() {
		return this.teammembers;
	}

	public void setTeammembers(List<Teammember> teammembers) {
		this.teammembers = teammembers;
	}

	public Teammember addTeammember(Teammember teammember) {
		getTeammembers().add(teammember);
		teammember.setEmployee(this);

		return teammember;
	}

	public Teammember removeTeammember(Teammember teammember) {
		getTeammembers().remove(teammember);
		teammember.setEmployee(null);

		return teammember;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

}