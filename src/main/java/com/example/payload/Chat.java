package com.example.payload;

import lombok.Data;

@Data
public class Chat{
	private long id;
	private String type;
	private String firstName;
	private String username;
}