package com.example.carhub.web;

import com.example.carhub.dto.CarRequestDto;
import com.example.carhub.dto.CarResponseDto;
import com.example.carhub.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    
    @GetMapping
    public List<CarResponseDto> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public CarResponseDto getById(@PathVariable Long id) {
        return carService.getCarDtoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponseDto create(@Valid @RequestBody CarRequestDto carDto) {
        return carService.createCar(carDto);
    }

    @PutMapping("/{id}")
    public CarResponseDto update(@PathVariable Long id, @Valid @RequestBody CarRequestDto carDto) {
        return carService.updateCar(id, carDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        carService.deleteCar(id);
    }
}