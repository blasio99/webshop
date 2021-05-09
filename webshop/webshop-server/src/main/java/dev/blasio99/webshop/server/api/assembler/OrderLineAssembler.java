package dev.blasio99.webshop.server.api.assembler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import dev.blasio99.webshop.common.dto.OrderLineDTO;
import dev.blasio99.webshop.server.model.OrderLine;

@Component
public class OrderLineAssembler implements BaseAssembler<OrderLineDTO, OrderLine> {
	
	private DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	
    @Override
    public OrderLine createModel(OrderLineDTO dto) {
        OrderLine orderLine = new OrderLine();
        orderLine.setId(dto.getId());
		orderLine.setOrderIdList(dto.getOrderIdList());
		orderLine.setOrderDate(LocalDateTime.parse(dto.getOrderDate(), dtf));
		orderLine.setUsername(dto.getUsername());
        return orderLine;
    }
	
    @Override
    public OrderLineDTO createDTO(OrderLine model) {
        OrderLineDTO dto = new OrderLineDTO();
        dto.setId(model.getId());
		dto.setOrderIdList(model.getOrderIdList());
		dto.setOrderDate(dtf.format(model.getOrderDate()));
		dto.setUsername(model.getUsername());
        return dto;
    }
}

