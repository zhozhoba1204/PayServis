package ru.blinov.payservis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.blinov.payservis.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
