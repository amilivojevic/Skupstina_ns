/**
 * Created by Korisnik on 6/2/2017.
 */
(function() {
    angular.module("skupstinaNS").controller("LoginController", loginController);

    function loginController($scope,$http,$window,LoginFactory) {
        var vm = this;

        vm.loggedIn = false;

        vm.login = login;
        vm.logout = logout;
        vm.getLoggedUserData = getLoggedUserData;


        checkIfLogged();

        function checkIfLogged(){

            if($window.localStorage.getItem("token")){
                console.log("Logged");
                vm.loggedIn = true;
                vm.token = $window.localStorage.getItem("token");

                //getLoggedUserData();


                var promise = LoginFactory.getLoggedUserData(vm.token);
                promise.then(
                    function(loggedUser) {

                        $window.localStorage['loggedUser'] = angular.toJson(loggedUser);
                        console.log("ucitan u funkciji window.localstorage: " + JSON.stringify($window.localStorage['loggedUser']));
                        $scope.loggedUser = loggedUser;
                        var user = angular.fromJson($window.localStorage['loggedUser']);

                        switch (user.role){
                            case "PREDSEDNIK" :
                                $window.location = "#!/predsednik"; break;
                            case "POSLANIK" :
                                $window.location = "#!/poslanik"; break;
                        }
                    }
                );
            }
            else{
                vm.loggedIn = false;
            }
            console.log("loggedin = " + vm.loggedIn);
        }


  /*      function login() {
            $http.post("/users/login", {"username" : vm.username, "password" : vm.password})
                .then(function(response) {

                    sessionStorage.user = response.data.user;

                });
        };*/

        //login method, takes form data (username and password) and calls login method from parent Controller
        function login() {
            console.log(vm.username+" and "+vm.password);
            var userData =  { "username": vm.username, "password": vm.password };

            $http.post('/api/users/login', userData)
                .then(function(token) {

                    var t = token.data.response.split(" ")[0];
                    var role = token.data.response.split(" ")[1];

                    $window.localStorage.setItem("token",t);
                    console.log("token = " + $window.localStorage.getItem("token"));
                    console.log("role = " + role);


                    checkIfLogged();


                }, function(response) {
                    alert(response.data.response);
                    console.log("Wrong username and password combination");
                });
        }

        function getLoggedUserData() {
            var promise = LoginFactory.getLoggedUserData(vm.token);
            promise.then(
                function(loggedUser) {

                    $window.localStorage['loggedUser'] = angular.toJson(loggedUser);
                    console.log("ucitan u funkciji window.localstorage: " + JSON.stringify($window.localStorage['loggedUser']));
                    $scope.loggedUser = loggedUser;
                    $scope.approved = loggedUser.approved;
                    return loggedUser;

                }
            );
        };


    }
})();