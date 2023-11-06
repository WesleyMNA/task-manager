package com.manager.taskapi.domain.note.services;

import com.manager.taskapi.config.handlers.exceptions.ForbiddenException;
import com.manager.taskapi.config.handlers.exceptions.NotFoundException;
import com.manager.taskapi.config.security.jwt.UserJwt;
import com.manager.taskapi.domain.user.User;
import com.manager.taskapi.domain.user.UserRepository;
import com.manager.taskapi.domain.note.Note;
import com.manager.taskapi.domain.note.NoteRepository;
import com.manager.taskapi.domain.note.dtos.NoteRequest;
import com.manager.taskapi.domain.note.dtos.NoteResponse;
import com.manager.taskapi.domain.task.Task;
import com.manager.taskapi.domain.task.TaskRepository;
import com.manager.taskapi.utils.helpers.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final NoteAssemblerModel assemblerModel;
    private final AuthHelper authHelper;
    private final ModelMapper mapper;

    public NoteResponse create(NoteRequest request) {
        User client = findCurrentClient();
        Task task = validateTaskId(request.getTaskId());
        var note = mapper.map(request, Note.class);
        note.setDateHour(LocalDateTime.now());
        note.setUser(client);
        note.setTask(task);
        noteRepository.save(note);
        return assemblerModel.toModel(note);
    }

    public void update(Long id, NoteRequest request) {
        Note note = validateNoteId(id);
        currentUserHasPermission(note);
        note.setText(request.getText());
        noteRepository.save(note);
    }

    public void delete(Long id) {
        Note note = validateNoteId(id);
        currentUserHasPermission(note);
        noteRepository.delete(note);
    }

    private Note validateNoteId(Long id) {
        return noteRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("note not found"));
    }

    private User findCurrentClient() {
        UserJwt currentClient = authHelper.getCurrentUser();
        return userRepository
                .findById(currentClient.getId())
                .orElseThrow(() -> new NotFoundException("client not found"));
    }

    private void currentUserHasPermission(Note note) {
        User currentClient = findCurrentClient();

        if (!currentClient.equals(note.getUser()))
            throw new ForbiddenException();
    }

    private Task validateTaskId(Long id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("task not found"));
    }
}
