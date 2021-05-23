package dev.blasio99.webshop.server.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.blasio99.webshop.common.dto.ProductDTO;

import dev.blasio99.webshop.server.api.assembler.ProductAssembler;
import dev.blasio99.webshop.server.exception.IncorrectInputValueException;
import dev.blasio99.webshop.server.model.Product;
import dev.blasio99.webshop.server.service.ProductService;


@CrossOrigin("*")
@RestController
public class ProductResource {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAssembler productAssembler;

	@GetMapping("api/product/id/{id}")
    public ProductDTO getProductByProductId(@PathVariable Long id) {
        return productAssembler.createDTO(productService.getProductByProductId(id));
    }
	
    @GetMapping("api/product/size/{size}")
    public List<ProductDTO> getProductsBySize(@PathVariable String size)  {
        return productAssembler.createDTOList(productService.getProductBySize(size));
    }

    @GetMapping("api/product/name/{name}")
    public List<ProductDTO> getProductByName(@PathVariable String name) {
        return productAssembler.createDTOList(productService.getProductByName(name));
    }

    @PostMapping("admin/api/product/add")
    public Product addProduct(@RequestBody ProductDTO dto) throws IncorrectInputValueException {
		if(!productService.contains(dto.getSize()) || !productService.contains(dto.getCategory()))
			throw new IncorrectInputValueException("Invalid SIZE input value");
        return productService.addProduct(productAssembler.createModel(dto));
    }

	@GetMapping("api/product/all")
	public List<Product> getProducts() {
		return productService.findAllProducts();
    }

	@GetMapping("api/product/category/{category}")
	public List<Product> getProductsByCategory(@PathVariable String category) {
		return productService.getProductByCategory(category);
    }

	@PutMapping("admin/api/product/edit")
    public Product editProduct(@RequestBody ProductDTO dto) throws IncorrectInputValueException{
        if(!productService.contains(dto.getSize()) || !productService.contains(dto.getCategory()))
			throw new IncorrectInputValueException("Invalid SIZE input value");
		return productService.updateProduct(productAssembler.createModel(dto));
    }
   
    @DeleteMapping("admin/api/product/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

	@PostMapping("api/product/sell")
    public void sellProduct(@RequestParam String name, @RequestParam String quantity, @RequestParam String size) {
        productService.sellProduct(name, Integer.valueOf(quantity), size);
    }
	

}
