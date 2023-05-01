package com.example.registrationservice.repo;

import com.example.registrationservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    Set<Event> findEventsByCreator(String creator);
}