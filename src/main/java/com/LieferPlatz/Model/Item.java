package com.LieferPlatzProject.LieferPlatz.Model;

import jakarta.persistence.*;
import org.apache.tomcat.util.codec.binary.Base64;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String name;
    @Column(nullable = false)
    private Double price;

    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }



    // getters and setters
    @ManyToOne
    @JoinColumn(name = "restaurant_id" ,nullable = false)
    private Restaurant restaurant;


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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    // Constructors

    public String generateBase64Image() {
        return Base64.encodeBase64String(this.image);
    }
    public Item() {}


}