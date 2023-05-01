package com.example.registrationservice.repo;

import com.example.registrationservice.model.Event;
import com.example.registrationservice.model.EventUser;
import com.example.registrationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventUserRepository extends JpaRepository<EventUser, User> {
    EventUser getEventUserByUserAndEvent(User user, Event event);
}