package dev.blasio99.webshop.server.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private final Pattern usernamePattern = Pattern.compile("[A-Za-z]\\w{4,25}");
    private final Pattern passwordPattern = Pattern.compile("\\w{8,20}");
	private final Pattern emailPattern    = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?!-)(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    
    public boolean validateUsername(String username) {
        return usernamePattern.matcher(username).matches();
    }

    public boolean validatePassword(String password) {
        return passwordPattern.matcher(password).matches();
    }

	public boolean validateEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

}