package org.pautib.entities;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.config.PropertyOrderStrategy;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.*;


@Entity
@NamedQuery(name = Employee.FIND_BY_NIE, query = "select e from Employee e where e.socialSecurityNumber = :nie")
@NamedQuery(name = Employee.FIND_ALL, query = "select e from Employee e left join fetch e.projects left join fetch e.qualifications")
@JsonbPropertyOrder(PropertyOrderStrategy.LEXICOGRAPHICAL)
public class Employee extends AbstractEntity {

    public static final String FIND_BY_NIE = "Employee.findByNIE";
    public static final String FIND_ALL = "Employee.findAll";

    public Employee() { super(); }

    public Employee(String fullName, String socialSecurityNumber, Date dateOfBirth, BigDecimal basicSalary, Date hiredDate) {
        this.fullName = fullName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.dateOfBirth = dateOfBirth;
        this.basicSalary = basicSalary;
        this.hiredDate = hiredDate;
    }

    @NotEmpty
    @Size(max = 40, message = "Full name must be less than 40 characters")
    @Column(name = "FULL_NAME")
    //@Basic
    private String fullName;

    @NotEmpty(message = "Social security number must be set")
    @Column(name = "SOCIAL_SECURITY_NUMBER")
    private String socialSecurityNumber;

    @NotNull(message = "Date of birth must be set")
    @Past(message = "Date of birth must be in the past")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @NotNull(message = "Basic salary must be set")
    @DecimalMin(value = "500", message = "Basic salary must be equal to or exceed 500")
    @Column(name = "BASIC_SALARY")
    private BigDecimal basicSalary;

    @NotNull(message = "Hired date must be set")
    @PastOrPresent(message = "Hired date must be in the past or present")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    @Column(name = "HIRED_DATE")
    private Date hiredDate;

    @OneToOne(cascade = CascadeType.DETACH)
    private Employee reportsTo;

    @Transient
    private Set<Employee> subordinates = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "EMPLOYMENT_TYPE")
    private EmploymentType employmentType;

    @Embedded
    private Address address;

    private int age;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private Set<Allowance> employeeAllowances = new LinkedHashSet<>();

    @OneToOne
    @JoinColumn(name = "CURRENT_PAYSLIP_ID")
    private Payslip currentPayslip;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ParkingSpace parkingSpace;

    @ElementCollection(targetClass = Qualifications.class)
    @CollectionTable(name = "QUALIFICATIONS", joinColumns = @JoinColumn(name = "EMP_ID"))
    private Collection<Qualifications> qualifications = new ArrayList<>();

    /**
    @ElementCollection
    @Column(name = "NICKY")
    private List<String> nickNames = new ArrayList<>();
    */
    @JsonbTransient
    @Transient
    @OneToMany
    private Collection<Payslip> pastPayslips = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "EMP_PHONE_NUMBERS")
    @MapKeyColumn(name = "PHONE_TYPE")
    @Column(name = "PHONE_NUMBER")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<PhoneType, String> employeePhoneNumbers = new HashMap<>();

    @ManyToMany(mappedBy = "employees")
    private Set<Project> projects;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;

    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "DEPT_ID")
    @Valid
    private Department department;

    public Set<Allowance> getEmployeeAllowances() {
        return employeeAllowances;
    }

    public void setEmployeeAllowances(Set<Allowance> employeeAllowances) {
        this.employeeAllowances = employeeAllowances;
    }

    /**
    public List<String> getNickNames() {
        return nickNames;
    }

    public void setNickNames(List<String> nickNames) {
        this.nickNames = nickNames;
    }
    */
    public Collection<Qualifications> getQualifications() {
        return qualifications;
    }

    public void setQualifications(Collection<Qualifications> qualifications) {
        this.qualifications = qualifications;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Payslip getCurrentPayslip() {
        return currentPayslip;
    }

    public void setCurrentPayslip(Payslip currentPayslip) {
        this.currentPayslip = currentPayslip;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public Collection<Payslip> getPastPayslips() { return pastPayslips; }

    public void setPastPayslips(Collection<Payslip> pastPayslips) { this.pastPayslips = pastPayslips; }

    public Set<Project> getProjects() { return projects; }

    public void setProjects(Set<Project> projects) { this.projects = projects; }

    public Department getDepartment() { return department; }

    public void setDepartment(Department department) { this.department = department; }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) { this.socialSecurityNumber = socialSecurityNumber; }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Employee getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(Employee reportsTo) {
        this.reportsTo = reportsTo;
    }

    public Set<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
