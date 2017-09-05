(function() {
    angular.module("skupstinaNS")
        .controller("AddAmandmanController", addAmandmanController);

    function addAmandmanController($scope,$http,$window) {
        var vm = this;
        vm.addAmandman = addAmandman;
        vm.dodajStavku = dodajStavku;
        vm.novi = novi;
        vm.selektovan = false;
        vm.aktSelected = null;

        vm.amandman = {
            "pravniOsnov" : "",
            "datumObjave" : Date(),
            "broj" : "",
            "naziv" : "",
            "kreirao" : JSON.parse($window.localStorage['loggedUser']).korisnickoIme,
            "sednicaID" : "",
            "aktID" : "",
            "id" : "",
            "stanje" : "ZAKAZAN",
            "obrazlozenje" : "",
            "stavke" : []
        };



        function novi(){
            alert("JOS NE RADI DODAVANJE NICEGA!");
        }

        $http.get('/api/akt/svi')
            .then(function(akti) {
                vm.akti = akti.data;
                vm.akti = JSON.parse(JSON.stringify(vm.akti));
                //console.log(JSON.stringify(akti.data));
            }, function(response) {
                alert(response.data.response);
            });

        function addAmandman() {

            var ulogovani = $window.localStorage['loggedUser'];

            vm.amandman.aktID = JSON.parse(vm.aktSelected).id;
            console.log("NOVI AMANDMAN: ",vm.amandman);
        }

        function dodajStavku(){

            if(vm.akt === null){
                alert("Morate odabrati akt!")
            }
            else{
                vm.novaStavka = {};
                vm.klik = true;
                vm.akt = JSON.parse(vm.aktSelected);
                console.log("izabran akt: " + JSON.stringify(vm.akt));
            }


        }


    }
})();