package com.connections.service;

import com.connections.model.Event;

import java.util.List;

public interface EventService {
    Event save(Event event);
    List<Event> findAll();
}
