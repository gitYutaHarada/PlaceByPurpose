package com.example.demo.service.string;

import org.springframework.stereotype.Service;

@Service
public class StringServiceImpl implements StringService {

	@Override
	public String prepareStringToJson(String str) {
		
		str = str.replaceAll("^```json", "").trim();
		str = str.replaceAll("$```", "").trim();
		
		return str;
	}

}
