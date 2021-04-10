package ru.blinov.payservis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.blinov.payservis.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByUserId(Long id);
    Token findBySessionId(String id);
}
