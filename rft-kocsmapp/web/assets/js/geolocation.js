var map;
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 47.5421887, lng: 21.6395724},
        zoom: 16
    });

    
    // Try HTML5 geolocation.
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };

            map.setCenter(pos);
        }, function() {
            handleLocationError(true);
        });
    } else {
        // Browser doesn't support Geolocation
        handleLocationError(false);
    }
}

function handleLocationError(browserHasGeolocation) {
    console.log(browserHasGeolocation ? 
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
    }