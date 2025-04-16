package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.google.places.GooglePlacesService;
import com.example.demo.service.json.JsonOthersService;
import com.example.demo.service.json.JsonUtilsService;
import com.example.demo.service.openAI.OpenAIService;
import com.example.demo.service.range.RangeService;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResponseController {

	private final GooglePlacesService googlePlacesService;
	private final JsonOthersService jsonOthersService;
	private final JsonUtilsService jsonUtilsService;
	private final OpenAIService openAIService;
	private final RangeService rangeService;

	@Value("${google.api.key}")
	private String googleApiKey;
	
	@GetMapping("/")
	public String getMapping() {
		return "index";
	}
	
	@PostMapping("/")
	public String response(@RequestParam String minutes, 
						   @RequestParam String purpose, 
						   Model model,
						   RedirectAttributes redirectAttributes){
		
		
		//GPTAPIからキーワード文字列を取得
		String typeKeywordSetString = openAIService.ChatGPT(minutes, purpose);	
		//キーワード文字列をJSONに変換
		JsonNode typeKeywordSetJson = jsonUtilsService.stringToJson(typeKeywordSetString);
		
		List<String> keywordList = jsonOthersService.JsonToListForKeyword(typeKeywordSetJson);
		List<String> typeList = jsonOthersService.JsonToListForType(typeKeywordSetJson);	
		
		int radiusMeters = rangeService.calculateRadiusMeters("徒歩", Integer.parseInt(minutes));
		//淵野辺駅の緯度経度
		double lat = 35.5684909, lng = 139.3952879;
		//キーワードをもとにGooglePlaceAPIで場所検索,検索した結果がすべてJson形式のString型で返される。
		String allPlacesString = 
				googlePlacesService.searchAllPlacesByTypeKeywordList(
						typeList,
						keywordList, 
						lat, 
						lng, 
						radiusMeters);
				
		System.out.println(keywordList.toString());
		System.out.println(radiusMeters);
		System.out.println(purpose);
		
		
		redirectAttributes.addFlashAttribute("allPlacesString", allPlacesString);
	    redirectAttributes.addFlashAttribute("googleApiKey", googleApiKey);
		
		return "redirect:/";
	}
}
