package com.manager.taskapi.domain.task.dtos;

import com.manager.taskapi.domain.task.dtos.enumaration.TaskPriority;
import com.manager.taskapi.domain.task.dtos.enumaration.TaskStatus;
import lombok.*;

@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskQuery {

    private String title;
    @Getter
    private TaskPriority priority;
    @Getter
    private TaskStatus status;

    public String getTitle() {
        return treatLikeString(title);
    }

    private String treatLikeString(String text) {
        if (text == null)
            return null;
        return "%" + text.replace("?", "%").toLowerCase() + "%";
    }
}
