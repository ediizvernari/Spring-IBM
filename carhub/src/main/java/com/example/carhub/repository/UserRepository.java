package com.example.carhub.repository;

import com.example.carhub.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    
}
