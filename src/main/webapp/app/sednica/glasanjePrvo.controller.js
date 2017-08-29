/**
 * Created by Nina on 17-Jun-17.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("ActivateSednicaController", activateSednicaController);

    function activateSednicaController($scope,$http, $window,  $stateParams) {
        var vm = this;
        vm.getAktiUPripremi = getAktiUPripremi;
        var id = $stateParams.sednicaID;

        var nazivAkta = $stateParams.naziv;

        getAktiUPripremi(id);

        $scope.redirect = function () {
            $window.location.href = "http://" + $window.location.host + "/#!/predsednik";

        }

        vm.voteFirstTime = function (naziv) {
            vm.voting1 = {

                brojPrisutnih: vm.sednicaData.brojPrisutnih,
                za: vm.sednicaData.za,
                protiv: vm.sednicaData.protiv,
                suzdrzani: vm.sednicaData.suzdrzani


            }

            console.log("salje na backend" + JSON.stringify(vm.voting1) );

            $http.post('/api/sednica/voting/first_voting', vm.voting1
            ).then(function (response) {

                $scope.redirect();

            }, function (response) {
                alert("Registration failed");
            });
        }



            /*      $http.post('/api/sednica/nova', novaSednica)
                      .then(function (response) {
                          console.log("Sve je dobro");
                          $window.location.href = "#!/predsednik";
                      })
                      .catch(function () {
                          console.log("Neka greska!");
                      }); */


            vm.id = vm.sednicaID;
            console.log("redni" + id);

            function getAktiUPripremi(id) {
                $http.get('/api/akt/svi_u_pripremi/' + id)
                    .then(function (akti) {
                        console.log("svi akti u_pripremi za tu sednicu");
                        console.log(id);
                        vm.akti = akti.data;
                        console.log(JSON.stringify(akti.data));
                    }, function (response) {
                        alert(response.data.response);
                    });
            }
        }

})();