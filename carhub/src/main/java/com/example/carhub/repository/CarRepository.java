package com.example.carhub.repository;

import com.example.carhub.domain.Car;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends BaseRepository<Car, Long>{
    List<Car> findAllByOwnerId(Long ownerId);
}
