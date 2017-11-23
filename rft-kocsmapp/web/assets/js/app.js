var app = angular.module('pubNFun', ['ezfb', 'ngRoute'])

.config(function (ezfbProvider, $routeProvider, $locationProvider) {
    ezfbProvider.setInitParams({
        appId: '292632967892473'
    });

    $routeProvider
      .when("/home", {
        templateUrl: "Templates/home.html",
        controller: "homeController"
      })
      .when("/pubs", {
        templateUrl: "Templates/pubs.html",
        controller: "pubsController"
      })
})
.controller('MainCtrl', function($scope, ezfb, $window, $location) {
    
    updateLoginStatus(updateApiMe);
    
    $scope.login = function () {
        /**
        * Calling FB.login with required permissions specified
        * https://developers.facebook.com/docs/reference/javascript/FB.login/v2.0
        */
        ezfb.login(function (res) {
            /**
            * no manual $scope.$apply, I got that handled
            */
            if (res.authResponse) {
                updateLoginStatus(updateApiMe);
            }
        }, {scope: 'email,user_likes'});
    }

    var autoToJSON  = ['loginStatus', 'apiMe']; 
    angular.forEach(autoToJSON, function (varName) {
      $scope.$watch(varName, function (val) {
        $scope[varName + 'JSON'] = JSON.stringify(val, null, 2);
      }, true);
    });
    
    /**
     * Update loginStatus result
     */
    function updateLoginStatus (more) {
      ezfb.getLoginStatus(function (res) {
        $scope.loginStatus = res;
  
        (more || angular.noop)();
      });
    }
  
    /**
     * Update api('/me') result
     */
    function updateApiMe () {
      ezfb.api('/me', function (res) {
        $scope.apiMe = res;
      });
    }
  

})
.controller("homeController", function($scope){

})
.controller("pubsController", function($scope){

});