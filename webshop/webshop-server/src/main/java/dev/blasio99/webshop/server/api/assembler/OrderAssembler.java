package dev.blasio99.webshop.server.api.assembler;

import org.springframework.stereotype.Component;

import dev.blasio99.webshop.common.dto.OrderDTO;
import dev.blasio99.webshop.server.model.Orders;

@Component
public class OrderAssembler implements BaseAssembler<OrderDTO, Orders> {
	
    @Override
    public Orders createModel(OrderDTO dto) {
        Orders order = new Orders();
        order.setId(dto.getId());
		order.setQuantity(dto.getQuantity());
		order.setProductId(dto.getProductId());
		order.setUsername(dto.getUsername());
        return order;
    }
	
    @Override
    public OrderDTO createDTO(Orders model) {
        OrderDTO dto = new OrderDTO();
        dto.setId(model.getId());
		dto.setQuantity(model.getQuantity());
		dto.setProductId(model.getProductId());
		dto.setUsername(model.getUsername());
        return dto;
    }
}
