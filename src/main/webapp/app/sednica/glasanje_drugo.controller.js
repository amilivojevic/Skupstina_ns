/**
 * Created by Nina on 17-Jun-17.
 */
(function() {
    angular.module("skupstinaNS")
        .controller("SecondVotingController", secondVotingController);

    function secondVotingController($scope,$http, $window,  $stateParams) {
        var vm = this;
        vm.getZakazaniAmandmani = getZakazaniAmandmani;
        vm.goToThirdVoting = goToThirdVoting;
        vm.id = $stateParams.sednicaID;
        vm.brojPrisutnih = $stateParams.brojPrisutnih;

        console.log("Dosao id sednice u drugo glasanje" + vm.id);
        console.log("Dosao broj prisutnih u drugo glasanje" + vm.brojPrisutnih);

        getZakazaniAmandmani(vm.id);

        vm.voteSecondTime = function (a) {
            vm.voting2 = {
                sednicaID : vm.id,
                amandmanID : a.id,
                brojPrisutnih: vm.brojPrisutnih,
                za: parseInt(a.za),
                protiv: parseInt(a.protiv),
                suzdrzani: parseInt(a.suzdrzani)

            };

            console.log("salje na backend" + JSON.stringify(vm.voting2) );

            $http.post('/api/sednica/voting/second_voting', vm.voting2
            ).then(function (response) {

                //     $scope.redirect();

            }, function (response) {
                alert("Vote second time failed");
            });
        };

        function getZakazaniAmandmani(id) {
            $http.get('/api/amandman/svi_zakazani/' + vm.id)
                .then(function (amandmani) {
                    vm.amandmani = amandmani.data;
                    console.log(JSON.stringify(amandmani.data));
                }, function (response) {
                    alert(response.data.response);
                });
        }

        function goToThirdVoting() {
            $state.go('thirdVoting', {sednicaID:vm.id}, {brojPrisutnih:parseInt(vm.sednicaData.brojPrisutnih)});
        }
    }

})();