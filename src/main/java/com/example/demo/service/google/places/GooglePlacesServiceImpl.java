package com.example.demo.service.google.places;

import java.net.URI;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GooglePlacesServiceImpl implements GooglePlacesService {
	
    ResourceBundle config = ResourceBundle.getBundle("application");
    String key = config.getString("google.api.key");
    String baseUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json";

    private final RestTemplate restTemplate = new RestTemplate();

    public String searchPlaces(String query, double lat, double lng, int radiusMeters) {
        String url = UriComponentsBuilder
        		.fromUri(URI.create(baseUrl))
                .queryParam("query", query)
                .queryParam("location", lat + "," + lng)
                .queryParam("radius", radiusMeters)
                .queryParam("language", "ja")
                .queryParam("key", key)
                .toUriString();

        return restTemplate.getForObject(url, String.class); // JSONがそのまま返る
    }
    
}
