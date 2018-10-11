angular.module('info', [])
.controller('techList', function($scope, $http) {
    $http.get('http://localhost:8080/technology').
        then(function(response) {
            $scope.techs = response.data;
        });
});