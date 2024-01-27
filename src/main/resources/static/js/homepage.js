let map, markersGroup, marker, markerLocation;

let southWest = L.latLng(36.619987291, 6.7499552751),
    northEast = L.latLng(47.1153931748, 18.4802470232),
    bounds = L.latLngBounds(southWest, northEast);

$(function(){
    map = L.map('map').setView([42.6384261,12.674297], 5);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '<a href="https://www.openstreetmap.org/">OpenStreetMap</a>',
        maxBounds: bounds,
        maxZoom: 18
    }).addTo(map);

    map.setMaxBounds(map.getBounds());

    markersGroup = L.layerGroup();
    map.addLayer(markersGroup);

    map.on('click', addMarker)
});

function addMarker(e) {
    markersGroup.clearLayers();
    marker = L.marker(e.latlng).addTo(markersGroup);
}

function searchAddress() {
    var address = document.getElementById('addressInput').value;
    if (address) {
        fetch('https://nominatim.openstreetmap.org/search?format=json&q=' + address + ', Italy')
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                if (data.length > 0) {
                    var lat = data[0].lat;
                    var lon = data[0].lon;
                    document.getElementById('latitude').value = lat;
                    document.getElementById('longitude').value = lon;
                    map.setView([lat, lon], 13);
                }
            });
    }
}