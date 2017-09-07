/**
 * Created by Nina on 17-Jun-17.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("ThirdVotingController", thirdVotingController);

    function thirdVotingController($scope,$http, $window, $state, $stateParams) {
        var vm = this;
        vm.getAktiUNacelu = getAktiUNacelu;
   //     vm.goToThirdVoting = goToThirdVoting;
        vm.id = $stateParams.sednicaID;

        console.log("Dosao id sednice u trece glasanje" + vm.id);

        getAktiUNacelu(vm.id);

        vm.voteThirdTime = function (a) {
            vm.voting3 = {
                sednicaID : vm.id,
                aktID : a.id,
                za: parseInt(a.za),
                protiv: parseInt(a.protiv),
                suzdrzani: parseInt(a.suzdrzani)

            };

            console.log("salje na backend" + JSON.stringify(vm.voting3) );

            $http.post('/api/sednica/voting/third_voting', vm.voting3
            ).then(function (response) {

                //     $scope.redirect();

            }, function (response) {
                alert("Vote third time failed");
            });
        };

        function getAktiUNacelu(id) {
            $http.get('/api/akt/svi_u_nacelu/' + vm.id)
                .then(function (akti) {
                    vm.akti = akti.data;
                    console.log(JSON.stringify(akti.data));
                }, function (response) {
                    alert(response.data.response);
                });
        }

        function finishSednica() {
       //     $state.go('thirdVoting', {sednicaID:vm.id});
        }
    }

})();