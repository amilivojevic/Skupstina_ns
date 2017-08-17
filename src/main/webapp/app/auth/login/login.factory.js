
(function(angular) {
    angular.module('skupstinaNS')
        .factory('LoginFactory', function($http) {

            return {
                getLoggedUserData: function(token) {

                    return $http.get('/api/users/data', token)
                        .then(function(loggedUserData) {
                            //console.log("tralala" + JSON.stringify(loggedUserData));
                            return loggedUserData.data;
                        });
                }

            }

        });
})(angular);
