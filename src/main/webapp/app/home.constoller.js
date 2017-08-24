(function() {
    angular.module("skupstinaNS")
        .controller("HomeController", homeController);


    // hardcode-ovano da se ucita iz baze samo akt sa id-om /akt/akt1.xml!
    function homeController($scope,$http,$window) {
        var vm = this;

        $http.get('/api/akt/jedan')
            .then(function(akti) {
                vm.akti = akti;
                console.log(JSON.stringify(akti));
            }, function(response) {
                alert(response.data.response);

            });
    }
})();