package com.axreng.backend.database;

import com.axreng.backend.engine.database.EventRepositoryConcrete;
import com.axreng.backend.model.Task;
import com.axreng.backend.utils.IdUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestEventDatabase {

    private EventRepositoryConcrete eventRepository;

    @BeforeEach
    public void setup() {
        this.eventRepository = EventRepositoryConcrete.getInstance();
    }

    @Test
    public void testAddTask() {
        final Task task = new Task(IdUtils.generateId(), "http://localhost/tmp.html");
        final String id = this.eventRepository.add(task);
        var searchTask = this.eventRepository.findById(id);
        assertNotNull(searchTask);
    }

}
