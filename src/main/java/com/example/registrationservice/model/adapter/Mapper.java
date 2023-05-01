package com.example.registrationservice.model.adapter;

import com.example.registrationservice.model.Event;
import com.example.registrationservice.model.User;
import com.example.registrationservice.model.UserGroup;
import com.example.registrationservice.model.dto.EventDto;
import com.example.registrationservice.model.dto.RegistrationDto;
import com.example.registrationservice.model.dto.UserGroupDto;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {


    User userRegistrationDtoToUser(RegistrationDto registrationDto);

    UserGroup groupDtoToGroup(UserGroupDto userGroupDto);

    UserGroupDto groupToDtoGroup(UserGroup group);

    Event eventDtoToEvent(EventDto eventDto);

    EventDto eventToEventDto(Event event);


}