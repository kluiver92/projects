package com.espublico.apirest.service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.espublico.apirest.model.Order;
import com.espublico.apirest.repository.OrderRepository;

@Service
public class CsvExportService {
	Logger log = LoggerFactory.getLogger(CsvExportService.class);

	private final OrderRepository orderRepository;

	public CsvExportService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public void writeOrdersToCsv(Writer writer) throws IOException {
		ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

		List<Order> orders = orderRepository.findAll();
		if (orders.isEmpty()) {
			String[] isNull = { "No hay registros en base de datos" };
			csvWriter.writeHeader(isNull);
		} else {
			String[] csvHeader = { "Order ID", "Order Priority", "Order Date", "Region", "Country", "Item Type",
					"Sales Channel", "Ship Date", "Units Sold", "Unit Price", "Unit Cost", "Total Revenue",
					"Total Cost", "Total Profit" };
			String[] nameMapping = { "uuid", "priority", "date", "region", "country", "item_type", "sales_channel",
					"ship_date", "units_sold", "unit_price", "unit_cost", "total_revenue", "total_cost",
					"total_profit" };

			csvWriter.writeHeader(csvHeader);

			for (Order order : orders) {
				csvWriter.write(order, nameMapping);
			}
		}

		csvWriter.close();
	}
}