/**
 * Created by Nina on 17-Jun-17.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("ActivateSednicaController", activateSednicaController);

    function activateSednicaController($scope,$http, $window,  $stateParams) {
        var vm = this;
        vm.getAktiUPripremi = getAktiUPripremi;
        vm.id = $stateParams.sednicaID;
        console.log("Dosao id sednice u prvo glasanje" + vm.id);

        var nazivAkta = $stateParams.naziv;

        getAktiUPripremi(vm.id);

        $scope.redirect = function () {
            $window.location.href = "http://" + $window.location.host + "/#!/predsednik";

        };



        vm.voteFirstTime = function (a) {
            vm.voting1 = {
                sednicaID : vm.id,
                aktID : a.id,
                brojPrisutnih: parseInt(vm.sednicaData.brojPrisutnih),
                za: parseInt(a.za),
                protiv: parseInt(a.protiv),
                suzdrzani: parseInt(a.suzdrzani)

            };
            console.log("sednicaID " + vm.id);
            console.log("sednicaID " + vm.voting1.za);

            console.log("salje na backend" + JSON.stringify(vm.voting1) );

            $http.post('/api/sednica/voting/first_voting', vm.voting1
            ).then(function (response) {

           //     $scope.redirect();

            }, function (response) {
                alert("Registration failed");
            });
        };



            /*      $http.post('/api/sednica/nova', novaSednica)
                      .then(function (response) {
                          console.log("Sve je dobro");
                          $window.location.href = "#!/predsednik";
                      })
                      .catch(function () {
                          console.log("Neka greska!");
                      }); */


            console.log("redni" + vm.id);

            function getAktiUPripremi(id) {
                $http.get('/api/akt/svi_u_proceduri/' + vm.id)
                    .then(function (akti) {
                        console.log("svi akti u_pripremi za tu sednicu");
                        console.log(id);
                        vm.akti = akti.data;
                        console.log(JSON.stringify(akti.data));
                    }, function (response) {
                        alert(response.data.response);
                    });
            }

        function goToSecondVoting() {
            $state.go('secondVoting', {sednicaID:vm.id}, {brojPrisutnih:parseInt(vm.sednicaData.brojPrisutnih)});
        }
        }

})();