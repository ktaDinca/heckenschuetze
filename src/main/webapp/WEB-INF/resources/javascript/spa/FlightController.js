angular
    .module('airvals')
    .controller('flightsController', ['$scope', '$http' , 'CityService', 'FlightService', '$translate', function($scope, $http, CityService, FlightService, $translate) {

        $('#airvalsSearchPanel').addClass('animated slideInDown');

        // init datepicker
        $('.input-daterange').datepicker({
            todayHighlight: true,
            format: 'MM, dd  yyyy'
        });

        $scope.searchFlightsHandler = function() {
            $scope.source = $('#airport-source').val();
            $scope.destination = $('#airport-dest').val();

            $scope.isOneWay = $('#oneWayOnOff').prop('checked');
            $scope.isDirectFlight = $('#directFlightOnOff').prop('checked');

            var dDate = new Date($scope.departureDate);
            var aDate = new Date($scope.returningDate);

            FlightService.findFlights($scope.source,
                                      $scope.destination,
                                      dDate.getTime(),
                                      aDate.getTime(),
                                      $scope.isDirectFlight,
                                      $scope.isOneWay)
                           .success(function(data) {
                                console.log(data);
                                if (data.message == 'success') {
                                    $scope.processReturnedFlights(data);
                                }

                           });
        };

        $scope.processReturnedFlights = function (data) {
            $scope.flights = data.flights;

            $scope.fastest = data.fastestTrip;
            $scope.fastest.name = "Fastest Trip";

            $scope.maxim = data.maximisedTime;
            $scope.maxim.name = "Maximum time spent";

            $scope.bestPrice = data.lowestPrice;
            $scope.bestPrice.name = "Best Price";

            $scope.recommendations = [$scope.bestPrice, $scope.fastest, $scope.maxim];

            $scope.fastestOutStep1Departure = new Date($scope.fastest.outBoundStep1.departure);

            if ($scope.flights.length == 0) {
                bootbox.alert("No results have been found!", function() {});
            }
        };

//        AUTOCOMPLETION SHIT
        CityService.getCities().success(function (data) {
            var substringMatcher = function (strs) {
                return function findMatches(q, cb) {
                    var matches, substringRegex;

                    // an array that will be populated with substring matches
                    matches = [];

                    // regex used to determine if a string contains the substring `q`
                    substrRegex = new RegExp(q, 'i');

                    // iterate through the pool of strings and for any string that
                    // contains the substring `q`, add it to the `matches` array
                    $.each(strs, function (i, str) {
                        if (substrRegex.test(str)) {
                            // the typeahead jQuery plugin expects suggestions to a
                            // JavaScript object, refer to typeahead docs for more info
                            matches.push({ value: str });
                        }
                    });

                    cb(matches);
                };
            };

            var states = [];

            for (var i = 0; i < data.cities.length; ++i) {
                states.push(data.cities[i].name);
            }

            $('.typeahead').typeahead(
                {
                    hint: true,
                    highlight: true,
                    minLength: 1
                },
                {
                    name: 'states',
                    displayKey: 'value',
                    source: substringMatcher(states)
                }
            );
        });

        $scope.openLogInModal = function() {
            $('#login-modal').modal('toggle');
        };

        $scope.openRegisterModal = function() {
            $('#login-modal').modal('hide');
            $('#registration-modal').modal('show');
        };

        $scope.openAdminLogIn = function() {
            $('#login-modal').addClass('animated slideOutDown');
            setTimeout(function() {
                $('#login-modal').modal('hide');
                $('#login-modal').removeClass('animated slideOutDown');
            }, 1500);

            $('#login-admin-modal').modal('toggle');

        };

        function compareFlightResults(flight1, flight2) {
            // outboundstep1
            if (flight1.outBoundStep1 != null && flight2.outBoundStep1 == null ||
                flight1.outBoundStep1 == null && flight2.outBoundStep1 != null) {
                return false;
            }
            if (flight1.outBoundStep1 != null && flight2.outBoundStep1 != null &&
                flight1.outBoundStep1.id != flight2.outBoundStep1.id) {
                return false;
            }

            // outboundstep2
            if (flight1.outBoundStep2 != null && flight2.outBoundStep2 == null ||
                flight1.outBoundStep2 == null && flight2.outBoundStep2 != null) {
                return false;
            }
            if (flight1.outBoundStep1 != null && flight2.outBoundStep1 != null &&
                flight1.outBoundStep1.id != flight2.outBoundStep1.id) {
                return false;
            }

            // inboundstep1
            if (flight1.inBoundStep1 != null && flight2.inBoundStep1 == null ||
                flight1.inBoundStep1 == null && flight2.inBoundStep1 != null) {
                return false;
            }
            if (flight1.inBoundStep1 != null && flight2.inBoundStep1 != null &&
                flight1.inBoundStep1.id != flight2.inBoundStep1.id) {
                return false;
            }

            // inboundstep2
            if (flight1.inBoundStep2 != null && flight2.inBoundStep2 == null ||
                flight1.inBoundStep2 == null && flight2.inBoundStep2 != null) {
                return false;
            }
            if (flight1.inBoundStep2 != null && flight2.inBoundStep2 != null &&
                flight1.inBoundStep2.id != flight2.inBoundStep2.id) {
                return false;
            }
            return true;
        }

        $scope.buyTicket = function(flightResult, action) {
            console.log(flightResult);

            var familyName = $('#familyName').val();
            var surname = $('#surname').val();
            var idSeries = $('#idSeries').val();
            var phoneno = $('#phoneno').val();
            var idNumber = $('#idNumber').val();
            var email = $('#email').val();

            FlightService
                .buyFlight(flightResult,familyName, surname, idSeries, idNumber, phoneno, email, action)
                .success(function(data) {
                    console.log(data);
                    $scope.ticketLocation = data.ticketPath;
                    $('#buyTicketModal').modal('hide');
                    $('#ticketBoughtModal').modal('show');
                });
        };

        $scope.openBuyTicketModal = function(flight) {
            console.log("open");
            $scope.selectedFlight = flight;
            $('#buyTicketModal').modal('show');
        };

        $scope.openPersonalInfoModal = function() {
            $('#personalInfoModal').modal('show');
        }

        $scope.loadHistory = function () {
            $http({
                method: 'GET',
                url: '/diplomarbeit/airvals/user/history'
            }).success(function (data) {
                console.log(data);
                $scope.userActions = data.interactions;
            });
        }

        /*
         when the personal info is shown, the request for loading the history will be triggered
         */
        $('#personalInfoModal').on('shown.bs.modal', function() {
            $scope.loadHistory();
        });

        $scope.changeLanguage = function(lang) {
            $translate.use(lang);
        }

        $scope.showMapHandler = function(flight) {
            $('#gmapsModal').modal('show');
            $('#gmapsModal').on('shown.bs.modal', function() {
                google.maps.event.trigger($scope.map, 'resize');
            });

            console.log("selected flight");
            console.log(flight);

            $scope.selectedFlight = flight;
            $scope.flightPathCoordinates = [];
            $scope.returnFlightPathCoordinates = [];

            console.log($scope.returnFlightPathCoordinates);

            var returnFlightPath;
            var flightPath;
//            flightPath.setMap(null);
//            returnFlightPath.setMap(null);

            if ($scope.selectedFlight != null) {
                var marker;
                var position;
                if ($scope.selectedFlight.outBoundStep1 != null) {

                    // source airport
                    position = new google.maps.LatLng($scope.selectedFlight.outBoundStep1.template.source.latitude,
                        $scope.selectedFlight.outBoundStep1.template.source.longitude);

                    marker = new google.maps.Marker({
                        title: '',
                        position: position
                    });
                    marker.setMap($scope.map);
                    $scope.flightPathCoordinates.push(position);

                    // destination airport
                    position = new google.maps.LatLng($scope.selectedFlight.outBoundStep1.template.destination.latitude,
                        $scope.selectedFlight.outBoundStep1.template.destination.longitude);
                    marker = new google.maps.Marker({
                        title: '',
                        position: position
                    });
                    marker.setMap($scope.map);
                    $scope.flightPathCoordinates.push(position);

                }
                if ($scope.selectedFlight.outBoundStep2 != null) {
                    position = new google.maps.LatLng($scope.selectedFlight.outBoundStep2.template.destination.latitude,
                        $scope.selectedFlight.outBoundStep2.template.destination.longitude);
                    marker = new google.maps.Marker({
                        title: '',
                        position: position
                    });
                    marker.setMap($scope.map);
                    $scope.flightPathCoordinates.push(position);
                }

                // inbound
                if ($scope.selectedFlight.inBoundStep1 != null) {
                    position = new google.maps.LatLng($scope.selectedFlight.inBoundStep1.template.source.latitude,
                        $scope.selectedFlight.inBoundStep1.template.source.longitude);
                    marker = new google.maps.Marker({
                        title: '',
                        position: position
                    });
                    marker.setMap($scope.map);
                    $scope.returnFlightPathCoordinates.push(position);

                    position = new google.maps.LatLng($scope.selectedFlight.inBoundStep1.template.destination.latitude,
                        $scope.selectedFlight.inBoundStep1.template.destination.longitude);
                    marker = new google.maps.Marker({
                        title: '',
                        position: position
                    });
                    marker.setMap($scope.map);
                    $scope.returnFlightPathCoordinates.push(position);
                }

                if ($scope.selectedFlight.inBoundStep2 != null) {
                    position = new google.maps.LatLng($scope.selectedFlight.inBoundStep2.template.destination.latitude,
                        $scope.selectedFlight.inBoundStep2.template.destination.longitude);
                    marker = new google.maps.Marker({
                        title: '',
                        position: position
                    });
                    marker.setMap($scope.map);
                    $scope.returnFlightPathCoordinates.push(position);
                }
            }

            flightPath = new google.maps.Polyline({
                path: $scope.flightPathCoordinates,
                geodesic: true,
                strokeColor: '#16a085', //verde
                strokeOpacity: 1.0,
                strokeWeight: 3
            });

            returnFlightPath = new google.maps.Polyline({
                path: $scope.returnFlightPathCoordinates,
                geodesic: true,
                strokeColor: '#3498db', //mov
                strokeOpacity: 1.0,
                strokeWeight: 3
            });

            flightPath.setMap($scope.map);
            returnFlightPath.setMap($scope.map);
        }


    }]);