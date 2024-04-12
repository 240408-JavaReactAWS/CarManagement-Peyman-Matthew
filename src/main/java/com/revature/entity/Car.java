package com.revature.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String color;

    @Column(nullable = true)
    private String specificDetails;

    @ManyToMany(mappedBy = "favoriteCars")
    private Set<User> likedByUsers = new HashSet<>();

    public Car() {
    }

    public Car(String make, String model, int year, String color, String specificDetails) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.specificDetails = specificDetails;
        // likedByUsers will be managed by methods
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSpecificDetails(String specificDetails) {
        this.specificDetails = specificDetails;
    }

    public String getSpecificDetails() {
        return specificDetails;
    }

    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    // Helper methods
    public void addLikedByUser(User user) {
        this.likedByUsers.add(user);
        user.getFavoriteCars().add(this);
    }

    public void removeLikedByUser(User user) {
        this.likedByUsers.remove(user);
        user.getFavoriteCars().remove(this);
    }
}
