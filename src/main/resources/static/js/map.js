function map() {
	
    const centerLocation = mapDataJson[0];

    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 12,
        center: centerLocation
    });
	
	const infoWindow = new google.maps.InfoWindow();
	
	mapDataJson.forEach(mapData => {
		
		const marker = new google.maps.Marker({
		    position: {lat: mapData.lat, lng:mapData.lng},
		    map: map,
			title: mapData.facilityName
		}); 
		
		marker.addListener("click", () => {
		    infoWindow.setContent(`
				<div style="color: black;">
					<strong>施設名：${mapData.facilityName}</strong><br>
					<strong>住所：${mapData.address}</strong><br>
				</div>
			`);
		    infoWindow.open(map, marker);
		});
		
	});
	
}
