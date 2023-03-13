package org.pautib.services;

import org.pautib.entities.Department;
import org.pautib.entities.Employee;

import java.util.List;

public interface QueryService {

    Department findDepartmentById(Long id);

    Department findDepartmentByCode(String code);

    List<Department> getDepartments();

    Employee findEmployeeById(Long id);

    Employee findEmployeeByNIE(String socialSecurityNum);

    List<Employee> getEmployees();

    boolean authenticateUser(String email, String plainTextPassword);
}
