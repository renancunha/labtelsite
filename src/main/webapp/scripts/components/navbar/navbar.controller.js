'use strict';

angular.module('labelsiteApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.isAdmin = false;
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
            Principal.isInRole('ROLE_ADMIN')
	        .then(function(result) {
	            if (result) {
	                $scope.isAdmin = true;
	            }
	        });
        });

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
            window.location.reload();
        };
    });
