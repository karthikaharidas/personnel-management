package com.intuit.personnel.management;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.personnel.management.model.Employee;
import com.intuit.personnel.management.model.Teammember;

@RestController
public class PersonnelController {
	private static final String JSON_CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8";

	@Autowired
	PersonnelService personnelService;

	/**
	 * Get employee by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/employee/{id}")
	public Employee getEmployeeById(@PathVariable(value = "id") int id) {
		System.out.println(personnelService.getEmployeeById(id));
		return personnelService.getEmployeeById(id);
	}

	/**
	 * Create employee and add to team.
	 * 
	 * @param employee
	 * @return
	 */
	@PostMapping(path="employee", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			JSON_CONTENT_TYPE})
	public Employee addEmployee(@RequestBody Employee employee) {
		System.out.println(employee.getName());
		Employee savedEmp = personnelService.createEmployee(employee);
		
		return savedEmp;
	}

	/**
	 * Update employee name & address.
	 * 
	 * @param id
	 * @param updateDetails
	 * @return
	 */
	@PutMapping(path = "employee/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			JSON_CONTENT_TYPE})
	public Employee updateEmpDetails(@PathVariable(value = "id") int id, @RequestBody Employee updateDetails) {
		Employee emp = personnelService.updateEmpDetails(id, updateDetails);
		return emp;
	}

	
	/**
	 * Employee promotion / titlechange.
	 * 
	 * @param id
	 * @param updateDetails
	 * @return
	 */
	@PutMapping(path = "employee/{id}/title" , consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			JSON_CONTENT_TYPE})
	public Employee empTitleChange(@PathVariable(value = "id") int id, @RequestBody Employee updateDetails) {
		Employee emp = personnelService.empTitleChange(id, updateDetails);
		return emp;
	}

	/**
	 * Employee goes on vacation.
	 * 
	 * @param id
	 * @param updateDetails
	 * @return
	 */
	@PutMapping(path="employee/{id}/vacation" ,consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			JSON_CONTENT_TYPE})
	public Employee updateVacation(@PathVariable(value = "id") int id, @RequestBody Employee updateDetails) {
		Employee emp = personnelService.updateVacation(id, updateDetails);
		return emp;
	}

	/**
	 * Get Manager Report - list of enployees under the manager.
	 * 
	 * @param id managerId
	 * @return
	 */
	@GetMapping("/manager/{id}/employee")
	public List<Employee> getManagerReport(@PathVariable(value = "id") int id) {
		return personnelService.getManagerReport(id);
	}

	/**
	 * Transfer employee to another team.
	 * 
	 * @param id
	 * @param teamId
	 * @param updateDetails
	 * @return 
	 * @return
	 */
	@PutMapping(path="employee/{id}/team/{teamId}" ,consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			JSON_CONTENT_TYPE})
	public Teammember transferTeam(@PathVariable(value = "id") int empId, @PathVariable(value = "teamId") int teamId) {
		// assuming emp belongs to onky 1 team
		return personnelService.transferTeam(empId, teamId);

	}

}
