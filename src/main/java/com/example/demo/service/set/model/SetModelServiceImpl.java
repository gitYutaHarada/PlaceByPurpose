package com.example.demo.service.set.model;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.dto.MapDataDto;
import com.example.demo.dto.MapDataListDto;

@Service
public class SetModelServiceImpl implements SetModelService {

	@Override
	public void setMapDataListDtoToModel(Model model, MapDataListDto mapDataListDto) {
		
		for(MapDataDto mapData : mapDataListDto.getMapDataListDto()) {
			model.addAttribute("facilityName", mapData.getFacilityName());
			model.addAttribute("address", mapData.getAddress());
			model.addAttribute("lat", mapData.getLat());
			model.addAttribute("lng", mapData.getLng());
		}
		
	}

}
