package com.revature.service;

import com.revature.entity.Car;
import com.revature.entity.User;
import com.revature.repository.UserDAO;
import com.revature.repository.CarDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
public class UserService {

    @Autowired
    private final UserDAO userDAO;

    @Autowired
    private final CarDAO carDAO;

    @Autowired
    public UserService(UserDAO userDAO, CarDAO carDAO) {
        this.userDAO = userDAO;
        this.carDAO = carDAO;
    }

    // Create/register user
    public User createUser(User user) {
        return userDAO.save(user);
    }

    // Login
    public Optional<User> login(String username, String password) {
        return userDAO.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }

    // Add car to favorites
    public User addFavoriteCar(Integer userId, Integer carId) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Car car = carDAO.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        user.addFavoriteCar(car);
        return userDAO.save(user);
    }

    // Get favorite cars
    public Set<Car> getUserFavoriteCars(Integer userId) {
        Optional<User> userOptional = userDAO.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getFavoriteCars();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Get all favorite cars
    // Will verify if user is an admin at a higher level
    public Set<Car> getAllUsersFavoriteCars() {
        Set<Car> favoriteCars = new HashSet<>();
        userDAO.findAll().forEach(user -> favoriteCars.addAll(user.getFavoriteCars()));
        return favoriteCars;
    }
}