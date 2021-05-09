package dev.blasio99.webshop.server.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.blasio99.webshop.server.model.OrderLine;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long>  {
    public Optional<OrderLine> findById(Long id);
	public OrderLine findByUsername(String username); 
}
