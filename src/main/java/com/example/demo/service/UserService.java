package com.example.demo.service;

import com.example.demo.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Integer save(UserDTO userDTO);

    void deleteUser(int id);

    List<UserDTO> getAll();

    List<UserDTO> getInfo(Optional<Integer> id, Optional<String> userName, Optional<String> role, Optional<Boolean> status);

}
