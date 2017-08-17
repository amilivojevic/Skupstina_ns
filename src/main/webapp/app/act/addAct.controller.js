/**
 * Created by Nina on 17-Jun-17.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("AddActController", addActController);

    function addActController(AddActService, $scope) {
        var vm = this;
        vm.addAct = addAct;
        vm.dodajDeoNaFront = dodajDeoNaFront;
        vm.dodajClanNaFront = dodajClanNaFront;
        vm.dodajSadrzaj_clan = dodajSadrzaj_clan;
        vm.dodajStav_clan = dodajStav_clan;
        vm.dodajTacku_stav_clan = dodajTacku_stav_clan;
        vm.dodajSadrzaj_stav_clan = dodajSadrzaj_stav_clan;



        function addAct() {
            vm.new_act = {

                naziv: vm.newAct.naziv,
                drzava: vm.newAct.drzava,
                regija: vm.newAct.regija,
                grad: vm.newAct.grad,
                stanje: vm.newAct.stanje
            }

            console.log("novi akt: " + JSON.stringify(vm.newAct))


           /* AddActService.addAct(vm.new_act).then(function (response) {
                console.log("valjda je kreiran: " + response.data.response)
            }, function (response) {
                alert(response.data);
            });*/

        }




        $scope.delovi=[];
        $scope.clanovi=[];
        $scope.sadrzaj = [];
        vm.disableDodajDeo = false;
        vm.disableDodajClan = false;

//*****************************************************************

        function dodajDeoNaFront(){
            console.log("usaо u dodajDeoNaFront");
            $scope.naslov1 = "Dodavanje Dela";
            vm.disableDodajClan = true;

        }

        function dodajClanNaFront(){
            console.log("usaо u dodajClanNaFront");
            $scope.naslov1 = "Dodavanje Clana";
            vm.disableDodajDeo = true;
            $scope.clanovi.push(
                {
                    "atributi":{
                        "naziv" : '',
                        "br" : '',
                    },
                    "disableDodajStav" : false,
                    "disableDodajSadrzaj" : false,
                    "stavovi" : [],
                    "sadrzaj" : []
                }
            );
        }

        function dodajSadrzaj_clan(index){
            console.log("usaо u dodajSadrzajNaFront");

            $scope.clanovi[index].disableDodajStav = true;
            $scope.clanovi[index].disableDodajSadrzaj = true;
            $scope.clanovi[index].sadrzaj.push("");
        }

        function dodajStav_clan(index){
            console.log("usaо u dodajStavNaFront");
            $scope.clanovi[index].disableDodajSadrzaj = true;
            $scope.clanovi[index].stavovi.push(
                {
                    "disableDodajSadrzaj": false,
                    "disableDodajTacku":false,
                    "tacke" : [],
                    "sadrzaj" : []
                }
            )
        }


        function dodajTacku_stav_clan(index_clana,index_stava){
            console.log("usaо u dodajTacku_stav_clan");
            $scope.clanovi[index_clana].stavovi[index_stava].disableDodajSadrzaj = true;
            $scope.clanovi[index_clana].stavovi[index_stava].tacke.push(
                {
                    "atributi" : { "br" : ""},
                    "podtacke" : [],
                    "sadrzaj" : []
                }
            )
        }

        function dodajSadrzaj_stav_clan(index_clana,index_stava){
            console.log("usaо u dodajSadrzaj_stav_clan");

            $scope.clanovi[index_clana].stavovi[index_stava].disableDodajSadrzaj = true;
            $scope.clanovi[index_clana].stavovi[index_stava].disableDodajTacku = true;
            $scope.clanovi[index_clana].stavovi[index_stava].sadrzaj.push("");
        }

//*****************************************************************

    }
})();