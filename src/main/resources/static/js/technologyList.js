angular.module('info', [])
.controller('techList', function($scope, $http) {
    $http.get('http://localhost:8080/technology/1').
        then(function(response) {
            $scope.techs = response.data;
        });
});