(function() {
    angular.module("skupstinaNS")
        .controller("AddAmandmanController", addAmandmanController);

    function addAmandmanController($scope,$http,$window) {
        var vm = this;
        vm.addAmandman = addAmandman;
        vm.dodajStavku = dodajStavku;
        vm.novi = novi;
        vm.brisanje = brisanje;
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
            "za" : 0,
            "protiv" : 0,
            "suzdrzani" : 0,
            "obrazlozenje" : "",
            "stavke" : {
                "stavkaAmandmana" : []
            }
        };



        function novi(){
            alert("JOS NE RADI DODAVANJE NICEGA!");
        }

        function brisanje(id){
            vm.novaStavka = {
                "tipIzmene" : "BRISANJE",
                "idPodakta" : id
            }

            vm.amandman.stavke.stavkaAmandmana.push(vm.novaStavka);

            console.log("NAPRAVLJENA NOVA STAVKA AMANDMANA: ",vm.novaStavka);
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

            vm.amandman.aktID = JSON.parse(vm.aktSelected).id;
            console.log("NOVI AMANDMAN: ",vm.amandman);
            $http.post('/api/amandman/novi', vm.amandman)
                .then(function (response) {
                    console.log("Amandman uspesno dodat");
                })
                .catch(function () {
                    console.log("Neka greska kod kreiranja amandmana!");
                });
        }

        function dodajStavku(){

            if(vm.akt === null){
                alert("Morate odabrati akt!")
            }
            else{
                vm.novaStavka = {
                    "tipIzmene" : "",
                    "mestoIzmene" : "", //OVO TREBA SAMO ZA DODAVANJE!!!
                    "idPodakta" : ""
                };
                vm.klik = true;
                vm.akt = JSON.parse(vm.aktSelected);
                console.log("izabran akt: " + JSON.stringify(vm.akt));
            }


        }


    }
})();