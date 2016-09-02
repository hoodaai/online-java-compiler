'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  .controller('AuthCtrl', function ($scope, Auth, $location, $log, $http) {


$scope.register = function(form) {
      $scope.submitted = true;
      if(form.$valid) {
        Auth.createUser({
           login: $scope.user.loginid,
           password: $scope.user.password,
           email: $scope.user.email,
           authorities: [ "ROLE_USER"],
           activated: "true",
           langKey: "en"
        })
        .then( function() {
          // Account created, redirect to home
          $location.path('/login');
        })
        .catch( function(err) {
          err = err.data;
          $scope.errors = err;
          $log.debug(err);
        });
      }
    };


$scope.login = function(form) {
  $log.debug("login called");
   if(form.$valid) {
    var user = {
      username: $scope.user.loginid,
      password: $scope.user.password,
      scope: 'read write',
      client_id: 'unicornapp',
      client_secret: 'mySecretOAuthSecret',
      grant_type: 'password'
    }
     Auth.login(user).then( function() {
         // Account created, redirect to home
         $log.debug("login success");
         $location.path('/');
      }).catch( function(err) {
         //err = err.data;
         $scope.errors = err;
         $log.debug(err);
      });
   }
 };

});
