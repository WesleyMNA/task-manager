package com.manager.taskapi.domain.task;

import com.manager.taskapi.config.handlers.exceptions.NotFoundException;
import com.manager.taskapi.config.security.jwt.UserJwt;
import com.manager.taskapi.domain.user.User;
import com.manager.taskapi.domain.user.UserRepository;
import com.manager.taskapi.domain.task.assemblers.DetailedTaskAssemblerModel;
import com.manager.taskapi.domain.task.assemblers.TaskAssemblerModel;
import com.manager.taskapi.domain.task.dtos.DetailedTaskResponse;
import com.manager.taskapi.domain.task.dtos.TaskQuery;
import com.manager.taskapi.domain.task.dtos.TaskRequest;
import com.manager.taskapi.domain.task.dtos.TaskResponse;
import com.manager.taskapi.utils.helpers.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskAssemblerModel taskAssembler;
    private final DetailedTaskAssemblerModel detailedAssembler;
    private final PagedResourcesAssembler<Task> pagedAssembler;
    private final ModelMapper mapper;
    private final AuthHelper authHelper;

    public PagedModel<TaskResponse> findAll(TaskQuery query, Pageable pageable) {
        UserJwt currentUser = authHelper.getCurrentUser();
        var specBuild = new TaskSpecification();
        Specification<Task> spec = Specification.where(
                specBuild.titleLikeIfNotNull(query.title())
                        .and(specBuild.priorityEqualsIfNotNull(query.priority())
                                .and(specBuild.statusEqualsIfNotNull(query.status()))
                                .and(specBuild.userRegisteredInTask(currentUser.getId())))
        );
        Page<Task> tasks = taskRepository.findAll(spec, pageable);
        return pagedAssembler.toModel(tasks, taskAssembler);
    }

    public DetailedTaskResponse findById(Long id) {
        Task task = validateId(id);
        return detailedAssembler.toModel(task);
    }

    public TaskResponse create(TaskRequest request) {
        Task task = mapper.map(request, Task.class);
        User user = validateClientId(request.getClientId());
        task.setUser(user);
        taskRepository.save(task);
        return taskAssembler.toModel(task);
    }

    public void update(Long id, TaskRequest request) {
        Task task = validateId(id);
        User user = validateClientId(request.getClientId());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setInitialDate(request.getInitialDate());
        task.setFinalDate(request.getFinalDate());
        task.setUser(user);
        taskRepository.save(task);
    }

    public void delete(Long id) {
        Task task = validateId(id);
        taskRepository.delete(task);
    }

    private Task validateId(Long id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("task not found"));
    }

    private User validateClientId(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }
}
