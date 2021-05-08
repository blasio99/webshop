package dev.blasio99.webshop.server.api.assembler;

import org.springframework.stereotype.Component;

import dev.blasio99.webshop.common.dto.UserDTO;
import dev.blasio99.webshop.server.enums.Role;
import dev.blasio99.webshop.server.model.User;

@Component
public class UserAssembler implements BaseAssembler<UserDTO, User> {

    @Override
    public User createModel(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
		user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setRole(Role.valueOf(dto.getRole()));
		user.setAddress(dto.getAddress());
		user.setPhone(dto.getPhone());
        return user;
    }

    @Override
    public UserDTO createDTO(User model) {
        UserDTO dto = new UserDTO();
        dto.setId(model.getId());
		dto.setEmail(model.getEmail());
        dto.setUsername(model.getUsername());
        dto.setRole(model.getRole().name());
		dto.setAddress(model.getAddress());
		dto.setPhone(model.getPhone());
        return dto;
    }
}