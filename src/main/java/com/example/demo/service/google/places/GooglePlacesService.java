package com.example.demo.service.google.places;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface GooglePlacesService {

	String searchPlacesByKeyword(String keyword, double lat, double lng, int radiusMeters);
	
	String searchAllPlacesByTypeKeywordList(List<String> typeList, List<String> keywordList, double lat, double lng, int radiusMeters);
	
	boolean isValidPlace(JsonNode place, List<String> typeList);
	
	ObjectNode extractPlaceInfo(JsonNode place, ObjectMapper mapper);

	ObjectNode searchCenterCandidate(String centerName);
	
	String searchPlacesByOnlyKeyword(String keyword);
	
}
