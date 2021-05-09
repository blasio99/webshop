package dev.blasio99.webshop.server.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.blasio99.webshop.server.enums.Size;
import dev.blasio99.webshop.server.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>  {
    public Optional<Product> findById(Long id);
	public List<Product> findByName(String name);
    public List<Product> findBySize(Size size);
}