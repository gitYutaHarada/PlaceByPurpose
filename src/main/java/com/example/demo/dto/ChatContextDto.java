package com.example.demo.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ChatContextDto {

	private String speaker;
	private String context;
	
}
