package org.pautib.services;

import org.pautib.entities.*;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Map;

/**
@DataSourceDefinition(
        name = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        className = "org.h2.Driver",
        url = "java:jboss/datasources/ExampleDS",
        user = "sa",
        password = "sa")
 */
@Stateless
public class PersistenceServiceImpl implements PersistenceService {

    @Inject
    private SecurityUtil securityUtil;

    @Inject
    private EntityManager entityManager;

    @Inject
    private QueryService queryService;

    public void saveEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    public void updateEmployee(Employee employee) {
        entityManager.merge(employee);
    }

    public void deleteEmployee(Employee employee) {
        entityManager.remove(entityManager.contains(employee) ? employee : entityManager.merge(employee));
    }

    public void saveEmployee(Employee employee, ParkingSpace parkingSpace) {
        employee.setParkingSpace(parkingSpace);
        entityManager.persist(employee);
    }

    public void saveDepartment(Department department) {

        entityManager.persist(department);
    }

    public void removeParkingSpace(Long employeeId) {
        Employee employee = queryService.findEmployeeById(employeeId);
        ParkingSpace parkingSpace = employee.getParkingSpace();

        employee.setParkingSpace(null);
        entityManager.remove(parkingSpace);
    }

    public void saveUser(ApplicationUser applicationUser) {

        Map<String, String> credMap = securityUtil.hashPassword(applicationUser.getPassword());

        applicationUser.setPassword(credMap.get("hashedPassword"));
        applicationUser.setSalt(credMap.get("salt"));

        if (applicationUser.getId() == null) {
            entityManager.persist(applicationUser);
        } else {
            entityManager.merge(applicationUser);
        }

    }

    public void updateDepartment(Department department) {
        entityManager.merge(department);
    }

    public void deleteDepartment(Department department) {
        entityManager.remove(entityManager.contains(department) ? department : entityManager.merge(department));
    }

    public void deleteEntity(AbstractEntity entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
