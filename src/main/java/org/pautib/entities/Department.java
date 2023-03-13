package org.pautib.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@NamedQuery(name = Department.FIND_BY_ID, query = "select d from Department d where d.id = :id")
@NamedQuery(name = Department.FIND_BY_NAME, query = "select d from Department d where d.departmentName = :name")
@NamedQuery(name = Department.FIND_BY_CODE, query = "select d from Department d where d.departmentCode = :code")
@NamedQuery(name = Department.LIST_DEPARTMENTS, query = "select d from Department d")
@Access(AccessType.FIELD)
public class Department extends AbstractEntity {

    public static final String FIND_BY_ID = "Department.findById";
    public static final String FIND_BY_NAME = "Department.findByName";
    public static final String FIND_BY_CODE = "Department.findByCode";
    public static final String LIST_DEPARTMENTS = "Department.listDepartments";

    @NotEmpty(message = "Department name must be set")
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    @OrderBy(value = "fullName ASC, dateOfBirth DESC, address.country ASC")
    private List<Employee> employees = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "EMPLOYEE_RANKS")
    @MapKeyJoinColumn(name = "EMP_ID")
    @Column(name = "RANK")
    private Map<Employee, Integer> employeeRanks = new HashMap<>();

    @NotEmpty(message = "Department code must be set")
    @Column(name = "DEPARTMENT_CODE")
    private String departmentCode;

    public String getDepartmentName() { return departmentName; }

    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public List<Employee> getEmployees() { return employees; }

    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    public String getDepartmentCode() { return departmentCode; }

    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }
}
