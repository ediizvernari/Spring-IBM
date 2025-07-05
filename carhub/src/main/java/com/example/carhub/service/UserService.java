package com.example.carhub.service;

import com.example.carhub.domain.User;
import com.example.carhub.dto.UserDto;
import com.example.carhub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    private User dtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    private UserDto userToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAllEntities().stream()
                .map(this::userToDto)
                .toList();
    }

    public UserDto createUser(UserDto userDto) {
        User createdUser = userRepository.create(dtoToUser(userDto));
        return this.userToDto(createdUser);
    }

    public UserDto getUserById(Long id) {
        User foundUser = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException());
        
        return this.userToDto(foundUser);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User userEntity = userRepository.getEntityById(id)
            .orElseThrow(() -> new EntityNotFoundException());

        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        
        User updatedUser = userRepository.save(userEntity);
        return this.userToDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User userEntity = userRepository.getEntityById(id)
            .orElseThrow(() -> new EntityNotFoundException());

        userRepository.delete(userEntity);
    }
}
