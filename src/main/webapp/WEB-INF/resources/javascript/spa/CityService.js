angular
    .module('airvals')
    .factory('CityService', ['$http', function($http) {
        var cityService = {};

        cityService.getCities = function() {
            return $http({
                method: "GET",
                url: "/diplomarbeit/airvals/cities"
            });
        }

        return cityService;
    }]);