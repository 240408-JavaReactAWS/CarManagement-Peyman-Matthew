package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @Column(name = "carId")
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


}
