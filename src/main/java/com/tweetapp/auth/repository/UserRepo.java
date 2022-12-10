package com.tweetapp.auth.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tweetapp.auth.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    List<User> findAll();
    
    @Query(value="SELECT * FROM users WHERE email LIKE %?%;",nativeQuery=true)
    List<User> findByUserName(String username);
    
    
   
}
