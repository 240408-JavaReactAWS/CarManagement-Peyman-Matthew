package com.revature.service;

import com.revature.exception.UserNotFoundException;
import com.revature.entity.Car;
import com.revature.entity.User;
import com.revature.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
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