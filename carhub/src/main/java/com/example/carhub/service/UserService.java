package com.example.carhub.service;

import com.example.carhub.domain.User;
import com.example.carhub.domain.Car;
import com.example.carhub.dto.CarResponseDto;
import com.example.carhub.dto.UserRequestDto;
import com.example.carhub.dto.UserResponseDto;
import com.example.carhub.mapper.CarMapper;
import com.example.carhub.mapper.UserMapper;
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
    private final PasswordHasher passwordHasher;
    private final EmailHasher emailHasher;

    private UserResponseDto mapEntityToDto(User user) {
        List<CarResponseDto> ownedCars = carRepository.findAllByOwnerId(user.getId()).stream()
            .map(CarMapper::toCarResponseDto)
            .collect(Collectors.toList());
        return UserMapper.toUserResponseDto(user, ownedCars);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapEntityToDto)
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
            
        userRepository.save(createdUser);
        return UserMapper.toUserResponseDto(createdUser, null);
    }

    public UserResponseDto getUserById(Long id) { 
        User foundUser = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException());
        
        return this.mapEntityToDto(foundUser);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User userToBeUpdated = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException());

        String hashedPassword = passwordHasher.hashPassword(userRequestDto.getPassword());
        String emailLookup = emailHasher.signEmail(userRequestDto.getEmail());

        UserMapper.updateUserEntity(userToBeUpdated, userRequestDto, hashedPassword, emailLookup);
        return this.mapEntityToDto(userToBeUpdated);
    }

    @Transactional
    public void deleteUser(Long id) {
        User userEntity = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException());

        userRepository.delete(userEntity);
    }

    @Transactional
    public UserResponseDto buyCar(Long userId, Long carId) {
        User buyer = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException());
        Car boughtCar = carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException());
        
        boughtCar.setOwner(buyer);
        carRepository.save(boughtCar);

        return this.mapEntityToDto(buyer);
    }

    @Transactional
    public UserResponseDto sellCar(Long userId, Long carId) {
        Car soldCar = carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException());
        
        if (!soldCar.getOwner().getId().equals(userId)) {
            throw new EntityNotFoundException();
        }

        soldCar.setOwner(null);
        User seller = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException());
        
        soldCar.setOwner(null);
        carRepository.save(soldCar);
        
        return this.mapEntityToDto(seller);
    }
}