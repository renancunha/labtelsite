'use strict';

angular.module('labelsiteApp')
    .controller('TeamController', function ($scope, Principal, User, UserListActive) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.Users = [];

        $scope.loadAll = function() {
            UserListActive.get({}, function(result, headers) {
                $scope.users = result;
            });
        };
        $scope.loadAll();

    });
