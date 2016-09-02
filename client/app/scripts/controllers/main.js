'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  .controller('MainCtrl', function ($scope, Auth, $location, $log, $http, FileUploader, $cookieStore) {

    var uploader = $scope.uploader = new FileUploader({
            url: 'http://localhost:8080/api/upload',
            headers: {
                  'Authorization': 'Bearer '+decodeURIComponent($cookieStore.get('token'))
            }
    });

   uploader.onSuccessItem = function(fileItem, response, status, headers) {
      $log.debug('onSuccessItem', fileItem, response, status, headers);
      $scope.output = JSON.stringify(response,null,"    ");
    };

   uploader.onErrorItem = function(fileItem, response, status, headers) {
      $log.debug('onErrorItem', fileItem, response, status, headers);
      $scope.output = JSON.stringify(response,null,"    ");
   };

    $scope.uploadFile = function(files) {
        $log.debug("uploading file" + $scope.zipFile);
        var fd = new FormData();
        //Take the first selected file
        fd.append("file", files[0]);

        $http.post(uploadUrl, fd, {
            withCredentials: true,
            headers: {'Content-Type': undefined },
            transformRequest: angular.identity
        }).success( ).error( );

    };

    $scope.logout = function(){
      $log.debug("logout...");
      Auth.logout();
      $location.path('/login');
    }


  });
