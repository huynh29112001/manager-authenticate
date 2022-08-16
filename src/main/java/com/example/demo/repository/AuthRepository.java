package com.example.demo.repository;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AuthRepository extends JpaRepository<Auth, Integer> {
    List<UserDTO> findByName(String roleName);
}
