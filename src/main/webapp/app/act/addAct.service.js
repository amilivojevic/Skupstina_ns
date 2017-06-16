/**
 * Created by Nina on 17-Jun-17.
 */
(function (angular) {

    'use strict';

    var BASE_URL = '/api/acts';

    angular
        .module('skupstinaNS')
        .service('AddActService', ['$http', addActService]);

    function addActService($http) {
        return {
            addAct: addAct,

        };

        function addAct(act_data) {
            return $http({
                method: 'POST',
                url: BASE_URL + "/addAct",
                data: act_data,
                headers: {"Content-Type": 'application/json'}
            });
        }


    }

}(angular));