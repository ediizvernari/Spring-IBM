package com.example.carhub.mapper;

import com.example.carhub.domain.Car;
import com.example.carhub.dto.CarRequestDto;
import com.example.carhub.dto.CarResponseDto;

public class CarMapper {
    public static CarResponseDto toCarResponseDto(Car car) {
        Long ownerId = (car.getOwner() != null)
            ? car.getOwner().getId()
            : null;

        return CarResponseDto.builder()
            .id(car.getId())
            .ownerId(ownerId)
            .make(car.getMake())
            .model(car.getModel())
            .year(car.getProductionYear())
            .price(car.getPrice())
            .build();
    }

    public static Car toCarEntity(CarRequestDto carDto) {
        return Car.builder()
            .make(carDto.getMake())
            .model(carDto.getModel())
            .productionYear(carDto.getYear())
            .price(carDto.getPrice())
            .build();
    }

    public static void updateCarEntity(Car car, CarRequestDto carDto) {
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setProductionYear(carDto.getYear());
        car.setPrice(carDto.getPrice());
    }
}
