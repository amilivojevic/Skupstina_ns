(function() {
    angular.module("skupstinaNS")
        .controller("HomeController", homeController);


    function homeController($scope,$http,$window) {
        var vm = this;
        vm.amdSelected = null;

        vm.applyAmandam = applyAmandam;

        $http.get('/api/akt/svi')
            .then(function(akti) {
                vm.akti = akti.data;
                //console.log(JSON.stringify(akti.data));
            }, function(response) {
                alert(response.data.response);
            });

        $http.get('/api/amandman/svi')
            .then(function(amd) {
                vm.amandmani = amd.data;
            }, function(response) {
                alert(response.data.response);
            });

        function applyAmandam() {
            console.log("selektovan: ",vm.amdSelected);
            console.log("selektovan id: ",JSON.parse(vm.amdSelected).id);
            $http.get('/api/amandman/primeni/' + JSON.parse(vm.amdSelected).id)
                .then(function() {

                    console.log(JSON.stringify("VALJDA JE DOBRO"));
                }, function(response) {
                    alert(response.data.response);
                });
        }
    }


})();