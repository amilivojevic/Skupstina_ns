
(function() {
    angular.module("skupstinaNS").controller("PredsednikController", predsednikController);

    function predsednikController($http, $window, $scope, LoginFactory, $state) {
        var vm = this;
        vm.getAllSednice = getAllSednice;


        vm.userData = angular.fromJson($window.localStorage['loggedUser']);
        console.log("vm.userData = " + JSON.stringify(vm.userData));

        getAllSednice();

        vm.redirect = function(){
            $state.go('izmeniProfilPredsednik');
       //     $window.location.href = "http://" + $window.location.host + "/#!/izmeniProfilPredsednik";

        }

        function redirect2(){
            $state.go('predsednik');
        //    $window.location.href = "http://" + $window.location.host + "/#!/predsednik";
        }

        vm.detalji = function(){
            $window.location.href = "http://" + $window.location.host + "/#!/izmeniProfilPredsednik";

        }

        vm.aktiviraj = function(redniBroj){
            //$window.location.href = "http://" + $window.location.host + "/#!/activateSednica";
            $http.post('/api/sednica/aktiviraj/'+redniBroj).then(function (response) {

                console.log("Sa prof predsednika salje id sednice prilikom aktiviranja: !" + redniBroj);
                $state.go('activateSednica', {sednicaID:redniBroj});
            })
                .catch(function () {
                    console.log("Neka greska!");
                });
        }

        vm.korisnickoIme = vm.userData.korisnickoIme;
        console.log("korisnicko ime" + vm.korisnickoIme);
        function getAllSednice() {
            $http.get('/api/sednica/sve_od_usera')
                .then(function (sednice) {
                    vm.sednice = sednice.data;
                    console.log("lista sednica " + JSON.stringify(sednice.data));
                }, function (response) {
                    alert(response.data.response);
                });
        }

        vm.modify = function () {
            vm.changed_user = {

                tip : "PREDSEDNIK",
                korisnickoIme: vm.userData.korisnickoIme,
                lozinka: vm.userData.lozinka,
                ime: vm.userData.ime,
                prezime: vm.userData.prezime

            }

            $http.post('/api/users/predsednik/modify', vm.changed_user).then(function (response) {

                //dovlace se podaci ulogovanog iz baze
                var promise = LoginFactory.getLoggedUserData(vm.token);
                promise.then(
                    function(loggedUser) {
                        console.log("ucitan u funkciji: " + JSON.stringify(loggedUser));
                        $window.localStorage['loggedUser'] = angular.toJson(loggedUser);
                        $scope.userData = loggedUser;
                        redirect2();
                    }
                );


            },function(response){
                alert(response.data.response);
            });
        }


    }
})();