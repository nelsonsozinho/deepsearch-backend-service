package com.axreng.backend.service;

import com.axreng.backend.engine.database.EventRepositoryConcrete;
import com.axreng.backend.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTaskService {

    private EventRepositoryConcrete eventRepository;

    private TaskService taskService;


    @BeforeEach
    public void before() throws Exception {
        this.eventRepository = EventRepositoryConcrete.getInstance();
        this.taskService = new TaskService();
    }

    @Test
    public void testAddNewTask() {
        final Task task = taskService.newTask("term");
        final Task taskSearched = eventRepository.findById(task.getId());
        assertEquals(task.getId(), taskSearched.getId());
    }

    @Test
    public void testGetTask() {
        final Task task = taskService.newTask("term");
        final Task taskSearched = eventRepository.findById(task.getId());
        assertNotNull(taskSearched);
    }

}
