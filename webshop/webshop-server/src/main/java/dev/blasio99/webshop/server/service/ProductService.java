package dev.blasio99.webshop.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import dev.blasio99.webshop.server.exception.ServiceException;
import dev.blasio99.webshop.server.model.Product;
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

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

	public Product updateProduct(Product product) throws ServiceException {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).get();
        if (product == null) throw new ServiceException("Product does not exist!", HttpStatus.CONFLICT);;
        productRepository.delete(product);
    }
}
