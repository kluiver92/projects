package com.espublico.apirest.controller;

import com.espublico.apirest.dto.OrderResponse;
import com.espublico.apirest.model.Order;
import com.espublico.apirest.service.CsvExportService;
import com.espublico.apirest.service.OrderService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    	OrderResponse orderResponse = this.orderService.findAll();
    	orderResponse.getContent().forEach(oderComp -> orderService.findOne(oderComp.getUuid()));
    	this.orderService.createOrders(orderResponse.getContent());
    	this.conteo(orderResponse);
    	return orderResponse;
    }
    
    @RequestMapping(path = "/csvOrders")
    public void getOrdersInCsv(HttpServletResponse servletResponse) throws IOException {
    	
    	 servletResponse.setContentType("text/csv");
         DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
         String currentDateTime = dateFormatter.format(new Date());
          
         String headerKey = "Content-Disposition";
         String headerValue = "attachment; filename=orders_" + currentDateTime + ".csv";
         servletResponse.setHeader(headerKey, headerValue);
    	
        csvExportService.writeOrdersToCsv(servletResponse.getWriter());
    }
    
        
    
    public OrderResponse conteo(OrderResponse orderResponse) {
    	List<String> tipos = new ArrayList<String>();
        Predicate<Order> p1 = (f) -> f.getItem_type().equals("Vegetables");
        Predicate<Order> p2 = (f) -> f.getItem_type().equals("Snacks");
        Predicate<Order> p3 = (f) -> f.getItem_type().equals("Personal Care");
        Predicate<Order> p4 = (f) -> f.getItem_type().equals("Office Supplies");
        Predicate<Order> p5 = (f) -> f.getItem_type().equals("Meat");
      /*  Predicate<Order> p6 = (f) -> f.getItem_type().equals("Household");
        Predicate<Order> p7 = (f) -> f.getItem_type().equals("Fruits");
        Predicate<Order> p8 = (f) -> f.getItem_type().equals("Cosmetics");
        Predicate<Order> p9 = (f) -> f.getItem_type().equals("Clothes");
        Predicate<Order> p10 = (f) -> f.getItem_type().equals("Cereal");
        Predicate<Order> p11 = (f) -> f.getItem_type().equals("Beverages");
        Predicate<Order> p12 = (f) -> f.getItem_type().equals("Baby Food");*/
        
        List<Order> cRegion =  orderResponse.getContent().stream().filter(p1).collect(Collectors.toList());
        String tipoRegion = "Vegetables tipo -> " + cRegion.size() ;
        tipos.add(tipoRegion);
        
        int cSnacks = orderResponse.getContent().stream().filter(p2).collect(Collectors.toList()).size();
        String tipoSnacks  = "Snacks  tipo -> " + cSnacks ;
        tipos.add(tipoSnacks);

        int cPc= orderResponse.getContent().stream().filter(p3).collect(Collectors.toList()).size();
        String tipocPc = "Personal Care  tipo -> " + cPc ;
        tipos.add(tipocPc);
        
        int cSupplies = orderResponse.getContent().stream().filter(p4).collect(Collectors.toList()).size();
        String tipoSupplies  = "Office Supplies  tipo -> " + cSupplies;
        tipos.add(tipoSupplies);
        
        int cMeat = orderResponse.getContent().stream().filter(p5).collect(Collectors.toList()).size();
        String tMeat  = "Meat  tipo -> " + cMeat ;
        tipos.add(tMeat);
        orderResponse.setConteo(tipos);
        return orderResponse;
    }
}
