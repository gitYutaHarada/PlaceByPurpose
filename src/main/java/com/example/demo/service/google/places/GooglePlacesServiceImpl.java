package com.example.demo.service.google.places;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class GooglePlacesServiceImpl implements GooglePlacesService {

	//    ResourceBundle config = ResourceBundle.getBundle("application");
	//    String key = config.getString("google.api.key");

	@Value("${google.api.key}")
	private String key;

	String baseUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json";

	private final RestTemplate restTemplate = new RestTemplate();

	//キーワード検索した結果を取得引数緯度経度が中心そこから半径radiusMeters
	@Override
	public String searchPlacesByKeyword(String keyword, double lat, double lng, int radiusMeters) {
		String url = UriComponentsBuilder
				.fromUri(URI.create(baseUrl))
				.queryParam("query", keyword)
				.queryParam("location", lat + "," + lng)
				.queryParam("radius", radiusMeters)
				.queryParam("language", "ja")
				.queryParam("key", key)
				.toUriString();
		//        String url = UriComponentsBuilder
		//        	    .fromUri(URI.create("https://maps.googleapis.com/maps/api/place/nearbysearch/json"))
		//        	    .queryParam("location", lat + "," + lng)
		//        	    .queryParam("radius", radiusMeters)
		//        	    .queryParam("type", keyword)  // 例: "cafe"
		//        	    .queryParam("language", "ja")
		//        	    .queryParam("key", key)
		//        	    .toUriString();
		return restTemplate.getForObject(url, String.class); // JSONがそのまま返る
	}

	//全てのキーワード検索をListにaddして返す
	@Override
	public String searchAllPlacesByTypeKeywordList(List<String> typeList, List<String> keywordList, double lat,
			double lng, int radiusMeters) {
		ObjectMapper mapper = new ObjectMapper(); //Jacksonのメインクラス
		ArrayNode allPlaces = mapper.createArrayNode(); //Jsonの配列
		Set<String> placeIds = new HashSet<>();

		for (String keyword : keywordList) {
			String json = searchPlacesByKeyword(keyword, lat, lng, radiusMeters);
			try {
				JsonNode parsed = mapper.readTree(json); //jacksonのJsonNodeツリー型に変換
				JsonNode results = parsed.get("results"); //“result”の部分だけ取得

				//同じ場所かどうか判定
				if (results != null && results.isArray()) {
					for (JsonNode place : results) {
						String placeId = place.get("place_id").asText();
						System.out.println(keyword + place.get("name").asText());

						if (!placeIds.contains(placeId)/* && isValidPlace(place, typeList)*/) {
							placeIds.add(placeId);
							ObjectNode extractedPlace = extractPlaceInfo(place, mapper);

							if (extractedPlace.has("location")) {
								JsonNode locationNode = extractedPlace.get("location");
								double placeLat = locationNode.get("lat").asDouble();
								double placeLng = locationNode.get("lng").asDouble();
								double distance = calculateDistance(lat, lng, placeLat, placeLng);

								// 指定された半径内かどうかをチェック
								if (distance <= radiusMeters) {
									// 距離をフィールドとして追加
									extractedPlace.put("distance", distance);
									placeIds.add(placeId);
									allPlaces.add(extractedPlace);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//ObjectMapperクラスのメソッドであるcreateObjectNode（）はからのJsonを作る
		ObjectNode root = mapper.createObjectNode();
		root.set("results", allPlaces);//set

		try {
			//.writerWithDefaultPrettyPrinter()コンソールなどで見やすくなるように改行や空白を挿入
			//.writeValueAsString(root)JSON文字（String型）に変換する
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
		} catch (Exception e) {
			return "{}";
		}
	}

	@Override
	public boolean isValidPlace(JsonNode place, List<String> typeList) {
		JsonNode types = place.get("types");
		if (types != null && types.isArray()) {
			for (JsonNode typeNode : types) {
				if (typeList.contains(typeNode.asText())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ObjectNode extractPlaceInfo(JsonNode place, ObjectMapper mapper) {
		ObjectNode extractedPlace = mapper.createObjectNode();

		if (place.has("name")) {
			extractedPlace.put("name", place.get("name").asText());
		}

		if (place.has("types")) {
			extractedPlace.set("types", place.get("types"));
		}

		if (place.has("formatted_address")) {
			extractedPlace.put("formatted_address", place.get("formatted_address").asText());
		}

		if (place.has("geometry") && place.get("geometry").has("location")) {
			JsonNode locationNode = place.get("geometry").get("location");
			ObjectNode location = mapper.createObjectNode();

			if (locationNode.has("lat")) {
				location.put("lat", locationNode.get("lat").asDouble());
			}
			if (locationNode.has("lng")) {
				location.put("lng", locationNode.get("lng").asDouble());
			}

			extractedPlace.set("location", location);
		}

		if (place.has("user_ratings_total")) {
			extractedPlace.put("user_ratings_total", place.get("user_ratings_total").asInt());
		}

		if (place.has("opening_hours") && place.get("opening_hours").has("open_now")) {
			extractedPlace.put("open_now", place.get("opening_hours").get("open_now").asBoolean());
		}

		return extractedPlace;
	}

	private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
		final int EARTH_RADIUS = 6371000; // 地球の半径（メートル）
		double latDistance = Math.toRadians(lat2 - lat1);
		double lngDistance = Math.toRadians(lng2 - lng1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
						* Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return EARTH_RADIUS * c;
	}

	@Override
	public String searchPlacesByOnlyKeyword(String centerName) {
		URI uri = UriComponentsBuilder
				.fromUriString(baseUrl)
				.queryParam("query", centerName)
				.queryParam("language", "ja")
				.queryParam("key", key)
		        .build()
		        .toUri();
		System.out.println(uri.toString());

		return restTemplate.getForObject(uri, String.class);
	}

	@Override
	public ObjectNode searchCenterCandidate(String centerName) {
		ObjectMapper mapper = new ObjectMapper(); //Jacksonのメインクラス
		ArrayNode allPlaces = mapper.createArrayNode(); //Jsonの配列

		String json = searchPlacesByOnlyKeyword(centerName);
		try {
			JsonNode parsed = mapper.readTree(json); //jacksonのJsonNodeツリー型に変換
			JsonNode results = parsed.get("results"); //“result”の部分だけ取得

			if(results.isArray()) {
				for(JsonNode place : results) {
					ObjectNode extractedPlace = extractPlaceInfo(place, mapper);
					allPlaces.add(extractedPlace);

				}
					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ObjectMapperクラスのメソッドであるcreateObjectNode（）はからのJsonを作る
		ObjectNode root = mapper.createObjectNode();
		root.set("results", allPlaces);//set
        
		return root;
		
	}

}
