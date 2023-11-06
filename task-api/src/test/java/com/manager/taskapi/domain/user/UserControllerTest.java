package com.manager.taskapi.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manager.taskapi.config.handlers.ErrorResponse;
import com.manager.taskapi.domain.user.dtos.requests.UserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void shouldCreateANewClient()
            throws Exception {
        UserRequest request = new UserRequest(
                "12345",
                "12345",
                "Test",
                "teste@email.com"
        );
        performPost(request)
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400ForPasswordsThatDoNotMatch()
            throws Exception {
        UserRequest request = new UserRequest(
                "12345",
                "54321",
                "Test",
                "teste@email.com"
        );
        performPost(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertEquals(errorResponse.message(), "Could not validate the request");
                    assertEquals(errorResponse.errors().size(), 1);
                    assertEquals(errorResponse.errors().get("request error 1"), "Passwords do not match");
                });
    }

    @Test
    void shouldReturn400ForRepeatedEmail()
            throws Exception {
        User client = new User(
                "test",
                "test@email.com",
                "12345"
        );
        repository.save(client);
        UserRequest request = new UserRequest(
                "12345",
                "12345",
                "Test",
                client.getEmail()
        );
        performPost(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertEquals(errorResponse.message(), "Could not validate the request");
                    assertEquals(errorResponse.errors().size(), 1);
                    assertEquals(errorResponse.errors().get("email"), "Value already exists");
                });
    }

    ResultActions performPost(UserRequest request)
            throws Exception {
        String content = objectMapper.writeValueAsString(request);
        return mvc
                .perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));
    }
}
