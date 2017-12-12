var app = angular.module('pubNFun', ['easyFacebook','ngRoute'])

.config(function($routeProvider){
    $routeProvider
    .when("/home", {
        templateUrl: "assets/templates/pages/home/index.html",
        controller: "HomeController"
    })
    .when("/pubs", {
        templateUrl: "assets/templates/pages/pubs/index.html",
        controller: "PubsController"
    })
    .otherwise({
        templateUrl: "assets/templates/pages/home/index.html"
    })
})

.directive('submitreview', function(){  
    return {
        restrict: 'A',
        templateUrl: 'assets/templates/review/index.html'
    };
})

.directive('pubnfunmap', function() {
    var link = function(scope, element, attrs) {
        scope.map;
        scope.infoWindow;
        scope.markers = [];
        
        var mapOptions = {
            center: new google.maps.LatLng(47.5421887, 21.6395724),
            zoom: 14,
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            scrollwheel: true
        };
        
        function initMap() {
            if (scope.map === void 0) {
                scope.map = new google.maps.Map(element[0], mapOptions);
            }
            
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    var pos = {
                        lat: position.coords.latitude,
                        lng: position.coords.longitude
                    };
                    
                    scope.map.setCenter(pos);
                }, function() {
                    handleLocationError(true);
                });
            } else {
                // Browser doesn't support Geolocation
                handleLocationError(false);
            }
            
        }
        
        
        var handleLocationError = function(browserHasGeolocation) {
            console.log(browserHasGeolocation ?
                'Error: The Geolocation service failed.' :
                'Error: Your browser doesn\'t support geolocation.');
            }
            
            scope.setMarker = function(map, position, title, content, pubRating) {
                var marker;
                var markerOptions = {
                    position: position,
                    map: map,
                    title: title,
                    icon: 'https://maps.google.com/mapfiles/ms/icons/green-dot.png'
                };
                
                marker = new google.maps.Marker(markerOptions);
                scope.markers.push(marker); // add marker to array
                
                google.maps.event.addListener(marker, 'click', function () {
                    
                    // close window if not undefined
                    if (scope.infoWindow !== void 0) {
                        scope.infoWindow.close();
                    }
                    // create new window
                    var infoWindowOptions = {
                        content: '<h5>' + title + '<span class="map-rating float-right">' + Math.round(pubRating*100)/100 +  '</span></h5>' +
                                 '<br>' + content
                    };
                    scope.infoWindow = new google.maps.InfoWindow(infoWindowOptions);
                    scope.infoWindow.open(map, marker);
                });

            }
            
            scope.setMapCenter = function(clickOff, latitude, longitude){ 
                if (navigator.geolocation && clickOff) {
                    navigator.geolocation.getCurrentPosition(function(position) {
                        var pos = {
                            lat: position.coords.latitude,
                            lng: position.coords.longitude
                        };
                        
                        scope.map.setCenter(pos);
                    }, function() {
                        handleLocationError(true);
                    });
                } else {
                    scope.map.setCenter({lat: latitude, lng: longitude});
                }
                
            }
            
            initMap();            
        }
        
        return {
            restrict: 'E',
            template: '<div id="google-map"></div>',
            replace: true,
            link: link
        };
        
    })
    
    .service('pubService', function($http){
        this.getPubs = function(){
            return $http.get('http://rftpubnfun.azurewebsites.net/PubnFunCore.svc/GetAllPub');
        };
        
        this.getOpinionsByPubID = function(pubID){
            return $http.get('http://rftpubnfun.azurewebsites.net/PubnFunCore.svc/GetAllOpinionAboutPubByID/' + pubID);
        };
    })
    
    .service('reviewService', function($http){
        this.postReview = function(opinionToPost){
            return $http.post('http://rftpubnfun.azurewebsites.net/PubnFunCore.svc/PostOpinion', opinionToPost);
        };
    })
    
    .controller("ReviewController", ['$scope', 'reviewService', function($scope, reviewService){
        
        this.review = {};
        
        this.addReview = function(opinionsArrayByPubID, pubID, apiMe){
            
            if(apiMe && (apiMe.name && apiMe.id)){
                this.review.pubID = pubID;
                this.review.customerName = 'Márió Bersenszki';
                this.review.customerId = 69;
                
                opinionsArrayByPubID.push(this.review);
                
                reviewService.postReview(this.review).then(function(){
                    var newOverallRating = 0;
                    for(var i = 0; i < opinionsArrayByPubID.length; i++){
                        newOverallRating += opinionsArrayByPubID[i].rating;
                    }
                    $scope.pubMap[pubID].customerOverallRatings = newOverallRating / opinionsArrayByPubID.length;
                    console.log($scope.pubMap[pubID].customerOverallRatings);
                    
                }, function(){
                    opinionsArrayByPubID.pop();
                    window.alert('Sajnos nem sikerült a komment elküldése!');
                    console.log("The review wasn't posted"); 
                });
                this.review = {};
            }else{
                window.alert('Nem kommentelhetsz amíg nem lépsz be Facebookkal!');
            }
        };
        
    }])
    
    .controller('HomeController', function($scope){
    })
    
    .controller('PubsController', ['$scope','pubService', function($scope, pubService){
        $scope.active = 0;
        $scope.pubOpinions = [];
        $scope.pubMap = {}; 
        
        
        $scope.setActiveId = function(idToSet){
            if($scope.active === idToSet && $scope.active !== 0){
                $scope.active = 0;
                $scope.setMapCenter(true);
            } else {
                if ($scope.pubOpinions[idToSet]) {
                    $scope.active = idToSet;
                    $scope.setMapCenter(false, $scope.pubMap[$scope.active].latitude, $scope.pubMap[$scope.active].longitude);
                } else {
                    pubService.getOpinionsByPubID(idToSet).then(function(opinions){
                        $scope.pubOpinions[idToSet] = opinions.data;
                    }).then(function(){
                        $scope.active = idToSet;
                        $scope.setMapCenter(false, $scope.pubMap[$scope.active].latitude, $scope.pubMap[$scope.active].longitude);
                    });
                }
            }
        };
        
        $scope.isActive = function(id){
            return $scope.active === id;
        };
        
        
        pubService.getPubs().then(function(pubs) {
            pubs.data.forEach(function(pub){
                $scope.pubMap[pub.pubID] = pub;
            });

            $scope.initMarkers();
        });
        
        $scope.initMarkers = function(){
            for(var pub in $scope.pubMap){
                var pubPosition = {lat: $scope.pubMap[pub].latitude, lng: $scope.pubMap[pub].longitude};
                    $scope.setMarker($scope.map, pubPosition, $scope.pubMap[pub].name, 
                                     $scope.pubMap[pub].address, $scope.pubMap[pub].customerOverallRatings);
                }
        };

    }]);
    
    