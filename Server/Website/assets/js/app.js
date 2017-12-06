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

.directive('submitreview', function(){
    return {
        restrict: 'E',
        templateUrl: 'assets/templates/review/index.html',
        replace: true
    };
})

.controller("ReviewController", function(){
    
        this.review = {};
      
        this.addReview = function(pub){
          this.review.createdOn = Date.now();
          product.reviews.push(this.review);
          this.review = {};
        };
    
      })

.directive('pubnfunmap', function() {
  var link = function(scope, element, attrs) {
    var map;
    var infoWindow;
    var markers = [];

    var mapOptions = {
      center: new google.maps.LatLng(47.5421887, 21.6395724),
      zoom: 16,
      mapTypeId: google.maps.MapTypeId.ROADMAP,
      scrollwheel: false
    };

    function initMap() {
        if (map === void 0) {
            map = new google.maps.Map(element[0], mapOptions);
        }

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

        var handleLocationError = function(browserHasGeolocation) {
            console.log(browserHasGeolocation ?
                'Error: The Geolocation service failed.' :
                'Error: Your browser doesn\'t support geolocation.');
            }
    }

    function setMarker(map, position, title, content) {
        var marker;
        var markerOptions = {
            position: position,
            map: map,
            title: title,
            icon: 'https://maps.google.com/mapfiles/ms/icons/green-dot.png'
        };

        marker = new google.maps.Marker(markerOptions);
        markers.push(marker); // add marker to array

        google.maps.event.addListener(marker, 'click', function () {
            // close window if not undefined
            if (infoWindow !== void 0) {
                infoWindow.close();
            }
            // create new window
            var infoWindowOptions = {
                content: content
            };
            infoWindow = new google.maps.InfoWindow(infoWindowOptions);
            infoWindow.open(map, marker);
        });
    }

    initMap();
//    setMarker(map, new google.maps.LatLng(47.5421887, 21.6395724), 'Ibolya', 'cool');
}

  return {
    restrict: 'E',
    template: '<div id="google-map"></div>',
    replace: true,
    link: link
  };

})

.service("pubService", function($http){
    this.getPubs = function(){
        return $http.get('http://rftpubnfun.azurewebsites.net/PubnFunCore.svc//GetAllPub');
    };

    this.getOpinionByPubID = function(pubID){
            return $http.get('http://rftpubnfun.azurewebsites.net/PubnFunCore.svc/GetAllOpinionAboutPubByID/' + pubID);
        };
})

.controller("homeController", function($scope){
})

.controller("pubsController", ['$scope','pubService', function($scope, pubService){
        $scope.active = 0;
        $scope.pubs = [];
        $scope.pubOpinions = [];


        $scope.setActiveId = function(idToSet){
            if($scope.active === idToSet){
            $scope.active = 0;
            } else {
            if ($scope.pubOpinions[idToSet]) {
                $scope.active = idToSet;
            } else {
                pubService.getOpinionByPubID(idToSet).then(function(opinions){
                $scope.pubOpinions[idToSet] = opinions.data;
                }).then(function(){
                $scope.active = idToSet;
                });
            }
            }
        };

        $scope.isActive = function(id){
            return $scope.active === id;
        };


        pubService.getPubs().then(function(pubs) {
            $scope.pubs = pubs.data;
        });

    }]);
