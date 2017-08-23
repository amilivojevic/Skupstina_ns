(function() {
    angular.module("skupstinaNS")
        .controller("HomeController", homeController);

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