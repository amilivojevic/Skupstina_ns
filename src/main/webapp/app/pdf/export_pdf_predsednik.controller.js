
(function() {
    angular.module("skupstinaNS").controller("ExportPDFPredsednikController", exportPDFPredsednikController);

    function exportPDFPredsednikController($http, $window, $scope, LoginFactory, $state) {
        var vm = this;
        vm.getAllAkti = getAllAkti;

        getAllAkti();


        // svi akti od tog korisnika i u proceduri
        function getAllAkti() {
            $http.get('/api/akt/svi_u_celosti')
                .then(function (akti) {
                    vm.akti = akti.data;
                }, function (response) {
                    alert(response.data.response);
                });
        }
    }
})();