package com.example.demo.service.set.model;

import org.springframework.ui.Model;

import com.example.demo.dto.MapDataListDto;

public interface SetModelService {

	void setMapDataListDtoToModel(Model model, MapDataListDto mapDataListDto);
	
}
