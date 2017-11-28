var app = angular.module('pubNFun', ['easyFacebook','ngRoute'])
.config(function($routeProvider){
    $routeProvider
    .when("/home", {
        templateUrl: "assets/templates/pages/home/index.html",
        controller: "homeController"
    }) 
    .when("/pubs", {
        templateUrl: "assets/templates/pages/pubs/index.html",
        controller: "pubsController"
    })
    .otherwise({
        templateUrl: "assets/templates/pages/home/index.html"
    })
})


.controller("homeController", function($scope){
})
.controller("pubsController", function($scope, $http){
    
    $http.get('http://pubnfun.azurewebsites.net/PubnFunCore.svc//GetAllPub')
    .then(function(data){
        for(var i = 0; i < data.length; i++){
            if(data[i] != null){
                $('.review').append('<div class="pub">' + data[i].name + '</div>');
            }
        }
    });
    
    
    $scope.initMap = function() {
        var map = new google.maps.Map(document.getElementById('google-map'), {
            center: {lat: 47.5421887, lng: 21.6395724},
            zoom: 16,
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
        
        
        $scope.initMap();
        
    });