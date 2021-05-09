package dev.blasio99.webshop.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.blasio99.webshop.server.exception.OutOfStockException;
import dev.blasio99.webshop.server.exception.ProductNotFoundException;
import dev.blasio99.webshop.server.exception.ServiceException;
import dev.blasio99.webshop.server.model.Product;
import dev.blasio99.webshop.server.enums.Category;
import dev.blasio99.webshop.server.enums.Size;
import dev.blasio99.webshop.server.repo.ProductRepository;

@Service
public class ProductService {
	@Autowired
    private ProductRepository productRepository;

    public Product getProductByProductId(Long id) {
        return productRepository.findById(id).get();
    }

    public List<Product> getProductBySize(String size) throws ServiceException {
		return productRepository.findBySize(Size.valueOf(size));
    }

	public List<Product> getProductByName(String name) throws ServiceException {
		return productRepository.findByName(name);
    }

	public List<Product> getProductByCategory(String category) throws ServiceException {
		return productRepository.findByCategory(Category.valueOf(category));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

	public Product updateProduct(Product product) throws ServiceException {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).get();
        if (product == null) throw new ProductNotFoundException();
        productRepository.delete(product);
    }

	public void sellProduct(String name, Integer quantity, String size) throws OutOfStockException, ProductNotFoundException{

		Product product = productRepository.findByNameAndSize(name, Size.valueOf(size));
		if(product == null) throw new ProductNotFoundException();
		if(product.getQuantity() - quantity < 0) throw new OutOfStockException();
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
		
    }
}
