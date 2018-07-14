package com.intuit.personnel.management;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.personnel.management.model.Employee;
import com.intuit.personnel.management.model.Teammember;
import com.intuit.personnel.management.repository.EmployeeRepository;
import com.intuit.personnel.management.repository.TeamMemberRepository;
import com.intuit.personnel.management.repository.TeamRepository;


@RestController
public class PersonnelService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	TeamMemberRepository teamMemberRepository;
	
	
    public Employee getEmployeeById(int id){
		System.out.println(employeeRepository.findById(id).get().getName());
        return employeeRepository.findById(id).get();
    }
	
    /**
     * Handle employee creation and save team-emp relation
     * 
     * @param employee
     * @return
     */
	public Employee createEmployee(Employee employee){
		System.out.println(employee.getName());
		employee.setVacationDaysRemaining(new BigDecimal(15));
		employee.setHireDate(new Date());
		Employee savedEmp = employeeRepository.save(employee);
		//save team members
		Teammember teammember = new Teammember();
		teammember.setEmployee(savedEmp);
		teammember.setTeam(teamRepository.findById(employee.getTeamId()).get());
		teamMemberRepository.save(teammember);
		//save current manager id
		return savedEmp;
	}
	
	/**
	 * Update emp name and address.
	 * 
	 * @param id
	 * @param updateDetails
	 * @return
	 */
	public Employee updateEmpDetails(int id,Employee updateDetails){
		Employee emp = employeeRepository.findById(id).get();
		emp.setName(updateDetails.getName());
		emp.setAddress(updateDetails.getAddress());
		emp = employeeRepository.save(emp);
		return emp;
	}
	
	/**
	 * Employee promotion or title change.
	 * 
	 * @param id
	 * @param updateDetails
	 * @return
	 */
	public Employee empTitleChange(int id, Employee updateDetails){
		Employee emp = employeeRepository.findById(id).get();
		emp.setTitle(updateDetails.getTitle());
		emp = employeeRepository.save(emp);
		return emp;
	}
	
	public Employee updateVacation(int id, Employee updateDetails){
		Employee emp = employeeRepository.findById(id).get();
		//For updation , bind no. of vacation days availed to emp.getVacationDaysRemaining()
		BigDecimal vacationPending = emp.getVacationDaysRemaining() ;
		if(vacationPending != null && vacationPending.compareTo(updateDetails.getVacationDaysRemaining())>0) {
			vacationPending = vacationPending.subtract(updateDetails.getVacationDaysRemaining());
		}else {
			//TODO
		}
		emp.setVacationDaysRemaining(vacationPending);
		emp = employeeRepository.save(emp);
		return emp;
	}
	
    public List<Employee> getManagerReport(int id){
		System.out.println(id);
        return employeeRepository.findByManagerId(id);
    }
	
	
	public Teammember transferTeam(int empId,int teamId){
		//assuming emp belongs to onky 1 team
		Teammember teamMemberRel = teamMemberRepository.findByEmployee(employeeRepository.findById(empId).get());
		//set new team
		teamMemberRel.setTeam(teamRepository.findById(teamId).get());
		teamMemberRepository.save(teamMemberRel);
		return teamMemberRel;
	}
	
}
