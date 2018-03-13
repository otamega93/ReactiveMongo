package com.reactive.example.ReactiveMongo.entities;

public class ErrorDto {
	
	private String name;
	
	private String message;

	public ErrorDto(String name, String message) {
		super();
		this.name = name;
		this.message = message;
	}

	public ErrorDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
