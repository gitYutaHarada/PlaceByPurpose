package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MapDataListDto {

	List<MapDataDto> mapDataListDto = new ArrayList<>();

	public void add(MapDataDto mapDataDto) {
		
		mapDataListDto.add(mapDataDto);		
	
	}
	
}
