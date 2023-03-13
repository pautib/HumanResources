package org.pautib.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import org.joda.time.DateTime;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.util.Date;

public class EmployeeDto {

    @Schema(name = "Name", type="string", description="Name of the employee")
    private String name;

    @Schema(name = "firstLastName", type="string", description="First last name of the employee")
    private String firstLastName;

    @Schema(name = "secondLastName", type="string", description="Second last name of the employee")
    private String secondLastName;

    @Schema(name = "NIE", type="string", description="NIE or social security number of the employee")
    private String socialSecurityNumber;

    @Schema(name = "Age", type="int", description="Employee's age")
    private int age;

    @Schema(name = "departmentCode", type="string", description="Department code")
    private String departmentCode;

    @Schema(name = "streetAddress", type="string", description="Street Address")
    private String streetAddress;

    @Schema(name = "zipCode", type="string", description="Zip code")
    private String zipCode;

    @Schema(name = "City", type="string", description="City")
    private String city;

    @Schema(name = "Country", type="string", description="Country")
    private String country;

    @Schema(name = "Phone", type="string", description="Phone")
    private String phone;

    @Schema(name = "dateOfBirth", type="date", description="Date of birth")
    private Date dateOfBirth;

    @Schema(name = "basicSalary", type="BigDecimal", description="Basic Salary")
    private BigDecimal basicSalary;

    @Schema(name = "hiredDate", type="DateTime", description="The date the employee was hired")
    private DateTime hiredDate;

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public DateTime getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(DateTime hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
