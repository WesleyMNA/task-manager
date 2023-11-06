package com.manager.taskapi.domain.auth;

import com.manager.taskapi.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "valid", nullable = false)
    private Boolean valid;
    @Column(name = "expiration_datetime", nullable = false)
    private LocalDateTime expirationDatetime;

    @OneToOne
    @JoinColumn(name = "child_id")
    private RefreshToken filho;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public RefreshToken(String token,
                        User user,
                        LocalDateTime expirationDatetime) {
        this.token = token;
        this.valid = true;
        this.user = user;
        this.expirationDatetime = expirationDatetime;
    }
}
