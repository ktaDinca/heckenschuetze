
angular
    .module('airvals', ['ngRoute'])
    .config(['$httpProvider', function($httpProvider) {
        delete $httpProvider.defaults.headers.common["X-Requested-With"]
    }])
    .config(function ($routeProvider) {
        var baseUrl = '/diplomarbeit/resources/spa-views/';
        $routeProvider
            .when('/', {
                templateUrl: baseUrl + 'flights.html'
            })
    })
    .controller('mainController', function($scope) {
    });

