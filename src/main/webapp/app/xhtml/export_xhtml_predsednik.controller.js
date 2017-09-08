
(function() {
    angular.module("skupstinaNS").controller("ExportXHTMLPredsednikController", exportXHTMLPredsednikController);

    function exportXHTMLPredsednikController($http, $window, $scope, LoginFactory, $state, $sce) {
        var vm = this;
        vm.getAllAkti = getAllAkti;
        getAllAkti();

        $scope.redirect = function(){
            $state.go('izmeniProfil');
            //         $window.location.href = "http://" + $window.location.host + "/#!/izmeniProfil";
        }


        // svi akti od tog korisnika i u proceduri
        function getAllAkti() {
            $http.get('/api/akt/svi_u_celosti')
                .then(function (akti) {
                    vm.akti = akti.data;
                }, function (response) {
                    alert(response.data.response);
                });
        }

        vm.stampajXHTML = function(id){
            alert("okinulo se stampanje");
            $http.get('/api/akt/download-xhtml/'+ id).then(function (response) {
                vm.userHtml = $sce.trustAsHtml(response.data);;
                console.log("Id akta za stampanje: " + id);
                $state.go('stampajPDF');
            })
                .catch(function () {
                    console.log("Neka greska!");
                });
        }

    }
})();