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

.service("pubService", function($http){
    this.getPubs = function(){
        return $http.get('http://pubnfun.azurewebsites.net/PubnFunCore.svc//GetAllPub');
        
    };
})


.controller("homeController", function($scope){
})
.controller("pubsController", ['$scope','pubService', function($scope, pubService){
    $scope.active = 0;

    $scope.setActiveId = function(idToSet){
        if($scope.active === idToSet){
            $scope.active = 0;
        }
        else{
            $scope.active = idToSet;
        }
        console.log($scope.active);
    };

    $scope.isActive = function(id){
        return $scope.active === id;
    };

    
    
    console.log(pubService);
    $scope.pubs = [];

    pubService.getPubs().then(function(data){
        $scope.pubs = data.data;

        console.log(data);
    });

    console.log($scope.pubs);


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
                $scope.handleLocationError(true);
            });
        } else {
            // Browser doesn't support Geolocation
            $scope.handleLocationError(false);
        }
    }
    
    $scope.handleLocationError = function(browserHasGeolocation) {
        console.log(browserHasGeolocation ? 
            'Error: The Geolocation service failed.' :
            'Error: Your browser doesn\'t support geolocation.');
        }
        
        $scope.initMap();
    }]);