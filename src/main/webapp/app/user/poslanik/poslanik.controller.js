
(function() {
    angular.module("skupstinaNS").controller("PoslanikController", poslanikController);

    function poslanikController($http, $window, $scope, LoginFactory, $state) {
        var vm = this;
        vm.getAllAkti = getAllAkti;
        vm.getSednice = getSednice;

        getAllAkti();

        vm.userData = angular.fromJson($window.localStorage['loggedUser']);
        console.log("vm.userData = " + JSON.stringify(vm.userData));

        $scope.redirect = function(){
            $state.go('izmeniProfil');
   //         $window.location.href = "http://" + $window.location.host + "/#!/izmeniProfil";
        }

        vm.change = function () {
            $scope.redirect();
        }

        $scope.redirect2 = function(){
            $state.go('profil');
        //    $window.location.href = "http://" + $window.location.host + "/#!/profil";
        }

        vm.modify = function () {
            vm.changed_user = {

                tip : "POSLANIK",
                korisnickoIme: vm.userData.korisnickoIme,
                lozinka: vm.userData.lozinka,
                ime: vm.userData.ime,
                prezime: vm.userData.prezime

            }

            $http.post('/api/users/poslanik/modify', vm.changed_user).then(function (response) {

                //dovlace se podaci ulogovanog iz baze
                var promise = LoginFactory.getLoggedUserData(vm.token);
                promise.then(
                    function(loggedUser) {
                        console.log("ucitan u funkciji: " + JSON.stringify(loggedUser));
                        $window.localStorage['loggedUser'] = angular.toJson(loggedUser);
                        $scope.userData = loggedUser;
                        $window.location.href = "#!/profil";
                    }
                );


            },function(response){
                alert(response.data.response);
            });


        }

        // svi akti od tog korisnika i u proceduri
        function getAllAkti() {
            $http.get('/api/akt/svi_u_proceduri')
                .then(function (akti) {
                    vm.akti = akti.data;
                    getSednice();
                    //      console.log(JSON.stringify(sednice.data));
                }, function (response) {
                    alert(response.data.response);
                });
        }

        function daliAktPripadaSednici(sednica, aktId){

        }

        function pronadjiSednicu(sednice, aktId) {

            for (var i = 0; i < sednice.length; i++) {
                for (var j = 0; j < sednice[i].akt.length; j++) {
                    if (sednice[i].akt[j].id == aktId) {
                        return sednice[i].redniBroj;
                    }
                }
            }
            return null;
        }



        // svi zakazane sednice
        function getSednice() {
            $http.get('/api/sednica/sve')
                .then(function (sednice) {
                    vm.sednice = sednice.data;

                    for(var i = 0; i < vm.akti.length; i++){
                        // za svaku sednicu proveriti da li je ona nasa sedica?


                        vm.akti[i].izabranaSednica = pronadjiSednicu(vm.sednice, vm.akti[i].id);
                        vm.akti[i].sednice = [];
                        vm.akti[i].sednice=vm.sednice.concat(vm.akti[i].sednice);
                    }
                    //      console.log(JSON.stringify(sednice.data));
                }, function (response) {
                    alert(response.data.response);
                });
        }
        
        vm.predlozi = function (a) {

            vm.predlozen = {
                aktID: a.id,
                sednicaRB: a.izabranaSednica

            }
       //     console.log("id akta "+ id + "rbr sednice "+vm.noviPredlog.sednicaRB);

            console.log("novi predlog: " + JSON.stringify(vm.predlozen));

            $http.post('/api/sednica/predlozi', vm.predlozen).then(function (response) {

                $scope.redirect2();

            },function(response){
                alert("Registration failed");
            });
        }

        vm.otkaziPredlog = function (a) {

            vm.predlozen = {
                aktID: a.id,
                sednicaRB: a.izabranaSednica

            }
            //     console.log("id akta "+ id + "rbr sednice "+vm.noviPredlog.sednicaRB);

            console.log("novi predlog: " + JSON.stringify(vm.predlozen));

            $http.post('/api/sednica/otkazi', vm.predlozen).then(function (response) {

                $scope.redirect2();

            },function(response){
                alert("Registration failed");
            });
        }



    }
})();