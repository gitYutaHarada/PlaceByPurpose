package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.CenterCandidateListDto;
import com.example.demo.dto.MapDataDto;
import com.example.demo.service.google.places.GooglePlacesService;
import com.example.demo.service.json.JsonOthersService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SelectCenterController {
	
	private final GooglePlacesService googlePlacesService;
	private final JsonOthersService jsonOthersService;


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
	
}
