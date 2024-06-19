package com.example.api.respositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.domain.event.Event;

public interface EventRepositories extends JpaRepository<Event,UUID> {
    
}
