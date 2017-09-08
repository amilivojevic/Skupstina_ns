
(function() {
    angular.module("skupstinaNS").controller("ExportRDFController", exportRDFController);

    function exportRDFController($http, $window, $scope, LoginFactory, $state, $sce) {
        var vm = this;
        vm.getAllAkti = getAllAkti;
        getAllAkti();

        $scope.redirect = function(){
            $state.go('izmeniProfil');
            //         $window.location.href = "http://" + $window.location.host + "/#!/izmeniProfil";
        }


        // svi akti od tog korisnika i u proceduri
        function getAllAkti() {
            $http.get('/api/akt/svi')
                .then(function (akti) {
                    vm.akti = akti.data;
                }, function (response) {
                    alert(response.data.response);
                });
        }

        vm.stampajRDF = function(id){
            alert("okinulo se stampanje");
            $http.get('/api/akt/export/rdf/'+ id).then(function (response) {
                console.log("stampanje rdf");
            })
                .catch(function () {
                    console.log("Neka greska!");
                });
        }

    }
})();