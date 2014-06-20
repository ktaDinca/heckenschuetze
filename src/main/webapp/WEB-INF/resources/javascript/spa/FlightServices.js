angular
    .module('airvals')
    .factory('FlightService', ['$http', function($http) {
        var flightService = {};

        flightService.findFlights = function(source, destination, departureDate, returningDate, isDirectFlight, isOneWay) {
            return $http({
                method: 'POST',
                url: '/diplomarbeit/airvals/flights/search',
                params: {
                    source: source,
                    destination: destination,
                    departureDate: departureDate,
                    returningDate: returningDate,
                    isOneWay: isOneWay,
                    isDirectFlight: isDirectFlight
                }
            });

        }

        return flightService;
    }]);