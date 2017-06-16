/**
 * Created by Nina on 17-Jun-17.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("AddActController", addActController);

    function addActController(AddActService) {
        var vm = this;
        vm.addAct = addAct;

        function addAct() {
            vm.new_act = {

                naziv: vm.newAct.naziv,
                drzava: vm.newAct.drzava,
                regija: vm.newAct.regija,
                grad: vm.newAct.grad,
                stanje: vm.newAct.stanje
            }

            console.log("novi akt: " + JSON.stringify(vm.newAct))


            AddActService.addAct(vm.new_act).then(function (response) {
                console.log("valjda je kreiran: " + response.data.response)
            }, function (response) {
                alert(response.data);
            });

        }

    }
})();