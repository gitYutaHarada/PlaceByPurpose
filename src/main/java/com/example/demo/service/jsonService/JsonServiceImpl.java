package com.example.demo.service.jsonService;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MapDataDto;
import com.example.demo.dto.MapDataListDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonServiceImpl implements JsonService {

	@Override
	public JsonNode stringToJson(String str) {
		ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        
		try {
			jsonNode = mapper.readTree(str);
			System.out.println(jsonNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return jsonNode;
	}

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

}
