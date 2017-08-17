/**
 * Created by Sandra on 6/14/2017.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("RegisterController", registerController);

    function registerController(RegisterService) {
        var vm = this;
        vm.register = register;
        vm.types = ['Poslanik', 'Predsednik'];

        function register(){
            vm.new_user = {
                type : vm.newUser.userType,
                username: vm.newUser.username,
                password: vm.newUser.password,
                name: vm.newUser.name,
                surname: vm.newUser.surname
            }

            console.log("novi korisnik: " + JSON.stringify(vm.newUser) )
            checkRepeatedPassword(vm.newUser.password, vm.newUser.repeatpass)

            RegisterService.register(vm.new_user).then(function (response) {
              console.log("valjda je kreiran: " + response.data.response)
            },function(response){
                alert(response.data);
            });

        }

        function checkRepeatedPassword(pass, repeatPass){
            if(vm.newUser.password != vm.newUser.repeatpass){
                alert("Passwords must match!");
                return;
            }

        }

    }
})();