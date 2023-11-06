package com.manager.taskapi.domain.note;

import com.manager.taskapi.domain.user.User;
import com.manager.taskapi.domain.task.Task;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "date_hour", nullable = false)
    private LocalDateTime dateHour;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
