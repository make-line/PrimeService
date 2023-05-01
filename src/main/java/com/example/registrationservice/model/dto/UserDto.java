package com.example.registrationservice.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private Set<UserGroupDto> groupsDto;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String corpEmail;
    private String phone;
}