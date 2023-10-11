package com.espublico.apirest.model;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
	private String uuid;
	private String region;
	private String country;
	private String item_type;
	private String sales_channel;
	private String priority;
	private String date;
	private String ship_date;
	private Integer units_sold;
	private Double unit_price;
	private Double unit_cost;
	private Double total_revenue;
	private Double total_cost;
	private Double total_profit;
}
