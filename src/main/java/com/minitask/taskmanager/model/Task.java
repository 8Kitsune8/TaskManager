package com.minitask.taskmanager.model;

import com.minitask.taskmanager.config.Messages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(generator = "task_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "task_id_seq", sequenceName = "task_id_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private String title;
    @NotEmpty(message = Messages.ERROR_NOT_EMPTY)
    private String description;
    private LocalDate dueDate;
    private boolean completed;

}
