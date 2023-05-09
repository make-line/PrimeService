package com.example.primeservice.repo;

import com.example.primeservice.model.Event;
import com.example.primeservice.model.EventUser;
import com.example.primeservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventUserRepository extends JpaRepository<EventUser, User> {
    EventUser getEventUserByUserAndEvent(User user, Event event);
}