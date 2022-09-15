package ru.qmbo.warranty.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.qmbo.warranty.domain.User;

public interface UserService extends CrudService<User>, UserDetailsService {

    User save(User user);

    User update(User user);

    void deleteById(Integer id);

}
