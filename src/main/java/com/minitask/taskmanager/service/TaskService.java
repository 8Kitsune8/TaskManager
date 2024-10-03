package com.minitask.taskmanager.service;

import com.minitask.taskmanager.model.Task;
import com.minitask.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

     public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

     public Task updateOrInsertTask(Task task) {
        return taskRepository.save(task);
    }

     public void deleteTask(Task task){
        taskRepository.delete(task);
    }

     public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }

    public Page<Task> getAllTasksPageable(Pageable pageable){
         return taskRepository.findAllUsersWithPagination(pageable);
    }
}
