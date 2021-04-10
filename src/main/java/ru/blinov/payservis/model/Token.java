package ru.blinov.payservis.model;


import javax.persistence.*;

@Entity
@Table(name = "Tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String generateToken;

    public Token() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGenerateToken() {
        return generateToken;
    }

    public void setGenerateToken(String generateToken) {
        this.generateToken = generateToken;
    }
}
