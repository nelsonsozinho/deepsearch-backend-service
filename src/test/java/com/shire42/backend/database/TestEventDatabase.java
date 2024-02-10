package com.shire42.backend.database;

import com.shire42.backend.engine.database.EventRepositoryConcrete;
import com.shire42.backend.model.Task;
import com.shire42.backend.utils.IdUtils;
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
