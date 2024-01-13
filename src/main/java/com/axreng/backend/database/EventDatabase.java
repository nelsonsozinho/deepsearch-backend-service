package com.axreng.backend.database;

import com.axreng.backend.model.Task;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EventDatabase {

    private static EventDatabase eventDatabase;

    private final Set<Task> events;

    private EventDatabase() {
        this.events = new HashSet<>();
    }

    public static EventDatabase getInstance() {
        if(eventDatabase == null) {
            eventDatabase = new EventDatabase();
        }
        return eventDatabase;
    }

    public synchronized UUID add(final Task event) {
        this.events.add(event);
        return event.getId();
    }

    public synchronized Task get(final UUID id) {
        return events.stream().filter(e -> e.getId().equals(id))
                .findAny()
                .orElse(null);
    }

}


