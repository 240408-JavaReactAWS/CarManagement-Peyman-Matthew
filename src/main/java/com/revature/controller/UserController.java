package com.revature.controller;

import com.revature.entity.User;
import com.revature.entity.Car;
import com.revature.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create/login user
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.status(201).body(newUser);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password)
                .map(user -> ResponseEntity.ok().build()) // Ideally return a token or user details
                .orElseGet(() -> ResponseEntity.badRequest().body("Invalid username or password"));
    }

    // Add a car to favorites
    @PostMapping("/{userId}/favorite-cars/{carId}")
    public ResponseEntity<String> addFavoriteCar(@PathVariable Integer userId, @PathVariable Integer carId) {
        try {
            userService.addFavoriteCar(userId, carId);
            return ResponseEntity.ok("Successfully added car to favorites");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to add to favorites");
        }
    }

    // Get favorite cars
    @GetMapping("/{userId}/favorite-cars")
    public ResponseEntity<Set<Car>> getUserFavoriteCars(@PathVariable Integer userId) {
        try {
            Set<Car> favoriteCars = userService.getUserFavoriteCars(userId);
            return ResponseEntity.ok(favoriteCars);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get ALL favorite cars (if Admin)
    @GetMapping("/favorite-cars/all")
    public ResponseEntity<Set<Car>> getAllUsersFavoriteCars(@RequestHeader("X-User-Admin") boolean isAdmin) {
        if (!isAdmin) {
            return ResponseEntity.status(403).body(null); // Forbidden access
        }
        Set<Car> allFavoriteCars = userService.getAllUsersFavoriteCars();
        return ResponseEntity.ok(allFavoriteCars);
    }
}