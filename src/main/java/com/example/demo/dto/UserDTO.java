package com.example.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String fullname;
    private Boolean status;

    private Integer auth_id[];

}
