function map() {
    const centerLocation =  allPlacesJson.results[0].location;

    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 12,
        center: centerLocation
    });
	
	const infoWindow = new google.maps.InfoWindow();
	
	const results = allPlacesJson.results;
	
	results.forEach(mapData => {
		
		const marker = new google.maps.Marker({
		    position: {lat: mapData.location.lat, lng:mapData.location.lng},
		    map: map,
			title: mapData.name
		}); 
		
		marker.addListener("click", () => {
		    infoWindow.setContent(`
				<div style="color: black;">
					<strong>施設名：${mapData.name}</strong><br>
					<strong>住所：${mapData.formatted_address}</strong><br>
				</div>
			`);
		    infoWindow.open(map, marker);
		});
		
	});
	
}
