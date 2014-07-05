
angular
    .module('airvals')
    .controller('loginController', ['$scope', 'UserService', '$location', function($scope, UserService, $location) {
        $scope.submitLogin = function() {

            UserService
                .logIn($scope.email, $scope.password)
                .success(function(data) {
                    console.log(data);
                    if (data.message == "success") {
                        $('#login-modal').modal('hide');
                        $scope.$parent.user = data.loggedInUser;
                    }
                    else {
                        alert("There was no user");
                    }
                });
        }
    }]);