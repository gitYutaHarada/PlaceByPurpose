package com.example.demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.Session.ChatSession;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GrobalAdviceController {

	private final ChatSession chatSession;
	
	
	@ModelAttribute("chatSession")
	public ChatSession addUserSession() {
		return chatSession;
	}

	
}
