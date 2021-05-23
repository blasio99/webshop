package dev.blasio99.webshop.server.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import dev.blasio99.webshop.server.enums.Category;
import dev.blasio99.webshop.server.enums.Size;

@Entity
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"name", "size"})
}) 
public class Product{
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
	@Enumerated(EnumType.STRING)
	private Category category;
	private Double price;
	@Enumerated(EnumType.STRING)
	private Size size;
	private Integer quantity;
	private String description;

	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

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

	@Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
		String items = (getQuantity() == 1) ? " item" : " items";
		
		stringBuilder.append("Name: \t\t" + getName() + '\n');
		stringBuilder.append("Category: \t" + getCategory() + '\n');
		stringBuilder.append("Price: \t\t" + getPrice() + " RON / item\n");
		stringBuilder.append("Size: \t\t" + sizeToString(getSize()) + '\n');
		stringBuilder.append("Quantity: \t" + getQuantity() + items + '\n');
		stringBuilder.append("Description: \t" + getDescription() + '\n');

		return stringBuilder.toString();
    }

	private String sizeToString(Size size){
		if(size.equals(Size.XXL) || size.equals(Size.XXXXL))
			return size.toString();
		else if(size.toString().length() == 3)
			return size.toString().substring(1);
		else if(size.toString().length() == 5)
			return size.toString().substring(1, 3) + '.' + size.toString().substring(4);
		else 
			return size.toString();
		
	}
}