/**
 * Created by Nina on 17-Jun-17.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("AddSednicaController", addSednicaController);

    function addSednicaController($scope,$http) {
        var vm = this;
        vm.addSednica = addSednica;

        function addSednica() {


       //     console.log("kreirani clanovi: " + JSON.stringify($scope.clanovi));
      //      console.log("transformisani clanovi: " + JSON.stringify(transformClanovi($scope)));

            var novaSednica = {
                "datum": vm.datum,
                "redniBroj": vm.redniBroj,
                "naziv": vm.naziv,
                "stanje": vm.stanje
            }

            $http.post('/api/sednica/novi', novaSednica)
                .then(function (response) {
                    console.log("Sve je dobro");
                })
                .catch(function () {
                    console.log("Neka greska!");
                });

        }
    }
})();