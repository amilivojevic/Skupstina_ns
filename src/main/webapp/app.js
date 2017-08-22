
(function(angular) {
    'use strict';
    angular
        .module('skupstinaNS', [
            'ngResource',
            'ngRoute',
            'restangular',
            'ui.router'
        ])
        .config(function($routeProvider,$stateProvider,$urlRouterProvider, $httpProvider){

            $urlRouterProvider.otherwise("/home");
            $stateProvider
                .state('notLoggedHome', {
                    url: '/home',
                    views: {
                        'content': {
                            templateUrl: 'app/static/notlogged_home.html',
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarController',
                            controllerAs: 'navbarCtrl'
                        }
                    },

                })
                .state('login', {
                    url: '/login',
                    views: {
                        'content': {
                            templateUrl: 'app/auth/login/login.html',
                            controller: 'LoginController',
                            controllerAs: 'loginCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarController',
                            controllerAs: 'navbarCtrl'
                        }
                    }
                })
                .state('register', {
                    url: '/register',
                    views: {
                        'content': {
                            templateUrl: 'app/auth/register/register.html',
                            controller: 'RegisterController',
                            controllerAs: 'registerCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarController',
                            controllerAs: 'navbarCtrl'
                        }
                    }
                })
                .state('profil', {
                    url: '/profil',
                    views: {
                        'content': {
                            templateUrl: 'app/user/profil_poslanik.html',
                            controller: 'PoslanikController',
                            controllerAs: 'poslanikCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarController',
                            controllerAs: 'navbarCtrl'
                        }
                    }
                })
                .state('izmeniProfil', {
                    url: '/izmeniProfil',
                    views: {
                        'content': {
                            templateUrl: 'app/user/izmeni_profil_poslanik.html',
                            controller: 'PoslanikController',
                            controllerAs: 'poslanikCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarController',
                            controllerAs: 'navbarCtrl'
                        }
                    }
                })
                .state('addAct', {
                    url: '/addAct',
                    views: {
                        'content': {
                            templateUrl: 'app/act/addAct.html',
                            controller: 'AddActController',
                            controllerAs: 'addActCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarController',
                            controllerAs: 'navbarCtrl'
                        }
                    }
                })
                .state('addSednica', {
                    url: '/addSednica',
                    views: {
                        'content': {
                            templateUrl: 'app/sednica/addSednica.html',
                            controller: 'AddSednicaController',
                            controllerAs: 'addSednicaCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarController',
                            controllerAs: 'navbarCtrl'
                        }
                    }
                })


/*'use strict';
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
                templateUrl: 'app/user/profil_poslanik.html',
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
            }); */

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

    })


})(angular);