package org.pautib.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class Project extends AbstractEntity {

    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Column(name = "PROJECT_START_DATE")
    private LocalDateTime projectStartDate;
    @Column(name = "PROJECT_END_DATE")
    private LocalDateTime projectEndDate;

    @ManyToMany
    @JoinTable(name = "PROJ_EMPLOYEES",
            joinColumns = @JoinColumn(name = "PROJ_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMP_ID"))
    private Collection<Employee> employees;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDateTime getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(LocalDateTime projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public LocalDateTime getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(LocalDateTime projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public Collection<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Collection<Employee> employees) {
        this.employees = employees;
    }
}
