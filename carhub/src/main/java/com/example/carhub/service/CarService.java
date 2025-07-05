package com.example.carhub.service;

import com.example.carhub.domain.Car;
import com.example.carhub.dto.CarDto;
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

    private CarDto carToDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .make(car.getMake())
                .model(car.getModel())
                .year(car.getProductionYear())
                .price(car.getPrice())
                .build();
    }

    private Car dtoToCar(CarDto carDto) {
        return Car.builder()
                .id(carDto.getId())
                .make(carDto.getMake())
                .model(carDto.getModel())
                .productionYear(carDto.getYear())
                .price(carDto.getPrice())
                .build();
    }

    public List<CarDto> getAllCars() {
        List<Car> cars = carRepository.findAllEntities();
        return cars.stream()
                .map(this::carToDto)
                .collect(Collectors.toList());
    }

    public CarDto getCarDtoById(Long id) {
        Car carEntity = carRepository.getEntityById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        return carToDto(carEntity);
    }

    @Transactional
    public CarDto createCar(CarDto carDto) {
        Car car = carRepository.create(dtoToCar(carDto));
        return this.carToDto(car);
    }

    @Transactional
    public CarDto updateCar(Long id, CarDto carDto) {
        Car carEntity = carRepository.getEntityById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        
        carEntity.setMake(carDto.getMake());
        carEntity.setModel(carDto.getModel());
        carEntity.setProductionYear(carDto.getYear());
        carEntity.setPrice(carDto.getPrice());

        Car savedCar = carRepository.save(carEntity);
        return this.carToDto(savedCar); 
    }

    @Transactional
    public void deleteCar(Long id) {
        Car carEntity = carRepository.getEntityById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        
        carRepository.delete(carEntity);
    }
}
