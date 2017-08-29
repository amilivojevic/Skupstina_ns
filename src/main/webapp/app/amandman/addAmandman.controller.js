(function() {
    angular.module("skupstinaNS")
        .controller("AddAmandmanController", addAmandmanController);

    function addAmandmanController($scope,$http,$window) {
        var vm = this;
        vm.addAmandman = addAmandman;
        vm.dodajStavku = dodajStavku;
        vm.akt = {};


        $http.get('/api/akt/svi')
            .then(function(akti) {
                vm.akti = akti.data;
                console.log(JSON.stringify(akti.data));
            }, function(response) {
                alert(response.data.response);
            });

        function addAmandman() {

            var ulogovani = $window.localStorage['loggedUser'];
            //console.log("ulogovani: " + ulogovani.korisnickoIme);
            console.log("izabrani akt: ",vm.akt);
        }

        function dodajStavku(){
            if(vm.akt.id === undefined){
                alert("Morate odabrati akt!")
            }
            var glasanje = {};
            
        }


    }
})();