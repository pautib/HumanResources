package org.pautib.services;

import org.pautib.entities.*;

public interface PersistenceService {

    void saveEmployee(Employee employee);

    void saveEmployee(Employee employee, ParkingSpace parkingSpace);

    void updateEmployee(Employee employee);

    void deleteEmployee(Employee employee);

    void removeParkingSpace(Long employeeId);

    void updateDepartment(Department department);

    void deleteDepartment(Department department);

    void saveUser(ApplicationUser applicationUser);

    void deleteEntity(AbstractEntity entity);

    void saveDepartment(Department d);
}
