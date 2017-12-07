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
            if(apiMe){
                this.review.pubID = pubID;
                this.review.customers = apiMe.name;
                this.review.customerId = apiMe.id;
                reviewService.postReview(this.review).then(function(){
                    opinionsArrayByPubID.push(this.review);
                }, function(){
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
        $scope.pubs = [];
        $scope.pubOpinions = [];
        
        
        $scope.setActiveId = function(idToSet){
            if($scope.active === idToSet){
                $scope.active = 0;
            } else {
                if ($scope.pubOpinions[idToSet]) {
                    $scope.active = idToSet;
                } else {
                    pubService.getOpinionsByPubID(idToSet).then(function(opinions){
                        $scope.pubOpinions[idToSet] = opinions.data;
                    }).then(function(){
                        $scope.active = idToSet;
                    });
                }
            }
            console.log($scope.pubOpinions[$scope.active]);
        };
        
        $scope.isActive = function(id){
            return $scope.active === id;
        };
        
        
        pubService.getPubs().then(function(pubs) {
            $scope.pubs = pubs.data;
        });
        
    }]);
    
    