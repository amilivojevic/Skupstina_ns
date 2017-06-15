/**
 * Created by Korisnik on 6/2/2017.
 */
(function() {
    angular.module("skupstinaNS").controller("LoginController", loginController);

    function loginController($http) {
        var vm = this;

        vm.login = login;

        function login() {
            $http.post("/users/login", {"username" : vm.username, "password" : vm.password})
                .then(function(response) {

                    sessionStorage.user = response.data.user;

                });
        };


    }
})();