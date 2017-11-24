var app = angular.module('pubNFun', ['easyFacebook','ngRoute'])
.config(function($routeProvider, $locationProvider){
      $routeProvider
      .when("#/home", {
        templateUrl: "templates/home.html",
        controller: "homeController"
      })
      .when("#/pubs", {
        templateUrl: "templates/pubs.html",
        controller: "pubsController"
      })
})

.controller("homeController", function($scope){
 })
.controller("pubsController", function($scope){
});