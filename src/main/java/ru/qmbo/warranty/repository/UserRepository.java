package ru.qmbo.warranty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.qmbo.warranty.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
