/**
 * Created by Korisnik on 6/2/2017.
 */
angular
    .module('skupstinaNS', [
        'ngResource',
        'ngRoute'
    ])
    .config(function($routeProvider){
        $routeProvider
            .when('/', {
                templateUrl: 'home.html'

            })
            .when('/login', {
                templateUrl: 'app/auth/login/login.html',
                controller: 'LoginController',
                controllerAs: 'loginCtrl'
            })
            .when('/register', {
                templateUrl: 'app/auth/register/register.html',
                controller: 'RegisterController',
                controllerAs: 'registerCtrl'
            })
            .when('/profile', {
                templateUrl: 'app/user/profile.html',
                controller: 'OwnerController',
                controllerAs: 'ownerCtrl',
            })
            .otherwise({
                redirectTo: '/'
            });

    });