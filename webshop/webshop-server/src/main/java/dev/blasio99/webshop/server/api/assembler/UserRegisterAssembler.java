package dev.blasio99.webshop.server.api.assembler;

import org.springframework.stereotype.Component;

import dev.blasio99.webshop.common.dto.UserRegisterDTO;
import dev.blasio99.webshop.server.enums.Role;
import dev.blasio99.webshop.server.model.User;

@Component
public class UserRegisterAssembler implements BaseAssembler<UserRegisterDTO, User> {
    
    @Override
    public User createModel(UserRegisterDTO dto) {
        User user = new User();
		user.setId(dto.getId());
		user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword());
        user.setRole(Role.valueOf(dto.getRole()));
		user.setAddress(dto.getAddress());
		user.setPhone(dto.getPhone());
        return user;
    }

    @Override
    public UserRegisterDTO createDTO(User model) {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setId(model.getId());
		dto.setEmail(model.getEmail());
        dto.setUsername(model.getUsername());
        dto.setPassword(model.getPassword());
        dto.setRole(model.getRole().name());
		dto.setAddress(model.getAddress());
		dto.setPhone(model.getPhone());
        return dto;
    }
}