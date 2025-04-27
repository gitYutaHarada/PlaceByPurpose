package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CenterCandidateListDto {

	private List<CenterCandidateDto> centerCandidateListDto;
	
	public CenterCandidateListDto() {
		
		this.centerCandidateListDto = new ArrayList<>();
	
	}

	public void add(CenterCandidateDto centerCandidateDto) {
		
		centerCandidateListDto.add(centerCandidateDto);
	}
	
}
