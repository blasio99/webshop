package dev.blasio99.webshop.server.api.assembler;

import org.springframework.stereotype.Component;

import dev.blasio99.webshop.common.dto.ProductDTO;
import dev.blasio99.webshop.server.enums.Size;
import dev.blasio99.webshop.server.model.Product;

@Component
public class ProductAssembler implements BaseAssembler<ProductDTO, Product> {

    @Override
    public Product createModel(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
		product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setSize(Size.valueOf(dto.getSize()));
		product.setDescription(dto.getDescription());
        return product;
    }
	
    @Override
    public ProductDTO createDTO(Product model) {
        ProductDTO dto = new ProductDTO();
        dto.setId(model.getId());
		dto.setName(model.getName());
        dto.setPrice(model.getPrice());
        dto.setSize(model.getSize().name());
		dto.setDescription(model.getDescription());
        return dto;
    }
}