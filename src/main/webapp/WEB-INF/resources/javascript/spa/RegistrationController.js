
angular
    .module('airvals')
    .controller('registrationController', ['$scope', 'UserService', function($scope, UserService) {

        $scope.createAccount = function() {
            UserService
                .saveUser($scope.email,
                        $scope.password,
                        $scope.firstname,
                        $scope.lastname)
                .success(function(data) {
                    if (data.message == 'success') {
                        alert('cont salvat cu success!');
                    }
                    $('#registration-modal').modal('hide');
                });
        }



    }]);