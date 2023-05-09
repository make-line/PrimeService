package com.example.primeservice.model.adapter;

import com.example.primeservice.model.Event;
import com.example.primeservice.model.User;
import com.example.primeservice.model.UserGroup;
import com.example.primeservice.model.dto.EventDto;
import com.example.primeservice.model.dto.RegistrationDto;
import com.example.primeservice.model.dto.UserGroupDto;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {


    User userRegistrationDtoToUser(RegistrationDto registrationDto);

    UserGroup groupDtoToGroup(UserGroupDto userGroupDto);

    UserGroupDto groupToDtoGroup(UserGroup group);

    Event eventDtoToEvent(EventDto eventDto);

    EventDto eventToEventDto(Event event);


}