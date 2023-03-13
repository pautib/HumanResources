package org.pautib.services;

import org.pautib.entities.ApplicationUser;
import org.pautib.entities.Department;
import org.pautib.entities.Employee;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.pautib.entities.Department.FIND_BY_CODE;

/**
 * The @Stateless annotation is to mark that this bean doesn't keep state. The invocation of every method is independent.
 * The result of one does not depend on the other. The bean is likely to be discarded when not used. And another instance
 * is created again.
 */
@Stateless
@Transactional
public class QueryServiceImpl implements QueryService {

    @Inject
    private SecurityUtil securityUtil;

    @Inject
    private EntityManager entityManager;

    @PostConstruct
    private void init() {}

    @PreDestroy
    private void destroy() {}

    public Department findDepartmentById(Long id) {
        Department department = entityManager.find(Department.class, id);
        return department;
    }

    public Department findDepartmentByCode(String departmentCode) {

        Department dep = entityManager
                .createNamedQuery(FIND_BY_CODE, Department.class)
                .setParameter("code", departmentCode)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        return dep;
    }

    public Employee findEmployeeById(Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        return employee;
    }
    public Employee findEmployeeByNIE(String socialSecurityNum) {

        Employee employee = entityManager
                .createNamedQuery(Employee.FIND_BY_NIE, Employee.class)
                .setParameter("nie", socialSecurityNum)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        return employee;
    }

    @Transactional
    public List<Employee> getEmployees() {

        List<Employee> employees = entityManager
                                        .createNamedQuery(Employee.FIND_ALL, Employee.class)
                                        .getResultList();
        return employees;
    }

    public List<Department> getDepartments() {
        return entityManager.createNamedQuery(Department.LIST_DEPARTMENTS, Department.class).getResultList();
    }

    public boolean authenticateUser(String email, String plainTextPassword) {

        ApplicationUser user = entityManager
                .createNamedQuery(ApplicationUser.FIND_USER_BY_CREDENTIALS, ApplicationUser.class)
                .setParameter("email", email.toLowerCase())
                .getResultList()
                .get(0);

        return securityUtil.passwordsMatch(user.getPassword(), user.getSalt(), plainTextPassword);
    }

    public static Integer getDummyStaticValue() {
        return 5;
    }

}
