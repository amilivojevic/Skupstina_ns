/**
 * Created by Nina on 17-Jun-17.
 */
(function (angular) {

    'use strict';

    var BASE_URL = '/api/sednice';

    angular
        .module('skupstinaNS')
        .service('AddSednicaService', ['$http', addSednicaService]);

    function addSednicaService($http) {
        return {
            addSednica: addSednica,

        };

        function addSednica(sednica_data) {
            return $http({
                method: 'POST',
                url: BASE_URL + "/addSednica",
                data: sednica_data,
                headers: {"Content-Type": 'application/json'}
            });
        }


    }

}(angular));