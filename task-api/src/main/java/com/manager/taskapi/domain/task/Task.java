package com.manager.taskapi.domain.task;

import com.manager.taskapi.domain.user.User;
import com.manager.taskapi.domain.note.Note;
import com.manager.taskapi.domain.task.dtos.enumaration.TaskPriority;
import com.manager.taskapi.domain.task.dtos.enumaration.TaskStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private TaskPriority priority;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;
    @Column(name = "initial_date")
    private LocalDate initialDate;
    @Column(name = "final_date")
    private LocalDate finalDate;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Note> notes = new HashSet<>();
}
