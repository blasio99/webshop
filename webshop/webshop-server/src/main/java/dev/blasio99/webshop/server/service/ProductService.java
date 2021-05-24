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

	public List<Product> findAllProducts(){
		return productRepository.findAll();
	}

    public Product addProduct(Product product) {
		
		//List<User> users = userRepository.findAll();
		//for(User user : users)
		//	if(user.getSubscriber()){
		//		emailService.update(product, user.getEmail());
		//	}

		if(productRepository.findByNameAndSize(product.getName(), product.getSize()) != null){
			Product existingProduct = productRepository.findByNameAndSize(product.getName(), product.getSize());
			existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
			return productRepository.save(existingProduct);
		}
        return productRepository.save(product);
    }

	public Product updateProduct(Product product) throws ServiceException {
		Product existingProduct = productRepository.findByNameAndSize(product.getName(), product.getSize());
		existingProduct.setCategory(product.getCategory());
		existingProduct.setDescription(product.getDescription());
		existingProduct.setName(product.getName());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setQuantity(product.getQuantity());
		existingProduct.setSize(product.getSize());
        return productRepository.save(existingProduct);
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
        
		System.out.println(product.getQuantity() + " ---- " + quantity);
		product.setQuantity(product.getQuantity() - quantity);
        
		productRepository.save(product);
		
    }

	public boolean contains(String input) {
		for (Size c : Size.values()) 
			if (c.name().equals(input)) 
				return true;
		for (Category c : Category.values()) 
			if (c.name().equals(input)) 
				return true;
	
		return false;
	}

}
