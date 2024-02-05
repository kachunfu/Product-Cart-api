package com.fdmgroup.productCartApi.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@JsonProperty("productId")
	private Long productId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("price")
	private BigDecimal price;

}
