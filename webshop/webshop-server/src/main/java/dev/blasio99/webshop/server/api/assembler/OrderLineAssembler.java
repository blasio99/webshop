package dev.blasio99.webshop.server.api.assembler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import dev.blasio99.webshop.common.dto.OrderLineDTO;
import dev.blasio99.webshop.server.enums.Payment;
import dev.blasio99.webshop.server.enums.Status;
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
		orderLine.setStatus(Status.valueOf(dto.getStatus()));
		orderLine.setPayment(Payment.valueOf(dto.getPayment()));
        return orderLine;
    }
	
    @Override
    public OrderLineDTO createDTO(OrderLine model) {
        OrderLineDTO dto = new OrderLineDTO();
        dto.setId(model.getId());
		dto.setOrderIdList(model.getOrderIdList());
		dto.setOrderDate(dtf.format(model.getOrderDate()));
		dto.setUsername(model.getUsername());
		dto.setStatus(model.getStatus().name());
		dto.setPayment(model.getPayment().name());
        return dto;
    }
}

