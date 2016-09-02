'use strict';

angular.module('clientApp')
  .factory('User', function ($http, $log, $q, $cookieStore) {
    return {

      get: function(id){
         $log.debug("get user " + id);

         var config = {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer '+decodeURIComponent($cookieStore.get('token'))
         }
        $http({
            method: 'GET',
            url: 'http://localhost:8080/api/users/',
            transformRequest: function(obj) {
              var str = [];
              for(var p in obj)
              str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
              return str.join("&");
            },
            data: id,
            headers: config
          }).then(function successCallback(response) {
            $log.debug(response.data[0]);
            return response.data[0];
            }, function errorCallback(response) {
            $log.debug(response);
         });
      }
   };
  });
