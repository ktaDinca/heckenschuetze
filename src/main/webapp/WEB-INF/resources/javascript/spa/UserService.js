angular
    .module('airvals')
    .factory('UserService', ['$http', function ($http) {

        var userService = {};
        userService.saveUser = function (email, password, firstname, lastname) {
            return $http({
                method: 'POST',
                url: '/diplomarbeit/airvals/user/save',
                params: {
                    email: email,
                    password: password,
                    firstname: firstname,
                    lastname: lastname
                }
            });
        }

        userService.logIn = function (email, password) {
            return $http({
                method: 'POST',
                url: '/diplomarbeit/airvals/user/login',
                params: {
                    email: email,
                    password: password
                }
            });
        }

        return userService;
    }]);