package com.example.demo.service.json;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MapDataDto;
import com.example.demo.dto.MapDataListDto;
import com.fasterxml.jackson.databind.JsonNode;

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

}
