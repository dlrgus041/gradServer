package org.grad.project;

import org.grad.project.domain.Employee;
import org.grad.project.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@SpringBootTest
@Transactional
class MainServerApplicationTests {

	@Autowired EmployeeRepository er;

//	@Test
//	void employeeInsertTest() {
//		Employee employee = new Employee();
//		employee.setId(5L);
//		employee.setName("Andrew");
//		employee.setPhone("01012345678");
//		employee.setAddress("Los Angeles");
//		er.save(employee);
//	}

//	@Test
//	void employeeDeleteTest() {
//		er.deleteById(5L);
//	}

//	@Test
//	void employeeSearch() {
//		er.searchByName("n");
//	}

	@Test
	void employeeVaccineSearch(){
		er.findByVaccine(true);
	}
}
