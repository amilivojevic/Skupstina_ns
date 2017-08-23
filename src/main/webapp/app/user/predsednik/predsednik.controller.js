
(function() {
    angular.module("skupstinaNS").controller("PredsednikController", predsednikController);

    function predsednikController($http, $window, $scope, LoginFactory) {
        var vm = this;
        vm.userData = angular.fromJson($window.localStorage['loggedUser']);
        console.log("vm.userData = " + JSON.stringify(vm.userData));

        $scope.redirect = function(){
            $window.location.href = "http://" + $window.location.host + "/#!/izmeniProfilPredsednik";

        }

        vm.change = function () {
            $scope.redirect();
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