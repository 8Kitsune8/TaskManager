package com.minitask.taskmanager.unit;

import com.minitask.taskmanager.model.Task;
import com.minitask.taskmanager.repository.TaskRepository;
import com.minitask.taskmanager.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private final Task task = new Task();
    private List<Task> tasks;

    @BeforeEach
    void init() {

        task.setId(1L);
        task.setTitle("task 1");
        task.setDescription("my 1st task");
        tasks = List.of(task);
    }


    @Test
    void givenThereAreTasks_whenGetAllTasks_thenReturnTaskList() {

        Mockito.when(taskRepository.findAll()).thenReturn(tasks);
        assertEquals(taskService.getAllTasks().getFirst(), task);
    }

    @Test
    void givenTask_whenUpdateOrInsertTask_thenReturnTask() {

        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);
        assertEquals(taskService.updateOrInsertTask(task),task);
    }

    @Test
    void givenTask_whenDeleteTask_thenDeleteIsInvoked() {
        taskService.deleteTask(task);
        Mockito.verify(taskRepository, Mockito.times(1)).delete(task);
    }

    @Test
    void givenTaskId_whenGetTaskById_thenOptionalIsRetrieved() {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        assertEquals(taskService.getTaskById(1L).getClass(),Optional.class);
    }
}