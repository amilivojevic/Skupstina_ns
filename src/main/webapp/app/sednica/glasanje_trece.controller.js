/**
 * Created by Nina on 17-Jun-17.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("ThirdVotingController", thirdVotingController);

    function thirdVotingController($scope,$http, $window, $state, $stateParams) {
        var vm = this;
        vm.getAktiUNacelu = getAktiUNacelu;
        vm.finishSednica = finishSednica;
   //     vm.goToThirdVoting = goToThirdVoting;
        vm.id = $stateParams.sednicaID;

        console.log("Dosao id sednice u trece glasanje" + vm.id);

        getAktiUNacelu(vm.id);

        $scope.redirect2 = function(){
            $state.go('predsednik');
        }

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
                    vm.aktiReset = akti.data;
                    for(var i = 0; i<vm.aktiReset.length; i++){
                        vm.aktiReset[i].za = 0;
                        vm.aktiReset[i].protiv = 0;
                        vm.aktiReset[i].suzdrzani = 0;
                    }
                    vm.akti = vm.aktiReset;
                    console.log(JSON.stringify(akti.data));
                }, function (response) {
                    alert(response.data.response);
                });
        }

        function finishSednica() {
            $http.post('/api/sednica/zavrsi/' + vm.id).then(function (response) {

                $scope.redirect2();

            },function(response){
                alert("Registration failed");
            });
        }

    }

})();