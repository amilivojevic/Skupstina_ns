/**
 * Created by Korisnik on 6/2/2017.
 */
'use strict';
angular
    .module('skupstinaNS', [
        'ngResource',
        'ngRoute'
    ])
    .config(function($routeProvider, $httpProvider){
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
            .when('/addAct', {
                templateUrl: 'app/act/addAct.html',
                controller: 'AddActController',
                controllerAs: 'addActCtrl'
            })
            .when('/addSednica', {
                templateUrl: 'app/sednica/addSednica.html',
                controller: 'AddSednicaController',
                controllerAs: 'addSednicaCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });

        $httpProvider
            .interceptors.push(['$q', '$window',
            function($q, $window) {
                return {
                    'request': function(config) {
                        config.headers = config.headers || {};
                        if($window.localStorage.token) {
                            config.headers["X-Auth-Token"] = $window.localStorage.token;
                        }
                        return config;
                    },
                    'responseError': function(response) {
                        if (response.status === 401 || response.status === 403) {

                            console.log("nesto kod interseptora je pogresno");
                        }
                        return $q.reject(response);
                    }
                };
            }
        ]);

    });