package dev.blasio99.webshop.common.dto;

public class SecureTokenDTO extends BaseDTO{

    private String token;
	private String timestamp;
	private String expireAt;
	private String email;
	private boolean isExpired;

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getExpireAt() {
		return this.expireAt;
	}

	public void setExpireAt(String expireAt) {
		this.expireAt = expireAt;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isIsExpired() {
		return this.isExpired;
	}

	public void setIsExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

}
