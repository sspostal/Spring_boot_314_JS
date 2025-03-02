package ru.kata._4.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata._4.model.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
}