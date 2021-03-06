package dev.blasio99.webshop.server.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import dev.blasio99.webshop.common.dto.UserDTO;
import dev.blasio99.webshop.common.dto.UserRegisterDTO;

import dev.blasio99.webshop.server.api.assembler.UserAssembler;
import dev.blasio99.webshop.server.api.assembler.UserRegisterAssembler;
import dev.blasio99.webshop.server.model.SecureToken;
import dev.blasio99.webshop.server.model.User;
import dev.blasio99.webshop.server.repo.SecureTokenRepository;
import dev.blasio99.webshop.server.exception.InvalidTokenException;
import dev.blasio99.webshop.server.exception.ServiceException;
import dev.blasio99.webshop.server.exception.UserExistsException;
import dev.blasio99.webshop.server.service.SecureTokenService;
import dev.blasio99.webshop.server.service.UserService;


@CrossOrigin("*")
@RestController
public class UserResource {

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRegisterAssembler userRegisterAssembler;

	@Autowired
    private SecureTokenService secureTokenService;

	@Autowired 
	private SecureTokenRepository secureTokenRepository;


	@PostMapping("/login")
    public UserDTO login(@RequestBody UserRegisterDTO dto) {
        return userAssembler.createDTO(userService.login(userRegisterAssembler.createModel(dto)));
    }

	@PostMapping("/register/{token}")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO register(@RequestBody UserRegisterDTO dto, @PathVariable String token) throws InvalidTokenException {
		
		SecureToken secureToken = secureTokenService.findByToken(token);

		if(Objects.isNull(secureToken) || secureToken.isExpired() || !secureToken.getEmail().equals(dto.getEmail()))
            throw new InvalidTokenException("Token is not valid");
        
		User user = userService.registerClient(userRegisterAssembler.createModel(dto));
		
		secureTokenService.removeToken(secureToken);
		return userAssembler.createDTO(user);
	}

    @GetMapping("/admin/api/user/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            return new ResponseEntity<>(userAssembler.createDTO(user), HttpStatus.OK);
        } catch(ServiceException e) {
            return new ResponseEntity<>(e.getHttpStatus());
        }
    }

    @GetMapping("/admin/api/clients")
    public List<UserDTO> getClients() {
        return userAssembler.createDTOList(userService.getClients());
    }

    @PostMapping("/admin/api/register/admin")
	@ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerAdmin(@RequestBody UserRegisterDTO dto) {
        User user = userService.registerAdmin(userRegisterAssembler.createModel(dto));
        return userAssembler.createDTO(user);
    }

	@PostMapping("/admin/api/register/client")
	@ResponseStatus(HttpStatus.CREATED)
    public String registerClient(@RequestParam String email) throws UserExistsException {
		/*if(userService.getUserByEmail(email) != null) 
			throw new UserExistsException("User already exists");*/

		SecureToken secureToken= secureTokenService.createSecureToken();
		secureToken.setEmail(email);
		secureTokenRepository.save(secureToken);
		return secureToken.getToken();
    }

    @DeleteMapping("/admin/api/delete/user/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

	@PostMapping("/api/user/subscribe")
	public void subscribe(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userService.subscribe(authentication.getName());
	}

	@PostMapping("/api/user/unsubscribe")
	public void unsubscribe(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userService.unsubscribe(authentication.getName());
	}

	@GetMapping("admin/api/users")
	public List<String> getUserList(){
		return userService.getUserList();
	}
    
}