package dev.blasio99.webshop.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

@Entity
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"productId", "username"})
}) 
public class Orders extends BaseModel {

	@Min(1)
	private Integer quantity;
	private Long productId;
	private String username;

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}