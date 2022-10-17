package com.example.payload;

import lombok.Data;

@Data
public class From{
	private long id;
	private boolean isBot;
	private String firstName;
	private String username;
}