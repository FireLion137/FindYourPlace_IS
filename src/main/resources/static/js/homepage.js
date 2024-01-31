let map, markersGroup, marker, circle;

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

    map.on('click', addMarkerClick)

    $('#raggio').on('change', function() {
        let raggio = parseFloat($(this).val()) * 1000;
        addCircle(raggio);
    });
});

function addMarkerClick(e) {
    markersGroup.clearLayers();
    marker = L.marker(e.latlng).addTo(markersGroup);
    document.getElementById('latitude').value = marker.getLatLng().lat;
    document.getElementById('longitude').value = marker.getLatLng().lng;

    addCircle(parseFloat($('#raggio').val()) * 1000);
}
function addMarkerSearch(lat, lng) {
    markersGroup.clearLayers();
    marker = L.marker([lat, lng]).addTo(markersGroup);
}

function addCircle(raggio) {
    // Rimuovi il cerchio precedente
    if (circle) {
        map.removeLayer(circle);
    }

    // Crea un nuovo cerchio con il raggio indicato e posizionalo sul marker
    if (marker) {
        circle = L.circle(marker.getLatLng(), {
            radius: raggio
        }).addTo(map);
    }
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
                    let lat = data[0].lat;
                    let lon = data[0].lon;
                    document.getElementById('latitude').value = lat;
                    document.getElementById('longitude').value = lon;
                    map.setView([lat, lon], 10);
                    addMarkerSearch(lat, lon);

                    let raggio = parseFloat($('#raggio').val()) * 1000;
                    addCircle(raggio);
                }
            });
    }
}

$(function(){
    let sliderRaggio = document.getElementById("raggio");
    let outputRaggio = document.getElementById("raggioValue");
    sliderRaggio.oninput = function() {
        outputRaggio.innerHTML = "Raggio in Km: " + this.value;
    }

    let sliderdangerMax = document.getElementById("dangerMax");
    let outputdangerMax = document.getElementById("dangerMaxValue");
    sliderdangerMax.oninput = function() {
        outputdangerMax.innerHTML = "Pericolosità massima: " + this.value + "%";
    }

    let slidernumAbitantiMin = document.getElementById("numAbitantiMin");
    let outputnumAbitantiMin = document.getElementById("numAbitantiMinValue");
    slidernumAbitantiMin.oninput = function() {
        outputnumAbitantiMin.innerHTML = "Numero abitanti minimo: " + this.value;
    }

    let slidernumAbitantiMax = document.getElementById("numAbitantiMax");
    let outputnumAbitantiMax = document.getElementById("numAbitantiMaxValue");
    slidernumAbitantiMax.oninput = function() {
        outputnumAbitantiMax.innerHTML = "Numero abitanti massimo: " + this.value;
    }

    let slidernumNegoziMin = document.getElementById("numNegoziMin");
    let outputnumNegoziMin = document.getElementById("numNegoziMinValue");
    slidernumNegoziMin.oninput = function() {
        outputnumNegoziMin.innerHTML = "Numero negozi minimo: " + this.value;
    }

    let slidernumRistorantiMin = document.getElementById("numRistorantiMin");
    let outputnumRistorantiMin = document.getElementById("numRistorantiMinValue");
    slidernumRistorantiMin.oninput = function() {
        outputnumRistorantiMin.innerHTML = "Numero ristoranti minimo: " + this.value;
    }

    let slidernumScuoleMin = document.getElementById("numScuoleMin");
    let outputnumScuoleMin = document.getElementById("numScuoleMinValue");
    slidernumScuoleMin.oninput = function() {
        outputnumScuoleMin.innerHTML = "Numero scuole minimo: " + this.value;
    }
})