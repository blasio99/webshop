package dev.blasio99.webshop.common.dto;

public abstract class BaseDTO {
    
	private Long id;

    public Long getId() {
        return id;
    }
	
    public void setId(Long id) {
        this.id = id;
    }
}