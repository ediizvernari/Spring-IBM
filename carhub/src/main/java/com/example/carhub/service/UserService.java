package com.example.carhub.service;

import com.example.carhub.domain.User;
import com.example.carhub.domain.Car;
import com.example.carhub.dto.UserRequestDto;
import com.example.carhub.dto.UserResponseDto;
import com.example.carhub.repository.UserRepository;
import com.example.carhub.repository.CarRepository;
import com.example.carhub.security.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CarService carService;
    private final PasswordHasher passwordHasher;
    private final EmailHasher emailHasher;

    private UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .ownedCars(carService.getAllCarsByOwnerId(user.getId()))
            .build();
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAllEntities().stream()
                .map(this::toUserResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        String hashedPassword = passwordHasher.hashPassword(userRequestDto.getPassword());
        String emailLookup = emailHasher.signEmail(userRequestDto.getEmail());

        User createdUser = User.builder()
            .username(userRequestDto.getUsername())
            .email(userRequestDto.getEmail())
            .emailLookup(emailLookup)
            .password(hashedPassword)
            .build();
            
        userRepository.create(createdUser);

        return this.toUserResponseDto(createdUser);
    }

    public UserResponseDto getUserById(Long id) { 
        User foundUser = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException());
        
        return this.toUserResponseDto(foundUser);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User userEntity = userRepository.getEntityById(id)
            .orElseThrow(() -> new EntityNotFoundException());

        String hashedPassword = passwordHasher.hashPassword(userRequestDto.getPassword());
        String emailLookup = emailHasher.signEmail(userRequestDto.getEmail());

        userEntity.setUsername(userRequestDto.getUsername());
        userEntity.setPassword(hashedPassword);
        userEntity.setEmail(userRequestDto.getEmail());
        userEntity.setEmailLookup(emailLookup);
        
        User updatedUser = userRepository.save(userEntity);
        return this.toUserResponseDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User userEntity = userRepository.getEntityById(id)
            .orElseThrow(() -> new EntityNotFoundException());

        userRepository.delete(userEntity);
    }

    @Transactional
    public UserResponseDto buyCar(Long userId, Long carId) {
        User buyer = userRepository.getEntityById(userId)
            .orElseThrow(() -> new EntityNotFoundException());
        Car boughtCar = carRepository.getEntityById(carId)
            .orElseThrow(() -> new EntityNotFoundException());
        
        boughtCar.setOwner(buyer);
        carRepository.save(boughtCar);

        return this.toUserResponseDto(buyer);
    }

    @Transactional
    public UserResponseDto sellCar(Long userId, Long carId) {
        Car soldCar = carRepository.getEntityById(carId)
            .orElseThrow(() -> new EntityNotFoundException());
        
        if (!soldCar.getOwner().getId().equals(userId)) {
            throw new EntityNotFoundException();
        }

        soldCar.setOwner(null);
        User seller = userRepository.getEntityById(userId)
            .orElseThrow(() -> new EntityNotFoundException());
        
        soldCar.setOwner(null);
        carRepository.save(soldCar);
        
        return this.toUserResponseDto(seller);
    }
}