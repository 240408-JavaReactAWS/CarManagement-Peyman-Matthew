package com.revature.service;

import com.revature.entity.Car;
import com.revature.repository.CarDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarDAO carDAO;

    @Autowired
    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    // Create car
    public Car createCar(Car car) {
        return carDAO.save(car);
    }

    // View all cars
    public List<Car> getAllCars() {
        return carDAO.findAll();
    }

    // View car by ID
    public Optional<Car> findCarById(Integer id) {
        return carDAO.findById(id);
    }

    // Update car by Id
    public Car updateCar(Integer id, Car updatedCar) {
        return carDAO.findById(id)
                .map(car -> {
                    car.setMake(updatedCar.getMake());
                    car.setModel(updatedCar.getModel());
                    car.setYear(updatedCar.getYear());
                    car.setColor(updatedCar.getColor());
                    car.setSpecificDetails(updatedCar.getSpecificDetails());
                    return carDAO.save(car);
                }).orElseThrow(() -> new RuntimeException("Car not found"));
    }

    // Delete car by Id
    public void deleteCar(Integer id) {
        carDAO.deleteById(id);
    }
}