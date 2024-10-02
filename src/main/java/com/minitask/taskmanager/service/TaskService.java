package com.minitask.taskmanager.service;

import com.minitask.taskmanager.model.Task;
import com.minitask.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

   /* @Query(
            value = "SELECT * FROM Tasks ORDER BY id",
            countQuery = "SELECT count(*) FROM Tasks",
            nativeQuery = true)
    Page<Task> findAllUsersWithPagination(Pageable pageable){
         return taskRepository.findAll(pageable);
    }*/
}
