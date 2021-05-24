package dev.blasio99.webshop.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.blasio99.webshop.server.behavioral.Iterator;
import dev.blasio99.webshop.server.enums.Role;
import dev.blasio99.webshop.server.exception.ServiceException;
import dev.blasio99.webshop.server.exception.UserNotFoundException;
import dev.blasio99.webshop.server.model.User;
import dev.blasio99.webshop.server.repo.UserRepository;

@Service
public class UserService { // implements Sector{
    
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;


    @Autowired 
    private ValidationService validator;

    public User registerUser(User user, Role role) throws ServiceException {

		if (!validator.validateEmail(user.getEmail())) 
			throw new ServiceException("Invalid email address!", HttpStatus.UNPROCESSABLE_ENTITY);
        if (!validator.validateUsername(user.getUsername())) 
			throw new ServiceException("Invalid username!", HttpStatus.UNPROCESSABLE_ENTITY);
        if (!validator.validatePassword(user.getPassword())) 
			throw new ServiceException("Invalid password!", HttpStatus.UNPROCESSABLE_ENTITY);

        User u = userRepository.findByUsername(user.getUsername());
        if (u != null) throw new ServiceException("Username already taken!", HttpStatus.CONFLICT);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(role);

        return userRepository.save(user);
    }

	public User login(User user) throws ServiceException {

		try{
			User userFromRepository = userRepository.findByUsername(user.getUsername());
        	if(!passwordEncoder.matches(user.getPassword(), userFromRepository.getPassword())) 
				throw new ServiceException("Authorization failed!", HttpStatus.UNPROCESSABLE_ENTITY);
        	return userFromRepository;
		}
		catch(ServiceException e){
			throw new ServiceException("Resource not found!", HttpStatus.UNPROCESSABLE_ENTITY);
		}
        
    }

	public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email)!=null ? true : false;
    }

    public List<User> getClients() {
        return userRepository.findByRole(Role.CLIENT);
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        User user =  userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException();
        return user;
    }

	public User getUserByEmail(String email) throws UserNotFoundException {
        User user =  userRepository.findByEmail(email);
		if (user == null) throw new UserNotFoundException();
        return user;
    }

	public User registerClient(User user) throws ServiceException {
        return registerUser(user, Role.CLIENT);
    }

    public User registerAdmin(User user) throws ServiceException {
        return registerUser(user, Role.ADMIN);
    }

	public User updateUser(User user) throws ServiceException {
        User u = userRepository.findByEmail(user.getEmail());
        //check if username is available
        if (u != null && !(u.getId().equals(user.getId()))) throw new ServiceException("Username is taken!", HttpStatus.CONFLICT);
        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException();
        userRepository.delete(user);
    }

	public void subscribe(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) throw new UserNotFoundException();
		user.setSubscriber(true);
		userRepository.save(user);
	}

	public void unsubscribe(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) throw new UserNotFoundException();
		user.setSubscriber(false);
		userRepository.save(user);
	}

	public Iterator getIterator() {
		return new UserIterator();
	}
	
	List<User> users;
	
	private class UserIterator implements Iterator {
        int index;
        
        @Override
        public boolean hasNext() {
            if(index < users.size()) {
                return true;
            } 
            return false;
        }

        @Override
        public Object next() {
            if(this.hasNext()) {
                return users.get(index++);
            }
            return null;
        }
    }

	public List<String> getUserList(){
		List<String> userList = new ArrayList<>();

		users = getClients();

		for(Iterator iterator = getIterator(); iterator.hasNext();) {
            User user = (User)iterator.next();
            userList.add(user.getUsername());
        }

		return userList;
	}

}