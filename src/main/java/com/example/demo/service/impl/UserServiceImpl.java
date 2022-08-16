package com.example.demo.service.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Auth;
import com.example.demo.entity.User;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.AuthRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthRepository authRepository;


    @Override
    public Integer save(UserDTO userDTO) {
        User user = new User();
        try {
            if (Objects.equals(userDTO.getUsername(), userDTO.getUsername().toLowerCase(Locale.ROOT))) {
                if (userRepository.findByUsername(userDTO.getUsername()).isEmpty())
                    user.setUsername(userDTO.getUsername());
                else throw new DataNotFoundException("Username is exist");
            } else throw new DataFormatException("Username is not format");

            if (Objects.equals(userDTO.getEmail(), userDTO.getEmail().toLowerCase(Locale.ROOT))) {
                if (getUserByEmail(userDTO.getEmail()).size() == 0)
                    user.setEmail(userDTO.getEmail());
                else throw new DataNotFoundException("Email is exist");
            } else throw new DataFormatException("Email is not format");

            user.setFullname(user.getFullname());
            user.setStatus(userDTO.getStatus());
            if (userDTO.getPassword().length() >= 8)
                user.setPassword(userDTO.getPassword());
            else throw new DataFormatException("Password is not format");
            List<Auth> listAuth = new ArrayList<>();
            for (Integer index :
                    userDTO.getAuth_id()) {
                listAuth.add(authRepository.findById(index).orElseThrow(DataNotFoundException::new));
            }
            user.setListAuth(listAuth);
        } catch (Exception exception) {
            return 0;
        }
        return userRepository.save(user).getId();
    }

    private List<UserDTO> getDetailUser(Integer id) {
        return userRepository.findById(id).map(userMapper::toDTO).stream().collect(Collectors.toList());
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(DataNotFoundException::new);
        if (user.getId() == null)
            throw new DataNotFoundException();
        else userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getInfo(Optional<Integer> id, Optional<String> userName, Optional<String> role, Optional<Boolean> status) {
        if (!role.isPresent()) {
            return userRepository.getInfoNotRoleFromDB(id.orElse(null), userName.orElse(null), status.orElse(null)).stream().map(userMapper::toDTO).collect(Collectors.toList());
        } else
            return userRepository.getInfoFromDB(id.orElse(null), userName.orElse(null), role.orElse(null), status.orElse(null)).stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    private List<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email).stream().map(userMapper::toDTO).collect(Collectors.toList());
    }
}
