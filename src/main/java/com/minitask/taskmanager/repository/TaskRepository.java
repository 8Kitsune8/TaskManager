package com.minitask.taskmanager.repository;

import com.minitask.taskmanager.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TaskRepository extends JpaRepository<Task, Long> {

        @Query(
            value = "SELECT * FROM Task ORDER BY id",
            countQuery = "SELECT count(*) FROM Task",
            nativeQuery = true)
        Page<Task> findAllUsersWithPagination(Pageable pageable);

}
