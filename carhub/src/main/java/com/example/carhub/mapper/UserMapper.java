package com.example.carhub.mapper;

import com.example.carhub.domain.User;
import com.example.carhub.dto.CarResponseDto;
import com.example.carhub.dto.UserRequestDto;
import com.example.carhub.dto.UserResponseDto;

import java.util.List;

public class UserMapper {
    public static UserResponseDto toUserResponseDto(User user, List<CarResponseDto> ownedCarIds) {
        return UserResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .ownedCars(ownedCarIds)
            .build();
    }

    public static User toUserEntity(UserRequestDto userRequestDto, String hashedPassword, String emailLookup) {
        return User.builder()
            .username(userRequestDto.getUsername())
            .email(userRequestDto.getEmail())
            .password(hashedPassword)
            .emailLookup(emailLookup)
            .build();
    }

    public static void updateUserEntity(User user, UserRequestDto userRequestDto, String hashedPassword, String emailLookup) {
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(hashedPassword);
        user.setEmailLookup(emailLookup);
    }
}
