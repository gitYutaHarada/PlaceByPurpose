package com.example.demo.service.json;

import java.util.List;

import com.example.demo.dto.CenterCandidateListDto;
import com.example.demo.dto.MapDataListDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonOthersService {
		
	MapDataListDto jsonToMapDataListDto(JsonNode mapDataJson);

	List<String> JsonToListForKeyword(JsonNode typeKeywordJson);
	
	List<String> JsonToListForType(JsonNode typeKeywordJson);
	
	CenterCandidateListDto JsonToCenterCandidateListDto(ObjectNode objectNode);
}
