package com.example.carhub.repository;

import com.example.carhub.domain.Car;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends BaseRepository<Car, Long>{
    
}
