(function() {
    angular.module("skupstinaNS")
        .controller("Pera", pera);

    function pera($scope, $http, $window) {
        var vm = this;
        vm.dodajPeru = dodajPeru;
        vm.citajPeru = citajPeru;

        console.log("usao u peru");

        function dodajPeru() {
            console.log("stisnuto dodajPeru");
            var pera = {
                "ime" : "Pera",
                "peric" : "Peric"
            }

            $http.post('/api/pera/dodaj', pera)
                .then(function (response) {
                    console.log("Sve je dobro: dodaj");
                })
                .catch(function () {
                    console.log("Neka greska!");
                });
        }

        function citajPeru() {
            console.log("stisnuto citajPeru");

            $http.get('/api/pera/citaj')
                .then(function (response) {
                    console.log("Sve je dobro: citaj");
                })
                .catch(function () {
                    console.log("Neka greska: citaj!");
                });
        }
    }
})();