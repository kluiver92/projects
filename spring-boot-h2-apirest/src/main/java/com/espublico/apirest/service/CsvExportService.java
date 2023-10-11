package com.espublico.apirest.service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.espublico.apirest.model.Order;
import com.espublico.apirest.repository.OrderRepository;

@Service
public class CsvExportService {
	    Logger log = LoggerFactory.getLogger(CsvExportService.class);

	    private final OrderRepository orderRepository;

	    public CsvExportService(OrderRepository orderRepository) {
	        this.orderRepository = orderRepository;
	    }

	    public void writeEmployeesToCsv(Writer writer) {

	        List<Order> orders = orderRepository.findAll();
	        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
	            csvPrinter.printRecord("ID", "First Name", "Last Name","Email","Department");
	            for (Order order : orders) {
	                csvPrinter.printRecord(order.getId(), order.getPriority(), order.getDate(), order.getRegion(), order.getItem_type());
	            }
	        } catch (IOException e) {
	            log.error("Error While writing CSV ", e);
	        }
	    }
	}