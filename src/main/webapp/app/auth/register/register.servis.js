/**
 * Created by Korisnik on 6/15/2017.
 */
(function (angular) {

    'use strict';

    var BASE_URL = '/api/users';

    angular
        .module('skupstinaNS')
        .service('RegisterService', ['$http', registerService]);

    function registerService($http) {
        return {
            register: register,

        };

        function register(user_data) {
            return $http({
                method: 'POST',
                url: BASE_URL + "/register",
                data: user_data,
                headers: {"Content-Type": 'application/json'}
            });
        }


    }

}(angular));