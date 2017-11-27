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
})


.controller("homeController", function($scope){
 })
.controller("pubsController", function($scope){
});