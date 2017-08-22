
(function () {
    angular.module("skupstinaNS")
        .controller('NavbarPredsednikController', navbarPredsednikController);

    function navbarPredsednikController($scope,$location,$window,LoginFactory) {

        var vm = this;

        vm.loggedIn = false;
        vm.logout = logout;
        vm.getLoggedUserData = getLoggedUserData;
        checkIfLogged();

        function checkIfLogged(){

            if($window.localStorage.getItem("token")){
                vm.loggedIn = true;
                vm.token = $window.localStorage.getItem("token");
            }
            else{
                vm.loggedIn = false;
            }
        }

        $scope.redirect = function(){
            $window.location.href = "http://" + $window.location.host + "/#/login";

        }


        function getLoggedUserData() {
            var promise = LoginFactory.getLoggedUserData(vm.token);
            promise.then(
                function(loggedUser) {
                    console.log("ucitan u funkciji: " + JSON.stringify(loggedUser));
                    $window.localStorage['loggedUser'] = angular.toJson(loggedUser);
                    $scope.loggedUser = loggedUser;

                }
            );
        };


        // method for deleting user data - token
        function logout() {
            console.log("usao u logout");
            $window.localStorage.removeItem("loggedUser");
            $window.localStorage.removeItem("token");
            checkIfLogged();
            $scope.redirect();
            //$location.path('/');
        }
        ;
    }
})();