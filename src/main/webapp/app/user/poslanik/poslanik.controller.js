
(function() {
    angular.module("skupstinaNS").controller("PoslanikController", poslanikController);

    function poslanikController($http, $window, $scope, LoginFactory) {
        var vm = this;
        vm.getAllAkti = getAllAkti;
        vm.getSednice = getSednice;

        getAllAkti();
        getSednice();

        vm.userData = angular.fromJson($window.localStorage['loggedUser']);
        console.log("vm.userData = " + JSON.stringify(vm.userData));

        $scope.redirect = function(){
            $window.location.href = "http://" + $window.location.host + "/#!/izmeniProfil";

        }
        
        vm.change = function () {
            $scope.redirect();
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
                    //      console.log(JSON.stringify(sednice.data));
                }, function (response) {
                    alert(response.data.response);
                });
        }

        // svi zakazane sednice
        function getSednice() {
            $http.get('/api/sednica/sve')
                .then(function (sednice) {
                    vm.sednice = sednice.data;
                    //      console.log(JSON.stringify(sednice.data));
                }, function (response) {
                    alert(response.data.response);
                });
        }
        
        vm.predlozi = function (id) {

            vm.predlozen = {
                id: vm.noviPredlog.id,
                datum: vm.noviPredlog.datum

            }
            vm.predlozen.id = id;

            console.log("id akta" + vm.predlozen.id);
         //   console.log("datum sednice" + vm.datum);
            console.log("novi predlog: " + JSON.stringify(vm.noviPredlog));

            $http.post('/api/sednica/predlozen' + vm.predlozen).then(function (response) {

                $scope.redirect();

            },function(response){
                alert("Registration failed");
            });
        }



    }
})();