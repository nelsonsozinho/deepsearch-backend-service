package com.axreng.backend.database;

import com.axreng.backend.model.Task;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class EventDatabase {

    private static EventDatabase eventDatabase;

    private Set<Task> events;

    private EventDatabase() {
        this.events = new HashSet<>();
    }

    public static EventDatabase getInstance() {
        if(eventDatabase == null) {
            eventDatabase = new EventDatabase();
        }
        return eventDatabase;
    }

    public UUID add(final Task event) {
        event.setId(UUID.randomUUID());
        this.events.add(event);
        return event.getId();
    }

    public Task get(final UUID id) {
        return events.stream().filter(e -> Objects.equals(e.getId(), id))
                .findAny()
                .orElse(null);
    }

    public void remove(final UUID id) {
        this.events.remove(this.get(id));
    }

}


