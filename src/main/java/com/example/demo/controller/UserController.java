package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.security_service.CustomUserDetailService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping("/user")
    public ResponseEntity<Integer> saveUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id){
        try{
            userService.deleteUser(id);
        }catch (DataNotFoundException dataNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id is not found");
        }
        return ResponseEntity.ok().body("Delele Success");
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getInfo(@RequestParam Optional<Integer> id, @RequestParam Optional<Boolean> status, @RequestParam Optional<String> roleName, @RequestParam Optional<String> username){
        System.out.println("value");
        return ResponseEntity.status(HttpStatus.OK).body(userService.getInfo(id, username,roleName, status));
    }
    @GetMapping("/test")
    public  ResponseEntity<?> get(){
        return ResponseEntity.status(HttpStatus.OK).body("kk");
    }
}
