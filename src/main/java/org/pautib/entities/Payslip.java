package org.pautib.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Payslip extends AbstractEntity {

    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
    @Column(name = "PAY_PERIOD")
    private LocalDate payPeriod;

    @Column(name = "TOTAL_ALLOWANCES")
    private BigDecimal totalAllowances;
    @Column(name = "TOTAL_DEDUCTIONS")
    private BigDecimal totalDeductions;
    @Column(name = "BASIC_SALARY")
    private BigDecimal basicSalary;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public LocalDate getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(LocalDate payPeriod) {
        this.payPeriod = payPeriod;
    }

    public BigDecimal getTotalAllowances() {
        return totalAllowances;
    }

    public void setTotalAllowances(BigDecimal totalAllowances) {
        this.totalAllowances = totalAllowances;
    }

    public BigDecimal getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(BigDecimal totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }
}
