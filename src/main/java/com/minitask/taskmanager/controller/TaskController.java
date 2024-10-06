package com.minitask.taskmanager.controller;

import com.minitask.taskmanager.exception.BadTaskFormatException;
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
import org.springframework.validation.Errors;
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
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, Errors errors){
        if (errors.hasErrors()) {
            return new ResponseEntity(new BadTaskFormatException(errors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(taskService.updateOrInsertTask(task), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task, Errors errors){
        if (errors.hasErrors()) {
            return new ResponseEntity(new BadTaskFormatException(errors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(taskService.updateOrInsertTask(task), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public String deleteTask(@RequestBody Task task){
        taskService.deleteTask(task);
        return "Task is deleted";
    }


}
