package com.minitask.taskmanager.controller;

import com.minitask.taskmanager.model.Task;
import com.minitask.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/allPageable")
    public Page<Task> getAllTasksPageable(@PageableDefault(value = 2, page = 0) Pageable pageable) {
        return taskService.getAllTasksPageableQuery(pageable);
    }

    @GetMapping("/allPagAndSort")
    public Page<Task> getAllTaskDefPag(Pageable pageable){
        return taskService.getAllTasksPageableAndSort(pageable);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable(name = "id") @Positive Long id){
        Optional<Task> task = taskService.getTaskById(id);
        return task.orElseGet(Task::new);
    }
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task){
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
