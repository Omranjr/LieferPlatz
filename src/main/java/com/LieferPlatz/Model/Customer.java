package com.LieferPlatzProject.LieferPlatz.Model;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String street;
    private String buildingNumber;
    private String city;
    private String zipCode;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password; // For simplicity, you may want to encrypt and secure this

    // Getters and Setters, Constructors


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Customer() {}

    @Override
    public String toString() {
        return "Customer{" +
                "street='" + street + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
