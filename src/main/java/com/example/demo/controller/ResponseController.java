package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.jsonService.JsonService;
import com.example.demo.service.openAI.OpenAIService;
import com.example.demo.service.string.StringService;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResponseController {

	private final OpenAIService openAIService;
	private final JsonService jsonService;
	private final StringService stringService;

	@Value("${google.api.key}")
	private String googleApiKey;
	
	@PostMapping("/response")
	public String response(@RequestParam String range, 
						   @RequestParam String purpose, 
						   Model model){
		
		
		String keywordString = openAIService.ChatGPT(range, purpose);	
		System.out.println(keywordString);
		
		keywordString = stringService.prepareStringToJson(keywordString);
		JsonNode keywordJson = jsonService.stringToJson(keywordString);
		
		System.out.println(keywordJson);
		model.addAttribute("keywordJson", keywordJson);
		
		
		
		model.addAttribute("googleApiKey", googleApiKey);
		return "index";
	}
}
