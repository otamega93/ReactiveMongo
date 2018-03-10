package com.reactive.example.ReactiveMongo.entities;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
//@Getter
//@Setter
//@NoArgsConstructor
//@RequiredArgsConstructor
//@ToString
public class Product {

    @Id
    private String _id;
    
    @NotBlank
    private String name;
    
    private String description;
    
    private BigDecimal price;
    
    @NotBlank
    private String imageUrl;

	public Product(String _id, @NotBlank String name, String description, BigDecimal price,
			@NotBlank String imageUrl) {
		super();
		this._id = _id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imageUrl = imageUrl;
	}

	public Product(@NotBlank String name, String description, BigDecimal price, @NotBlank String imageUrl) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.imageUrl = imageUrl;
	}



	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	} 
    
}
