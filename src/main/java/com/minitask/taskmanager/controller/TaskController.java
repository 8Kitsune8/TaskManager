package com.minitask.taskmanager.controller;

import com.minitask.taskmanager.model.Task;
import com.minitask.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/all")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable(name = "id") Long id){
        Optional<Task> task = taskService.getTaskById(id);
        return task.orElseGet(Task::new);
    }
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        return new ResponseEntity<>(taskService.updateOrInsertTask(task), HttpStatus.CREATED);
    }
    @PutMapping
    public Task updateTask(@RequestBody Task task){
        return taskService.updateOrInsertTask(task);
    }

    @DeleteMapping
    public String deleteTask(@RequestBody Task task){
        taskService.deleteTask(task);
        return "Task is deleted";
    }


}