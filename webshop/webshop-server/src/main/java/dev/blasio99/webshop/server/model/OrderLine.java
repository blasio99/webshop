package dev.blasio99.webshop.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table
public class OrderLine extends BaseModel{

	private ArrayList<Long> orderIdList = new ArrayList<>();
	private LocalDateTime orderDate;

	@Column(unique = true)
	private String username;

	public ArrayList<Long> getOrderIdList() {
		return this.orderIdList;
	}

	public void setOrderIdList(ArrayList<Long> orderIdList) {
		this.orderIdList = orderIdList;
	}
	
	public LocalDateTime getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
