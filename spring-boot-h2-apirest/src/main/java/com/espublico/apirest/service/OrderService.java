package com.espublico.apirest.service;

import java.util.List;

import com.espublico.apirest.dto.OrderResponse;
import com.espublico.apirest.model.Order;

public interface OrderService {
	OrderResponse findAll();
	List<Order>  createOrders(List<Order> orderResponse);
	Order findOne(String uuid);
}
