package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.CenterCandidateListDto;
import com.example.demo.dto.MapDataDto;
import com.example.demo.service.google.places.GooglePlacesService;
import com.example.demo.service.json.JsonOthersService;
import com.example.demo.service.json.JsonUtilsService;
import com.example.demo.service.openAI.OpenAIService;
import com.example.demo.service.range.RangeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
	
	@PostMapping("/search-center")
	public String searchCenter(@RequestParam String centerName,
							   Model model) {
		
		//centerNameでtextsearchで検索して、DTO（placeId、地名、住所、緯度、経度）を返す。
		ObjectNode centerCandidateListObjectNode = googlePlacesService.searchCenterCandidate(centerName);
		CenterCandidateListDto centerCandidateListDto = jsonOthersService.JsonToCenterCandidateListDto(centerCandidateListObjectNode);

		System.out.println(centerCandidateListDto);
		model.addAttribute("centerCandidateListDto", centerCandidateListDto.getCenterCandidateListDto());
		
		return "index";
	}
	
	@PostMapping("/select-center")
	public String selectCenter(@ModelAttribute MapDataDto mapDataDto,
							   Model model){
		model.addAttribute("centerDataDto", mapDataDto);
		return "index";
	}
	
	@GetMapping("/")
	public String getMapping() {
		return "index";
	}
	
	@PostMapping("/")
	public String response(@ModelAttribute MapDataDto centerDataDto,
						   @RequestParam String minutes, 
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
		double lat = centerDataDto.getLat(), lng = centerDataDto.getLng();
		//キーワードをもとにGooglePlaceAPIで場所検索,検索した結果がすべてJson形式のString型で返される。
		String allPlacesString = 
				googlePlacesService.searchAllPlacesByTypeKeywordList(
						typeList,
						keywordList, 
						lat, 
						lng, 
						radiusMeters);
		
		redirectAttributes.addFlashAttribute("allPlacesString", allPlacesString);
	    redirectAttributes.addFlashAttribute("googleApiKey", googleApiKey);
		
		return "redirect:/";
	}
}
