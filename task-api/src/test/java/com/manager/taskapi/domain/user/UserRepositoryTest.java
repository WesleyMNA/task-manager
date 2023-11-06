package com.manager.taskapi.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void testing_existsByEmailAndIdNotMethod() {
        User User = new User(
                "test",
                "test@email.com",
                "12345"
        );
        repository.save(User);
        boolean shouldBeFalse = repository.existsByEmailAndIdNot(User.getEmail(), User.getId());
        assertFalse(shouldBeFalse);
        boolean shouldBeTrue = repository.existsByEmailAndIdNot(User.getEmail(), 2L);
        assertTrue(shouldBeTrue);
    }
}
