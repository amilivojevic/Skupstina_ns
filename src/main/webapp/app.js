
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
                            templateUrl: '/home.html',
                            controller: 'HomeController',
                            controllerAs: 'homeCtrl'
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
                            templateUrl: 'app/user/poslanik/profil_poslanik.html',
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
                            templateUrl: 'app/user/poslanik/izmeni_profil_poslanik.html',
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
                .state('exportPDF', {
                    url: '/exportPDF',
                    views: {
                        'content': {
                            templateUrl: 'app/pdf/export_pdf.html',
                            controller: 'ExportPDFController',
                            controllerAs: 'exportPDFCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarController',
                            controllerAs: 'navbarCtrl'
                        }
                    }
                })
                .state('exportXHTML', {
                    url: '/exportXHTML',
                    views: {
                        'content': {
                            templateUrl: 'app/xhtml/export_xhtml.html',
                            controller: 'ExportXHTMLController',
                            controllerAs: 'exportXHTMLCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/navbar.html',
                            controller: 'NavbarController',
                            controllerAs: 'navbarCtrl'
                        }
                    }
                })
                .state('exportPDFPredsednik', {
                    url: '/exportPDFPredsednik',
                    views: {
                        'content': {
                            templateUrl: 'app/pdf/export_pdf_predsednik.html',
                            controller: 'ExportPDFPredsednikController',
                            controllerAs: 'exportPDFPredsednikCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/predsednik_navbar.html',
                            controller: 'NavbarPredsednikController',
                            controllerAs: 'navbarPredsednikCtrl'
                        }
                    }
                })
                .state('exportXHTMLPredsednik', {
                    url: '/exportXHTMLPredsednik',
                    views: {
                        'content': {
                            templateUrl: 'app/xhtml/export_xhtml_predsednik.html',
                            controller: 'ExportXHTMLPredsednikController',
                            controllerAs: 'exportXHTMLPredsednikCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/predsednik_navbar.html',
                            controller: 'NavbarPredsednikController',
                            controllerAs: 'navbarPredsednikCtrl'
                        }
                    }
                })
                .state('predsednik', {
                    url: '/predsednik',
                    views: {
                        'content': {
                            templateUrl: 'app/user/predsednik/profil_predsednik.html',
                            controller: 'PredsednikController',
                            controllerAs: 'predsednikCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/predsednik_navbar.html',
                            controller: 'NavbarPredsednikController',
                            controllerAs: 'navbarPredsednikCtrl'
                        }
                    }
                })
                .state('izmeniProfilPredsednik', {
                    url: '/izmeniProfilPredsednik',
                    views: {
                        'content': {
                            templateUrl: 'app/user/predsednik/izmeni_profil_predsednik.html',
                            controller: 'PredsednikController',
                            controllerAs: 'predsednikCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/predsednik_navbar.html',
                            controller: 'NavbarPredsednikController',
                            controllerAs: 'navbarPredsednikCtrl'
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
                .state('addAmandman', {
                    url: '/addAmandman',
                    views: {
                        'content': {
                            templateUrl: 'app/amandman/addAmandman.html',
                            controller: 'AddAmandmanController',
                            controllerAs: 'addAmdCtrl'
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
                            templateUrl: 'app/navbar/predsednik_navbar.html',
                            controller: 'NavbarPredsednikController',
                            controllerAs: 'navbarPredsednikCtrl'
                        }
                    }
                })
                .state('activateSednica', {
                    url: '/activateSednica',
                    views: {
                        'content': {
                            templateUrl: 'app/sednica/glasanje_prvo.html',
                            controller: 'ActivateSednicaController',
                            controllerAs: 'activateSednicaCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/predsednik_navbar.html',
                            controller: 'NavbarPredsednikController',
                            controllerAs: 'navbarPredsednikCtrl'
                        }
                    },  params: {
                        'sednicaID': null,
                        'aktNaziv': ""
                     //   'skupstina' null
                    }
                })
                .state('secondVoting', {
                    url: '/secondVoting',
                    views: {
                        'content': {
                            templateUrl: 'app/sednica/glasanje_drugo.html',
                            controller: 'SecondVotingController',
                            controllerAs: 'secondVotingCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/predsednik_navbar.html',
                            controller: 'NavbarPredsednikController',
                            controllerAs: 'navbarPredsednikCtrl'
                        }
                    },  params: {
                        'sednicaID': null
              //          'brojPrisutnih': null
                    }
                })
                .state('thirdVoting', {
                    url: '/thirdVoting',
                    views: {
                        'content': {
                            templateUrl: 'app/sednica/glasanje_trece.html',
                            controller: 'ThirdVotingController',
                            controllerAs: 'thirdVotingCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/predsednik_navbar.html',
                            controller: 'NavbarPredsednikController',
                            controllerAs: 'navbarPredsednikCtrl'
                        }
                    },  params: {
                        'sednicaID': null
                        //          'brojPrisutnih': null
                    }
                })
                .state('exportToPDF', {
                    url: '/exportToPDF',
                    views: {
                        'content': {
                            templateUrl: 'app/pdf/export_to_pdf.html',
                            controller: 'ExportPDFController',
                            controllerAs: 'exportPDFCtrl'
                        },
                        'navbar': {
                            templateUrl: 'app/navbar/predsednik_navbar.html',
                            controller: 'NavbarPredsednikController',
                            controllerAs: 'navbarPredsednikCtrl'
                        }
                    },  params: {

                        //   'skupstina': null
                    }
                })



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