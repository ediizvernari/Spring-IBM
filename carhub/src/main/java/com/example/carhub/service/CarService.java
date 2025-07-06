package com.example.carhub.service;

import com.example.carhub.domain.Car;
import com.example.carhub.dto.CarRequestDto;
import com.example.carhub.dto.CarResponseDto;
import com.example.carhub.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    private CarResponseDto toCarResponseDto(Car car) {
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

    public List<CarResponseDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        
        return cars.stream()
                .map(this::toCarResponseDto)
                .collect(Collectors.toList());
    }

    public List<CarResponseDto> getAllCarsByOwnerId(Long ownerId) {
        List<Car> cars = carRepository.findAllByOwnerId(ownerId);

        return cars.stream()
            .map(this::toCarResponseDto)
            .collect(Collectors.toList());
    }

    public CarResponseDto getCarDtoById(Long id) {
        Car carEntity = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        return toCarResponseDto(carEntity);
    }

    @Transactional
    public CarResponseDto createCar(CarRequestDto carRequestDto) {
        Car car = Car.builder()
            .owner(null)
            .make(carRequestDto.getMake())
            .model(carRequestDto.getModel())
            .productionYear(carRequestDto.getYear())
            .price(carRequestDto.getPrice())
            .build();

        this.carRepository.save(car);
        return this.toCarResponseDto(car);
    }

    @Transactional
    public CarResponseDto updateCar(Long id, CarRequestDto carDto) {
        Car carEntity = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        
        carEntity.setMake(carDto.getMake());
        carEntity.setModel(carDto.getModel());
        carEntity.setProductionYear(carDto.getYear());
        carEntity.setPrice(carDto.getPrice());

        Car savedCar = carRepository.save(carEntity);
        return this.toCarResponseDto(savedCar); 
    }

    @Transactional
    public void deleteCar(Long id) {
        Car carEntity = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        
        carRepository.delete(carEntity);
    }
}