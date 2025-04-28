package com.example.demo.service.range;

import org.springframework.stereotype.Service;

@Service
public class RangeServiceImpl implements RangeService {

	@Override
	public int calculateRadiusMeters(String transportation, int minutes) {
		int wolkSpeed = 80;
		int bicycleSpeed = 250;
		int bikeSpeed = 500;
		
		if("walk".equals(transportation)) {
			return minutes * wolkSpeed;
		}else if("bicycle".equals(transportation)) {
			return minutes * bicycleSpeed;
		}else if("bike".equals(transportation)) {
			return minutes * bikeSpeed;
		}
		
		return 0;
	}

}
