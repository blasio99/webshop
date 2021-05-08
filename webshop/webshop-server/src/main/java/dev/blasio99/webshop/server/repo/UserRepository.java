package dev.blasio99.webshop.server.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dev.blasio99.webshop.server.enums.Role;
import dev.blasio99.webshop.server.model.User;

public interface UserRepository extends CrudRepository<User, Long>  {
    public User findByUsername(String username);
	public User findByEmail(String email);
    public List<User> findByRole(Role role);
}