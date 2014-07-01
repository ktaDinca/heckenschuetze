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

        };

        flightService.buyFlight = function(flightResult, familyName, surname, idSeries, idNumber, phoneno, email, action) {

            var out1_id = (flightResult.outBoundStep1 || {}).id;
            var out2_id = (flightResult.outBoundStep2 || {}).id;
            var in1_id = (flightResult.inBoundStep1 || {}).id;
            var in2_id = (flightResult.inBoundStep2 || {}).id;

            return $http({
                method: 'POST',
                url: '/diplomarbeit/airvals/flight-results/buy',
                params: {
                    out1_id : out1_id,
                    out2_id : out2_id,
                    in1_id : in1_id,
                    in2_id : in2_id,
                    price : flightResult.price,
                    familyName : familyName,
                    surname : surname,
                    idSeries : idSeries,
                    idNumber : idNumber,
                    phoneno: phoneno,
                    email : email,
                    action : action
                }
            });
        };

        return flightService;
    }]);