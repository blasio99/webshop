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
import org.springframework.web.bind.annotation.RestController;

import dev.blasio99.webshop.common.dto.ProductDTO;

import dev.blasio99.webshop.server.api.assembler.ProductAssembler;
import dev.blasio99.webshop.server.model.Product;
import dev.blasio99.webshop.server.service.ProductService;
import dev.blasio99.webshop.server.repo.ProductRepository;


@CrossOrigin("*")
@RestController
public class ProductResource {

    @Autowired
    private ProductService productService;

	@Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAssembler productAssembler;

	@GetMapping("api/product/id/{id}")
    public ProductDTO getProductByProductId(@PathVariable Long id) {
        return productAssembler.createDTO(productService.getProductByProductId(id));
    }
	
    @GetMapping("api/product/size/{size}")
    public List<ProductDTO> getProductsBySize(@PathVariable String size) {
        return productAssembler.createDTOList(productService.getProductBySize(size));
    }

    @GetMapping("api/product/name/{name}")
    public List<ProductDTO> getProductByName(@PathVariable String name) {
        return productAssembler.createDTOList(productService.getProductByName(name));
    }

    @PostMapping("admin/api/product/add")
    public Product addProduct(@RequestBody ProductDTO dto) {
        return productService.addProduct(productAssembler.createModel(dto));
    }

	@GetMapping("api/product/all")
	public List<Product> getProducts() {
		return productRepository.findAll();
    }

	@PutMapping("admin/api/product/edit")
    public Product editProduct(@RequestBody ProductDTO dto) {
        return productRepository.save(productAssembler.createModel(dto));
    }
   
    @DeleteMapping("admin/api/product/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
	

}
