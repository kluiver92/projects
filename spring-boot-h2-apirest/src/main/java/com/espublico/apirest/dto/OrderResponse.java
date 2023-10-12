package com.espublico.apirest.dto;

import com.espublico.apirest.model.Order;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    public String status;
    
    @JsonProperty("content")
    public List<Order> content;
    
    
    @JsonProperty("conteo")
    public List<String> conteo;
}
