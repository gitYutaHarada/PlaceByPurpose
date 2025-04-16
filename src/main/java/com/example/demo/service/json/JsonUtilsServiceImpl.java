package com.example.demo.service.json;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonUtilsServiceImpl implements JsonUtilsService {

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
	
}
