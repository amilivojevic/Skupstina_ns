/**
 * Created by Korisnik on 6/15/2017.
 */
(function(angular) {
    'use strict';

    angular
        .module('skupstinaNS')
        .service('XhttpService', xhttpService);

    function xhttpService() {

        return {
            get: get,
            post: post
        };

        function get(url) {
            var xhttpRequest = new XMLHttpRequest();
            xhttpRequest.open('GET', url, false);
            xhttpRequest.send();
            return xhttpRequest.responseXML;
        }

        function post(data, url) {
            var xhttpRequest = new XMLHttpRequest();
            xhttpRequest.open('POST', url, false);
            xhttpRequest.send(data);
            return xhttpRequest.responseXML;
        }

    }
})(angular);