package com.example.demo.service.google.places;

public interface GooglePlacesService {

	String searchPlaces(String query, double lat, double lng, int radiusMeters);
	
}
