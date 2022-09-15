package ru.qmbo.warranty.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.qmbo.warranty.domain.User;
import ru.qmbo.warranty.exceptions.ValidationException;
import ru.qmbo.warranty.repository.UserRepository;
import ru.qmbo.warranty.service.UserService;

import java.util.List;

@Service
public class UserServiceDefault implements UserService {

    private final UserRepository repository;

    public UserServiceDefault(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of User should not be NULL");
        }
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return this.repository.findAll();
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User should not be NULL");
        }
        if (user.getId() != null) {
            throw new ValidationException("Id of User should be NULL");
        }
        return this.repository.save(user);
    }

    @Override
    public User update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User should not be NULL");
        }
        if (user.getId() == null) {
            throw new ValidationException("Id of User should not be NULL");
        }
        return this.repository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of User should not be NULL");
        }
        this.repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.repository.findByUsername(username);
    }

}
