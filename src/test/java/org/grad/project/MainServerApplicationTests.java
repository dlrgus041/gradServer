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
//		employee.setName("김익현");
//		employee.setPhone("01052654815");
//		employee.setAddress("경기도 화성시");
//		er.save(employee);
//	}

//	@Test
//	void employeeDeleteTest() {
//		er.deleteById(5L);
//	}
}
