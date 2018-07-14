package com.intuit.personnel.management;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.intuit.personnel.management.helpers.JsonHelper;
import com.intuit.personnel.management.model.Employee;
import com.intuit.personnel.management.model.Team;
import com.intuit.personnel.management.model.Teammember;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
@WebMvcTest(PersonnelController.class)
public class PersonnelControllerTest {

	private static final String JSON_CONTENT_TYPE = MediaType.APPLICATION_JSON + ";charset=UTF-8";

	@Autowired
	private WebApplicationContext wac;

	@MockBean
	private PersonnelController personnelController;
	private MockMvc mockMvc;

	@Configuration
	@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
	public static class Config {
		@Bean
		public PersonnelController personnelController() {
			return new PersonnelController();
		}
	}

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testGetEmployee() throws Exception {
		Employee employee = new Employee(1, "EmpOne", "Addr1", "Engineer",BigDecimal.ZERO, 1, 2);

		given(personnelController.getEmployeeById(1)).willReturn(employee);

		mockMvc.perform(MockMvcRequestBuilders.get("/employee/" + 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.empId").exists()).andExpect(jsonPath("$.empId", is(1)))
				.andExpect(jsonPath("$.name", is("EmpOne"))).andDo(print());
	}
	
	/**
	 * Add employee.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateEmployee() throws Exception {
		Employee employee = new Employee(1, "EmpOne", "Addr1", "Engineer",BigDecimal.ZERO, 1, 2);
		given(personnelController.addEmployee(any(Employee.class))).willReturn(employee);
		mockMvc.perform(MockMvcRequestBuilders.post("/employee", employee).accept(MediaType.APPLICATION_JSON)
				.contentType(JSON_CONTENT_TYPE).content(JsonHelper.asJsonString(employee))).andExpect(status().isOk())
				.andExpect(jsonPath("$.empId").exists()).andExpect(jsonPath("$.managerId", is(2)))
				.andExpect(jsonPath("$.teamId", is(1))).andDo(print());

	}
	
	/**
	 * Employee Name Change/ Address change 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateEmpDetails() throws Exception {
		Employee employee = new Employee(1, "EmpOne", "Addr1", "Engineer",BigDecimal.ONE, 1, 2);
		given(personnelController.addEmployee(any(Employee.class))).willReturn(employee);
		
		Employee updateEmp = new Employee(0, "EmpNew", "AddrNew", "",BigDecimal.ZERO);
		Employee updated = new Employee(1, "EmpNew", "AddrNew", "Engineer",BigDecimal.ONE, 1, 2);
		
		//update name and address
		given(personnelController.updateEmpDetails(eq(1),any(Employee.class) )).willReturn(updated);
		mockMvc.perform(MockMvcRequestBuilders.put("/employee/" + 1).accept(MediaType.APPLICATION_JSON)
				.content(JsonHelper.asJsonString(updateEmp)).contentType(JSON_CONTENT_TYPE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.empId").exists()).andExpect(jsonPath("$.empId", is(1)))
				.andExpect(jsonPath("$.name", is("EmpNew")))
				.andExpect(jsonPath("$.address", is("AddrNew")))
				.andDo(print());

	}
	
	/**
	 * Employee promotion/title change.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateEmpTitle() throws Exception {
		Employee employee = new Employee(1, "EmpOne", "Addr1", "Engineer",BigDecimal.ZERO, 1, 2);
		given(personnelController.addEmployee(any(Employee.class))).willReturn(employee);
		
		Employee updateEmp = new Employee(0, "", "", "Senior Engineer", BigDecimal.ZERO);
		Employee updated = new Employee(1, "EmpOne", "Addr1", "Senior Engineer",BigDecimal.ZERO, 1, 2);
		
		//update emp title
		given(personnelController.empTitleChange(eq(1),any(Employee.class))).willReturn(updated);
		mockMvc.perform(MockMvcRequestBuilders.put("/employee/" + 1+"/title").accept(MediaType.APPLICATION_JSON)
				.content(JsonHelper.asJsonString(updateEmp)).contentType(JSON_CONTENT_TYPE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.empId").exists()).andExpect(jsonPath("$.empId", is(1)))
				.andExpect(jsonPath("$.name", is("EmpOne")))
				.andExpect(jsonPath("$.address", is("Addr1")))
				.andDo(print());

	}
	
	/**
	 * Employee goes on vacation.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateVacation() throws Exception {
		Employee employee = new Employee(1, "EmpOne", "Addr1", "Engineer",BigDecimal.ONE, 1, 2);
		given(personnelController.addEmployee(any(Employee.class))).willReturn(employee);
		
		Employee updateEmp = new Employee(0, "", "", "",new BigDecimal(5));
		Employee updated = new Employee(1, "EmpOne", "Addr1", "Engineer",new BigDecimal(10), 1, 2);
		
		//update name and address
		given(personnelController.updateVacation(eq(1),any(Employee.class))).willReturn(updated);
		mockMvc.perform(MockMvcRequestBuilders.put("/employee/" + 1 +"/vacation").accept(MediaType.APPLICATION_JSON)
				.content(JsonHelper.asJsonString(updateEmp)).contentType(JSON_CONTENT_TYPE)).andExpect(status().isOk())
		.andDo(print());

	}
	
	/**
	 * Employee goes on vacation.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTransferTeam() throws Exception {
		Employee employee = new Employee(1, "EmpOne", "Addr1", "Engineer",new BigDecimal(10), 1, 2);
		given(personnelController.addEmployee(any(Employee.class))).willReturn(employee);
		
		Employee updateEmp = new Employee(1, "EmpOne", "Addr1", "Engineer",new BigDecimal(10), 2, 2);
		Teammember teamMembers = new Teammember();
		teamMembers.setId(1);
		teamMembers.setEmployee(updateEmp);
		teamMembers.setTeam(new Team());
		
		//verify teamId
		given(personnelController.transferTeam(eq(1),eq(2))).willReturn(teamMembers);
		mockMvc.perform(MockMvcRequestBuilders.put("/employee/" + 1 +"/team/"+2).accept(MediaType.APPLICATION_JSON)
				.content("{}").contentType(JSON_CONTENT_TYPE)).andExpect(status().isOk())
		.andExpect(jsonPath("$.employee").exists()).andExpect(jsonPath("$.employee.teamId", is(2)))
		.andDo(print());

	}
	
	/**
	 * Manager Report- list of employees test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetManagerReport() throws Exception {
		
		List<Employee> managerReport =  new ArrayList<Employee>();
		managerReport.add(new Employee(1, "EmpOne", "Addr1", "Engineer",BigDecimal.ONE, 1, 2));
		managerReport.add(new Employee(3, "EmpThree", "Addr3", "Engineer",BigDecimal.ONE, 1, 2));
		given(personnelController.getManagerReport(2)).willReturn(managerReport);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/manager/" + 2 + "/employee").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print());
	}
}
