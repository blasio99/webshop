package dev.blasio99.webshop.server.api.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import dev.blasio99.webshop.common.dto.ProductDTO;
import dev.blasio99.webshop.server.enums.Category;
import dev.blasio99.webshop.server.enums.Size;
import dev.blasio99.webshop.server.model.Product;

@Component
public class ProductAssembler {

    public Product createModel(ProductDTO dto)  {
        Product product = new Product();
        product.setProductId(dto.getProductId());
		product.setName(dto.getName());
		product.setCategory(Category.valueOf(dto.getCategory()));
        product.setPrice(dto.getPrice());
		product.setSize(Size.valueOf(dto.getSize()));
		product.setQuantity(dto.getQuantity());
		product.setDescription(dto.getDescription());
        return product;
    }
	
    public ProductDTO createDTO(Product model) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(model.getProductId());
		dto.setName(model.getName());
		dto.setCategory(model.getCategory().name());
        dto.setPrice(model.getPrice());
        dto.setSize(model.getSize().name());
		dto.setQuantity(model.getQuantity());
		dto.setDescription(model.getDescription());
        return dto;
    }

	public List<Product> createModelList(List<ProductDTO> dtoList) {
        List<Product> models = new ArrayList<>(dtoList.size());
        for (ProductDTO dto : dtoList) {
            models.add(createModel(dto));
        }
        return models;
    }

    public List<ProductDTO> createDTOList(List<Product> models) {
        List<ProductDTO> dtoList = new ArrayList<>(models.size());
        for (Product model : models) {
            dtoList.add(createDTO(model));
        }
        return dtoList;
    }
}