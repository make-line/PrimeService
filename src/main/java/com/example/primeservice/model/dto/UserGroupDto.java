package com.example.primeservice.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserGroupDto {
    private Long id;
    private Set<UserDto> users;
    private Set<UserDto> subs;
    private String name;
    private String definition;
    private Boolean isAdminAddsOnly;
    private UserDto admin;
    private UserGroupDto parentUserGroup;
}
