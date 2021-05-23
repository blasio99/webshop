package dev.blasio99.webshop.server.repo;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.blasio99.webshop.server.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>  {
    public Optional<Orders> findById(Long id);
	public ArrayList<Orders> findByProductId(Long productId);
}
