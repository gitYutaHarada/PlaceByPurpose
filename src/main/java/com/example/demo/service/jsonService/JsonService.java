package com.example.demo.service.jsonService;

import com.example.demo.dto.MapDataListDto;
import com.fasterxml.jackson.databind.JsonNode;

public interface JsonService {
	
	JsonNode stringToJson(String str);
	
	MapDataListDto jsonToMapDataListDto(JsonNode mapDataJson);
	
}
