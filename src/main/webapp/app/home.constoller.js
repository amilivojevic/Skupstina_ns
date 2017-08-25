(function() {
    angular.module("skupstinaNS")
        .controller("HomeController", homeController);


    function homeController($scope,$http,$window) {
        var vm = this;

        $http.get('/api/akt/svi')
            .then(function(akti) {
                vm.akti = akti.data;
                console.log(JSON.stringify(akti.data));
            }, function(response) {
                alert(response.data.response);
            });
    }
})();