package com.example.demo.service.json;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CenterCandidateDto;
import com.example.demo.dto.CenterCandidateListDto;
import com.example.demo.dto.MapDataDto;
import com.example.demo.dto.MapDataListDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class JsonOthersServiceImpl implements JsonOthersService {

	@Override
	public MapDataListDto jsonToMapDataListDto(JsonNode mapDataJson) {
		
		MapDataListDto mapDataList = new MapDataListDto();
		
		if(mapDataJson.isArray()) {
			for(JsonNode json : mapDataJson) {
				MapDataDto mapDataDto = new MapDataDto();
				mapDataDto.setFacilityName(json.get("facilityName").asText());
				mapDataDto.setAddress(json.get("address").asText());
				mapDataDto.setLat(json.get("lat").asDouble());
				mapDataDto.setLng(json.get("lng").asDouble());
				mapDataList.add(mapDataDto);
			}
		}
		
		return mapDataList;
	}

	@Override
	public List<String> JsonToListForKeyword(JsonNode typeKeywordJson) {
		List<String> keywordList = new ArrayList<>();
		JsonNode keywords = typeKeywordJson.get("keyword");
		if(keywords != null && keywords.isArray()) {
			for(JsonNode keyword : keywords) {
				keywordList.add(keyword.asText());
			}
		}
		return keywordList;
	}

	@Override
	public List<String> JsonToListForType(JsonNode typeKeywordJson) {
		List<String> typeList = new ArrayList<>();
		JsonNode types = typeKeywordJson.get("type");
		if(types != null && types.isArray()) {
			for(JsonNode type : types) {
				typeList.add(type.asText());
			}
		}
		return typeList;
	}

	@Override
	public CenterCandidateListDto JsonToCenterCandidateListDto(ObjectNode objectNode) {
		JsonNode results = objectNode.get("results");
		CenterCandidateListDto centerCandidateListDto = new CenterCandidateListDto();
		
		for(JsonNode result : results) {
			CenterCandidateDto centerCandidateDto = new CenterCandidateDto();
			centerCandidateDto.setFacilityName(result.get("name").asText());
			centerCandidateDto.setAddress(result.get("formatted_address").asText());
			if(result.has("location")) {
				JsonNode location = result.get("location");
				centerCandidateDto.setLat(location.get("lat").asDouble());
				centerCandidateDto.setLng(location.get("lng").asDouble());
			}
			centerCandidateListDto.add(centerCandidateDto);
		}
		
		return centerCandidateListDto;
	}

}
