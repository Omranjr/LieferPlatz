package com.LieferPlatzProject.LieferPlatz.Model;

import jakarta.persistence.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String radius;
    private String street;

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    private String buildingNumber;
    private String city;
    private String zipCode;
    private String description;
    private byte[] image;

    @Transient
    private MultipartFile multipartImage;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private LocalTime openingHours;
    private LocalTime closingHours;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Item> menu;


    // Getters and Setters,

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
            return email;
    }
    public void setEmail(String email) {
            this.email = email;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getMenu() {
        return menu;
    }

    public void setMenu(List<Item> menu) {
        this.menu = menu;
    }

    public LocalTime getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(LocalTime openingHours) {
        this.openingHours = openingHours;
    }

    public LocalTime getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(LocalTime closingHours) {
        this.closingHours = closingHours;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String generateBase64Image() {
        return Base64.encodeBase64String(this.image);
    }

    public MultipartFile getMultipartImage() {
        return multipartImage;
    }

    public void setMultipartImage(MultipartFile multipartImage) {
        this.multipartImage = multipartImage;
    }

    // Constructors
    public Restaurant() {}
}
