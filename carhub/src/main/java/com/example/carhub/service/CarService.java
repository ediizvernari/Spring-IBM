package com.example.carhub.service;

import com.example.carhub.domain.Car;
import com.example.carhub.dto.CarRequestDto;
import com.example.carhub.dto.CarResponseDto;
import com.example.carhub.mapper.CarMapper;
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

    public List<CarResponseDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        
        return cars.stream()
                .map(CarMapper::toCarResponseDto)
                .collect(Collectors.toList());
    }

    public List<CarResponseDto> getAllCarsByOwnerId(Long ownerId) {
        List<Car> cars = carRepository.findAllByOwnerId(ownerId);

        return cars.stream()
            .map(CarMapper::toCarResponseDto)
            .collect(Collectors.toList());
    }

    public CarResponseDto getCarDtoById(Long id) {
        Car carEntity = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        return CarMapper.toCarResponseDto(carEntity);
    }

    @Transactional
    public CarResponseDto createCar(CarRequestDto carRequestDto) {
        Car carEntity = CarMapper.toCarEntity(carRequestDto);
        this.carRepository.save(carEntity);
        return CarMapper.toCarResponseDto(carEntity);
    }

    @Transactional
    public CarResponseDto updateCar(Long id, CarRequestDto carDto) {
        Car carEntity = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        
        CarMapper.updateCarEntity(carEntity, carDto);
        Car savedCar = carRepository.save(carEntity);
        return CarMapper.toCarResponseDto(savedCar); 
    }

    @Transactional
    public void deleteCar(Long id) {
        Car carEntity = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        
        carRepository.delete(carEntity);
    }
}