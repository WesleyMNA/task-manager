package com.manager.taskapi.domain.user.services;

import com.manager.taskapi.config.handlers.exceptions.BadRequestException;
import com.manager.taskapi.config.handlers.exceptions.ForbiddenException;
import com.manager.taskapi.config.handlers.exceptions.NotFoundException;
import com.manager.taskapi.config.handlers.exceptions.UnauthorizedException;
import com.manager.taskapi.config.security.jwt.UserJwt;
import com.manager.taskapi.domain.user.User;
import com.manager.taskapi.domain.user.UserRepository;
import com.manager.taskapi.domain.user.UserSpecification;
import com.manager.taskapi.domain.user.dtos.UserQuery;
import com.manager.taskapi.domain.user.dtos.UserResponse;
import com.manager.taskapi.domain.user.dtos.requests.UserRequest;
import com.manager.taskapi.domain.user.dtos.requests.UpdateUserRequest;
import com.manager.taskapi.domain.user.dtos.requests.UpdatePasswordRequest;
import com.manager.taskapi.utils.helpers.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final UserAssemblerModel assembler;
    private final PagedResourcesAssembler<User> pagedAssembler;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;
    private final AuthHelper authHelper;

    public PagedModel<UserResponse> findAll(UserQuery query, Pageable pageable) {
        var specBuild = new UserSpecification();
        Specification<User> spec = Specification.where(
                specBuild.nameLikeIfNotNull(query.getName())
                        .and(specBuild.emailLikeIfNotNull(query.getEmail()))
        );
        Page<User> clients = repository.findAll(spec, pageable);
        return pagedAssembler.toModel(clients, assembler);
    }

    public UserResponse findById(Long id) {
        User client = validateId(id);
        return assembler.toModel(client);
    }

    public UserResponse create(UserRequest request) {
        User client = mapper.map(request, User.class);
        client.setPassword(encoder.encode(request.getPassword()));
        repository.save(client);
        return assembler.toModel(client);
    }

    public void update(Long id, UpdateUserRequest request) {
        validateCurrentClient(id);
        validateUpdateRequest(id, request);
        User client = validateId(id);
        client.setName(request.getName());
        client.setEmail(request.getEmail());
        repository.save(client);
    }

    public void updatePassword(Long id, UpdatePasswordRequest request) {
        validateCurrentClient(id);
        User client = validateId(id);

        if (!encoder.matches(request.getCurrentPassword(), client.getPassword()))
            throw new UnauthorizedException("Invalid password");

        client.setPassword(encoder.encode(request.getPassword()));
        repository.save(client);
    }

    private User validateId(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("client not found"));
    }

    private void validateCurrentClient(Long id) {
        UserJwt currentClient = authHelper.getCurrentUser();

        if (!Objects.equals(currentClient.getId(), id))
            throw new ForbiddenException();
    }

    private void validateUpdateRequest(Long id, UpdateUserRequest request) {
        if (repository.existsByEmailAndIdNot(request.getEmail(), id))
            throw new BadRequestException("Email already exists");
    }

}
