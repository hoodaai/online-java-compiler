'use strict';

angular.module('clientApp')
  .factory('Auth', function Auth($location, $rootScope, $http, User, $cookieStore, $q, $log) {
    var currentUser = {};
    if($cookieStore.get('token')) {
      currentUser = User.get();
    }

    return {

      /**
       * Authenticate user and save token
       *
       * @param  {Object}   user     - login info
       * @param  {Function} callback - optional
       * @return {Promise}
       */
      login: function(user, callback) {
        var cb = callback || angular.noop;
        var deferred = $q.defer();
        var config = {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': 'Basic dW5pY29ybmFwcDpteVNlY3JldE9BdXRoU2VjcmV0'
        }
        $http({
            method: 'POST',
            url: 'http://localhost:8080/oauth/token',
            transformRequest: function(obj) {
              var str = [];
              for(var p in obj)
              str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
              return str.join("&");
            },
            data: user,
            headers: config
          }).success(function(data) {
            $log.debug("login success, user.loginid " + user.username);
            $cookieStore.put('token', data.access_token);
            $log.debug($cookieStore.get('token'));
            currentUser = User.get(user.loginid);
            deferred.resolve(data);
            return cb();
          }).
          error(function(err) {
            this.logout();
            deferred.reject(err);
            return cb(err);
          }.bind(this));

        return deferred.promise;
      },
      /**
       * Delete access token and user info
       *
       * @param  {Function}
       */
      logout: function() {
        $cookieStore.remove('token');
        currentUser = {};
      },

      /**
       * Create a new user
       *
       * @param  {Object}   user - user info
       * @return {Promise}
       */
      createUser: function(user) {
        $log.debug("calling create user" + JSON.stringify(user));
        var config = {
                'Content-Type': 'application/json',
                'Accept': 'text/plain',

        }
        return $http({
            method: 'POST',
            url: 'http://localhost:8080/api/register',
            data: user
            //headers: config
          })
      },

      /**
       * Gets all available info on authenticated user
       *
       * @return {Object} user
       */
      getCurrentUser: function() {
        return currentUser;
      },

      /**
       * Check if a user is logged in
       *
       * @return {Boolean}
       */
      isLoggedIn: function() {
        return currentUser.hasOwnProperty('authorities');
      },

      /**
       * Waits for currentUser to resolve before checking if user is logged in
       */
      isLoggedInAsync: function(cb) {
        if(currentUser.hasOwnProperty('$promise')) {
          currentUser.$promise.then(function() {
            cb(true);
          }).catch(function() {
            cb(false);
          });
        } else
        if(currentUser.hasOwnProperty('authorities')) {
          cb(true);
        } else {
          cb(false);
        }
      },

      /**
       * Get auth token
       */
      getToken: function() {
        return $cookieStore.get('token');
      }
    };
  });
