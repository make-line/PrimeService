package com.example.primeservice.repo;

import com.example.primeservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    Set<Event> findEventsByCreator(String creator);
}