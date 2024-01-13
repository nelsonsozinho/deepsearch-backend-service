package com.axreng.backend.database;

import com.axreng.backend.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestEventDatabase {

    private EventDatabase eventDatabase;

    @BeforeEach
    public void setup() {
        this.eventDatabase = EventDatabase.getInstance();
    }

    @Test
    public void testAddTask() {
        final Task task = new Task(UUID.randomUUID(), "http://localhost/tmp.html");
        final UUID id = this.eventDatabase.add(task);
        var searchTask = this.eventDatabase.get(id);
        assertNotNull(searchTask);
    }

}
