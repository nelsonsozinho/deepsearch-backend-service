package com.axreng.backend.engine.database;

import com.axreng.backend.model.Task;

import java.util.HashSet;
import java.util.Set;

public class EventRepositoryConcrete implements Repository<Task, String> {

    private static EventRepositoryConcrete eventDatabase;

    private final Set<Task> events;

    private EventRepositoryConcrete() {
        this.events = new HashSet<>();
    }

    public static EventRepositoryConcrete getInstance() {
        if(eventDatabase == null) {
            eventDatabase = new EventRepositoryConcrete();
        }
        return eventDatabase;
    }

    @Override
    public synchronized String add(final Task event) {
        this.events.add(event);
        return event.getId();
    }

    @Override
    public synchronized Task findById(final String id) {
        return events.stream().filter(e -> e.getId().equals(id))
                .findAny()
                .orElse(null);
    }

}


