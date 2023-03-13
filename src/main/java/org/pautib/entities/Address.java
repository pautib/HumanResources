package org.pautib.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(name = "STREET_ADDRESS")
    private String streetAddress;
    @Column(name = "ZIP_CODE")
    private String zipCode;

    private String city;

    private String country;

    private String phone;

    public String getStreetAddress() { return streetAddress; }

    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    public String getZipCode() { return zipCode; }

    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }
}
