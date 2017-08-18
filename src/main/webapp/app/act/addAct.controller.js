/**
 * Created by Nina on 17-Jun-17.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("AddActController", addActController);

    function addActController($http,$scope) {
        var vm = this;
        vm.addAct = addAct;
        vm.dodajDeoNaFront = dodajDeoNaFront;
        vm.dodajClanNaFront = dodajClanNaFront;
        vm.dodajSadrzaj_clan = dodajSadrzaj_clan;
        vm.dodajStav_clan = dodajStav_clan;
        vm.dodajTacku_stav_clan = dodajTacku_stav_clan;
        vm.dodajSadrzaj_stav_clan = dodajSadrzaj_stav_clan;
        vm.dodajPodtacku_tacka_stav_clan = dodajPodtacku_tacka_stav_clan;
        vm.dodajSadrzaj_tacka_stav_clan = dodajSadrzaj_tacka_stav_clan;
        vm.dodajAlineju_podtacku_tacka_stav_clan = dodajAlineju_podtacku_tacka_stav_clan;
        vm.dodajSadrzaj_podtacka_tacka_stav_clan = dodajSadrzaj_podtacka_tacka_stav_clan;



        function addAct($http) {
            vm.new_act = {

                naziv: vm.newAct.naziv,
                drzava: vm.newAct.drzava,
                regija: vm.newAct.regija,
                grad: vm.newAct.grad,
                stanje: vm.newAct.stanje
            }

            console.log("novi akt: " + JSON.stringify(vm.newAct));
            console.log("kreirani clanovi: " + JSON.stringify($scope.clanovi));
            console.log("transformisani clanovi: " + JSON.stringify(transformClanovi($scope)));

            var clanoviREST = {
                "clanovi" : transformClanovi($scope)
            }



            var proba = {
                "clanovi" : [
                    {
                        "atributi" : {
                            "naziv" : "neki naziv",
                            "br" : "123"
                        },
                        "stavovi" : ["nesto1", "nesto2", "nesto3"]
                    },
                    {
                        "atributi" : {
                            "naziv" : "neki naziv 222",
                            "br" : "123 222"
                        },
                        "stavovi" : ["nesto1222", "nesto2222", "nesto3222"]
                    }
                ]
            }



            $http.post('/api/akt/novi', proba)
                .then(function (response) {
                    Console.log("Sve je dobro");
                })
                .catch(function () {
                    Console.log("Neka greska!");
                });

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
                },
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
        //dodajPodtacku_tacka_stav_clan

        function dodajPodtacku_tacka_stav_clan(index_clana,index_stava,index_tacke){
            console.log("usaо u dodajPodtacku_tacka_stav_clan");
            $scope.clanovi[index_clana].stavovi[index_stava].tacke[index_tacke].disableDodajSadrzaj = true;
            $scope.clanovi[index_clana].stavovi[index_stava].tacke[index_tacke].podtacke.push(
                {
                    "atributi" : { "br" : ""},
                    "alineje" : [],
                    "sadrzaj" : []
                },
                {
                    "atributi" : { "br" : ""},
                    "alineje" : [],
                    "sadrzaj" : []
                }
            )
        }

        function dodajSadrzaj_tacka_stav_clan(index_clana,index_stava,index_tacke){
            console.log("usaо u dodajSadrzaj_tacka_stav_clan");

            $scope.clanovi[index_clana].stavovi[index_stava].tacke[index_tacke].disableDodajSadrzaj = true;
            $scope.clanovi[index_clana].stavovi[index_stava].tacke[index_tacke].disableDodajPodtacku = true;
            $scope.clanovi[index_clana].stavovi[index_stava].tacke[index_tacke].sadrzaj.push("");
        }

        function dodajAlineju_podtacku_tacka_stav_clan(index_clana,index_stava,index_tacke,index_podtacke){
            console.log("usaо u dodajAlineju_podtacku_tacka_stav_clan");
            $scope.clanovi[index_clana].stavovi[index_stava].tacke[index_tacke].podtacke[index_podtacke].disableDodajSadrzaj = true;
            $scope.clanovi[index_clana].stavovi[index_stava].tacke[index_tacke].podtacke[index_podtacke].disableDodajAlineju = true;
            $scope.clanovi[index_clana].stavovi[index_stava].tacke[index_tacke].podtacke[index_podtacke].alineje.push(
                {
                    "sadrzaj" : []
                }
            )
        }

        function dodajSadrzaj_podtacka_tacka_stav_clan(index_clana,index_stava,index_tacke,index_podtacke){
            console.log("usaо u dodajSadrzaj_podtacka_tacka_stav_clan");

            $scope.clanovi[index_clana].stavovi[index_stava].tacke[index_tacke].podtacke[index_podtacke].disableDodajSadrzaj = true;
            $scope.clanovi[index_clana].stavovi[index_stava].tacke[index_tacke].podtacke[index_podtacke].disableDodajAlineju = true;
            $scope.clanovi[index_clana].stavovi[index_stava].tacke[index_tacke].podtacke[index_podtacke].sadrzaj.push("");
        }

//*****************************************************************

    }

    function transformClanovi($scope){
        var clanoviNovi = [];
        var clan;
        for(var i=0; i<$scope.clanovi.length; i++){
            clan = {};

            clan.atributi = $scope.clanovi[i].atributi;
            if($scope.clanovi[i].stavovi.length > 0){
                clan.stavovi = [];
                for(var j=0; j<$scope.clanovi[i].stavovi.length; j++) {
                    clan.stavovi.push({});
                    if ($scope.clanovi[i].stavovi[j].tacke.length > 0) {
                        clan.stavovi[j].tacke = [];
                        for(var k=0; k<$scope.clanovi[i].stavovi[j].tacke.length; k++) {
                            clan.stavovi[j].tacke.push({});
                            clan.stavovi[j].tacke[k].atributi = $scope.clanovi[i].stavovi[j].tacke[k].atributi;
                            if ($scope.clanovi[i].stavovi[j].tacke[k].podtacke.length > 0) {
                                clan.stavovi[j].tacke[k].podtacke = [];
                                for(var l=0; l<$scope.clanovi[i].stavovi[j].tacke[k].podtacke.length; l++) {
                                    clan.stavovi[j].tacke[k].podtacke.push({});
                                    clan.stavovi[j].tacke[k].podtacke[l].atributi = $scope.clanovi[i].stavovi[j].tacke[k].podtacke[l].atributi;

                                    if ($scope.clanovi[i].stavovi[j].tacke[k].podtacke[l].alineje.length > 0) {
                                        clan.stavovi[j].tacke[k].podtacke[l].alineje = $scope.clanovi[i].stavovi[j].tacke[k].podtacke[l].alineje;
                                    }

                                    if ($scope.clanovi[i].stavovi[j].tacke[k].podtacke[l].sadrzaj.length > 0) {
                                        clan.stavovi[j].tacke[k].podtacke[l].sadrzaj = $scope.clanovi[i].stavovi[j].tacke[k].podtacke[l].sadrzaj[0];
                                    }
                                }
                            }
                            if ($scope.clanovi[i].stavovi[j].tacke[k].sadrzaj.length > 0) {
                                clan.stavovi[j].tacke[k].sadrzaj = $scope.clanovi[i].stavovi[j].tacke[k].sadrzaj[0];
                            }
                        }
                    }
                    if ($scope.clanovi[i].stavovi[j].sadrzaj.length > 0) {

                        clan.stavovi[j].sadrzaj = $scope.clanovi[i].stavovi[j].sadrzaj[0];
                    }
                }
            }
            if($scope.clanovi[i].sadrzaj.length > 0){
                clan.sadrzaj = $scope.clanovi[i].sadrzaj[0];
            }
            clanoviNovi.push(clan);
        }
        return clanoviNovi;
    }
})();