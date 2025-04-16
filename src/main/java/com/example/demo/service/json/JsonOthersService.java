package com.example.demo.service.json;

import java.util.List;

import com.example.demo.dto.MapDataListDto;
import com.fasterxml.jackson.databind.JsonNode;

public interface JsonOthersService {
		
	MapDataListDto jsonToMapDataListDto(JsonNode mapDataJson);

	List<String> JsonToListForKeyword(JsonNode typeKeywordJson);
	
	List<String> JsonToListForType(JsonNode typeKeywordJson);
	
}
