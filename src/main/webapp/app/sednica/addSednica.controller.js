/**
 * Created by Nina on 17-Jun-17.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("AddSednicaController", addSednicaController);

function addSednicaController($scope,$http, LoginFactory, $window) {
    var vm = this;
        vm.addSednica = addSednica;


        $scope.redirect = function(){
            $window.location.href = "http://" + $window.location.host + "/#!/predsednik";

         }

        vm.change = function () {
            $scope.redirect();
        }

        function addSednica() {


       //     console.log("kreirani clanovi: " + JSON.stringify($scope.clanovi));
      //      console.log("transformisani clanovi: " + JSON.stringify(transformClanovi($scope)));

            var logedUser = $window.localStorage['loggedUser'];

            console.log("kreirani clanovi: " + JSON.stringify(logedUser));

            vm.kreirao = angular.fromJson($window.localStorage['loggedUser']);;

            var novaSednica = {
                "akt": [],
                "datum": vm.datum,
                "redniBroj": vm.redniBroj,
                "naziv": vm.naziv,
                "brojPrisutnih": 0,
                "korisnickoIme": vm.kreirao.korisnickoIme,
                "stanje": "ZAKAZANA"

            }

            $http.post('/api/sednica/nova', novaSednica)
                .then(function (response) {
                    console.log("Sve je dobro");
                    $scope.redirect();
                })
                .catch(function () {
                    console.log("Neka greska!");
                });

        }
    }
})();