package dev.blasio99.webshop.server.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import dev.blasio99.webshop.server.enums.Category;
import dev.blasio99.webshop.server.enums.Size;

@Entity
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"name", "size"})
}) 
public class Product extends BaseModel {
    
    private String name;
	@Enumerated(EnumType.STRING)
	private Category category;
	private Double price;
	@Enumerated(EnumType.STRING)
	private Size size;
	private Integer quantity;
	private String description;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Size getSize() {
		return this.size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}