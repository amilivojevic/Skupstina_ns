/**
 * Created by Nina on 17-Jun-17.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("ActivateSednicaController", activateSednicaController);

    function activateSednicaController($scope,$http, LoginFactory, $window) {
        var vm = this;


        $scope.redirect = function(){
            $window.location.href = "http://" + $window.location.host + "/#!/predsednik";

        }

        $http.get('/api/akt/svi')
            .then(function(akti) {
                vm.akti = akti.data;
                console.log(JSON.stringify(akti.data));
            }, function(response) {
                alert(response.data.response);
            });
    }
})();