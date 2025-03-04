package ru.kata._4.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata._4.DAO.RoleDAO;
import ru.kata._4.DAO.UserDAO;
import ru.kata._4.model.Role;
import ru.kata._4.model.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(long id) {
        User user = null;
        Optional<User> optional = userDAO.findById(id);
        if(optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    @Transactional
    public void save(User user) {
        userDAO.save(passwordCoder(user));
    }

    @Override
    @Transactional
    public void update(User user) {
        userDAO.save(user);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        userDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    @PostConstruct
    @Transactional
    public void addDefaultUser() {
        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleDAO.findById(1L).orElse(null));
        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleDAO.findById(1L).orElse(null));
        roles2.add(roleDAO.findById(2L).orElse(null));
        User user1 = new User(
                "Pat",
                "Holman", (byte) 29,
                "3friends@mail.ru",
                "user",
                "user", roles1);
        User user2 = new User(
                "Sergei",
                "Gonsoronov", (byte) 21,
                "gonsoronss@icloud.com",
                "admin",
                "admin", roles2);
        save(user1);
        save(user2);
        }
}

