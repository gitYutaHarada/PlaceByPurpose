package com.example.demo.service.range;

import org.springframework.stereotype.Service;

@Service
public class RangeServiceImpl implements RangeService {

	@Override
	public int calculateRadiusMeters(String method, int minutes) {
		int wolkSpeed = 80;
		if("徒歩".equals(method)) {
			return minutes * wolkSpeed;
		}
		return 0;
	}

}
