package com.espublico.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.espublico.apirest.dto.OrderResponse;
import com.espublico.apirest.model.Order;
import com.espublico.apirest.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl extends AbstractClient implements OrderService {

	public OrderServiceImpl(RestTemplate restTemplate) {
		super(restTemplate);
	}

	@Autowired
	private OrderRepository  orderRepository;

	@Override
	public OrderResponse findAll() {
		String uri = baseUrl + "orders";

		ResponseEntity<OrderResponse> response = restTemplate.getForEntity(uri, OrderResponse.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			log.info("Successfully user creation: {}", response.getBody().getStatus());
			return response.getBody();
		}
		log.error("Error in user creation - httpStatus was: {}", response.getStatusCode());
		throw new RuntimeException("Error");
	}
	
	@Override
	public Order findOne(String uuid) {
		String uri = baseUrl + "orders/".concat(uuid);
		ResponseEntity<Order> response = restTemplate.getForEntity(uri, Order.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			log.info("Successfully user creation: {}", response.getBody());
			return response.getBody();
		}
		log.error("Error in user creation - httpStatus was: {}", response.getStatusCode());
		throw new RuntimeException("Error");
	}

	public List<Order> createOrders(List<Order> orderResponse) {
		return orderRepository.saveAll(orderResponse);
	}

}
