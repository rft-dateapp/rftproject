var app = angular.module('pubNFun', ['easyFacebook','ngRoute'])
.config(function($routeProvider, $locationProvider){
      $routeProvider
      .when("/home", {
        templateUrl: "assets/templates/home.html",
        controller: "homeController"
      })
      .when("/pubs", {
        templateUrl: "assets/templates/pubs.html",
        controller: "pubsController"
      })
})

.controller("homeController", function($scope){
 })
.controller("pubsController", function($scope){
});