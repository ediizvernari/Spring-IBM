package com.example.carhub.web;

import com.example.carhub.dto.CarRequestDto;
import com.example.carhub.dto.CarResponseDto;
import com.example.carhub.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Response Entity is not needed but I placed it to practise
//Cu el fac raspunsuri custom la cereri http

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    
    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<CarResponseDto>> ListAllCarsByOwnerId(@PathVariable Long ownerId) {
        return ResponseEntity.ok(carService.getAllCarsByOwnerId(ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarDtoById(id));
    }

    @PostMapping
    public ResponseEntity<CarResponseDto> create(@Valid @RequestBody CarRequestDto carDto) {
        return ResponseEntity.ok(carService.createCar(carDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDto> update(@PathVariable Long id, @Valid @RequestBody CarRequestDto carDto) {
        return ResponseEntity.ok(carService.updateCar(id, carDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}