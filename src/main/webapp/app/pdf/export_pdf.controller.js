
(function() {
    angular.module("skupstinaNS").controller("ExportPDFController", exportPDFController);

    function exportPDFController($http, $window, $scope, LoginFactory, $state) {
        var vm = this;
        vm.getAllAkti = getAllAkti;
        getAllAkti();

        $scope.redirect = function(){
            $state.go('izmeniProfil');
            //         $window.location.href = "http://" + $window.location.host + "/#!/izmeniProfil";
        }


        // svi akti od tog korisnika i u proceduri
        function getAllAkti() {
            $http.get('/api/akt/svi_u_celosti')
                .then(function (akti) {
                    vm.akti = akti.data;
                }, function (response) {
                    alert(response.data.response);
                });
        }

        vm.stampajPDF = function(id){
            alert("okinulo se stampanje");
            $http.get('/api/akt/download-pdf/'+ id).then(function (response) {
                return response.data.withHttpConfig({ responseType: 'arraybuffer'});
                console.log("Id akta za stampanje: " + id);
                $state.go('stampajPDF');
            })
                .catch(function () {
                    console.log("Neka greska!");
                });
        }

    }
})();