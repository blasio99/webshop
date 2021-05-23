package dev.blasio99.webshop.common.dto;

public class UserDTO extends BaseDTO{
    
    private String email;
	private String username;
	private String password;
	private String role;
	private String address;
	private String phone;
	private Boolean subscriber;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Boolean getSubscriber() {
		return this.subscriber;
	}

	public void setSubscriber(Boolean subscriber) {
		this.subscriber = subscriber;
	}

}