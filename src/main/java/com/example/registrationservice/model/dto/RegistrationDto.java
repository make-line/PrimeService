package com.example.registrationservice.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RegistrationDto {
    private Long id;
    private Set<UserGroupDto> groups;
    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private String email;
    private String corpEmail;
    private String phone;
}
