package com.espublico.apirest.controller;

import com.espublico.apirest.dto.OrderResponse;
import com.espublico.apirest.model.Order;
import com.espublico.apirest.service.CsvExportService;
import com.espublico.apirest.service.OrderService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("espublicotech/g3stiona")
public class OrderController {
	
	@Autowired
	CsvExportService csvExportService;
 
    private final OrderService orderService;

    public OrderController(OrderService  orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public OrderResponse findAll() {
    	OrderResponse listaOrder = this.orderService.findAll();
    	listaOrder.getContent().forEach(oderComp -> orderService.findOne(oderComp.getUuid()));
    	this.orderService.createOrders(listaOrder.getContent());
    
        Predicate<Order> p2 = (f) -> f.getItem_type().equals("Clothes");
        List<Order> nuevaLista=listaOrder.getContent().stream().filter(p2).collect(Collectors.toList());
        System.out.println(nuevaLista.size());

    	return listaOrder;
    }
    
    @RequestMapping(path = "/csvOrders")
    public void getAllEmployeesInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"employees.csv\"");
        csvExportService.writeEmployeesToCsv(servletResponse.getWriter());
    }
}
