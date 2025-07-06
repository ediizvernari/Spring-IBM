package com.example.carhub.web;

import com.example.carhub.dto.UserRequestDto;
import com.example.carhub.dto.UserResponseDto;
import com.example.carhub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequestDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void>deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/purchase/{carId}")
    public ResponseEntity<UserResponseDto> purchaseCar(@PathVariable Long userId, @PathVariable Long carId) {
        return ResponseEntity.ok(userService.buyCar(userId, carId));
    }

    @PostMapping("/{userId}/sell/{carId}")
    public ResponseEntity<UserResponseDto> sellCar(@PathVariable Long userId, @PathVariable Long carId) {
        return ResponseEntity.ok(userService.sellCar(userId, carId));
    }
}
