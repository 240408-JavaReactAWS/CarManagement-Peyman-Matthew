package com.revature.controller;

import com.revature.entity.Car;
import com.revature.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    // Create car
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car newCar = carService.createCar(car);
        return ResponseEntity.ok(newCar);
    }

    // View all cars
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id) {
        Optional<Car> carOptional = carService.findCarById(id);
        if (carOptional.isPresent()) {
            return ResponseEntity.ok(carOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update car by ID
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Integer id, @RequestBody Car car) {
        try {
            Car updatedCar = carService.updateCar(id, car);
            return ResponseEntity.ok(updatedCar);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete car by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
        try {
            carService.deleteCar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}