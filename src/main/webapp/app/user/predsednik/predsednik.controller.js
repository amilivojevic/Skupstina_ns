
(function() {
    angular.module("skupstinaNS").controller("PredsednikController", predsednikController);

    function predsednikController($http, $window, $scope, LoginFactory, $state) {
        var vm = this;
        vm.getAllSednice = getAllSednice;

        getAllSednice();
        vm.userData = angular.fromJson($window.localStorage['loggedUser']);
        console.log("vm.userData = " + JSON.stringify(vm.userData));



        vm.redirect = function(){
            $window.location.href = "http://" + $window.location.host + "/#!/izmeniProfilPredsednik";

        }

        vm.detalji = function(){
            $window.location.href = "http://" + $window.location.host + "/#!/izmeniProfilPredsednik";

        }

        vm.aktiviraj = function(redniBroj){
            //$window.location.href = "http://" + $window.location.host + "/#!/activateSednica";
            $state.go('activateSednica', {sednicaID:redniBroj});

        }

        function getAllSednice() {
            $http.get('/api/sednica/sve')
                .then(function (sednice) {
                    vm.sednice = sednice.data;
                    //      console.log(JSON.stringify(sednice.data));
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
                        $window.location.href = "#!/predsednik";
                    }
                );


            },function(response){
                alert(response.data.response);
            });
        }


    }
})();