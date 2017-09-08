(function() {
    angular.module("skupstinaNS")
        .controller("HomeController", homeController);


    function homeController($scope,$http,$window) {
        var vm = this;
        vm.amdSelected = null;
        vm.traziPoNazivu = traziPoNazivu;
        vm.traziPoSadrzaju = traziPoSadrzaju;
        vm.traziPoStanju = traziPoStanju;
        vm.traziPoOblasti = traziPoOblasti;

        vm.naziv = "";

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

        function traziPoNazivu(naziv){
            $http.get('/api/akt/trazi/naziv/'+naziv)
                .then(function(filtrirani) {
                    console.log("pretrazivanje po nazivu...");
                    vm.akti = filtrirani.data;
                    console.log("reyultat: ",filtrirani.data)
                }, function(response) {
                    alert(response.data.response);
                });
        }

        function traziPoSadrzaju(sadrzaj){
            $http.get('/api/akt/trazi/sadrzaj/'+sadrzaj)
                .then(function(akti) {
                    console.log("pretrazivanje po sadrzaju...",akti.data);
                    vm.akti = akti.data;
                }, function(response) {
                    alert(response.data.response);
                });
        }
        function traziPoStanju(sadrzaj){
            $http.get('/api/akt/trazi/sadrzaj/'+sadrzaj)
                .then(function(akti) {
                    console.log("pretrazivanje po sadrzaju...",akti.data);
                    vm.akti = akti.data;
                }, function(response) {
                    alert(response.data.response);
                });
        }
        function traziPoOblasti(sadrzaj){
            $http.get('/api/akt/trazi/sadrzaj/'+sadrzaj)
                .then(function(akti) {
                    console.log("pretrazivanje po sadrzaju...",akti.data);
                    vm.akti = akti.data;
                }, function(response) {
                    alert(response.data.response);
                });
        }
    }


})();