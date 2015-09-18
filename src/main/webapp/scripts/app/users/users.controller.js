'use strict';

angular.module('labelsiteApp')
    .controller('UsersController', function ($scope, User, ParseLinks, Auth) {
        $scope.Users = [];
        $scope.page = 1;

        $scope.loadAll = function() {
            User.query({page: $scope.page, per_page: 20}, function(result, headers) {
                //$scope.links = ParseLinks.parse(headers('link'));
                $scope.users = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            User.get({id: id}, function(result) {
                $scope.user = result;
                $('#deleteUserConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            User.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.user = {name: null, description: null, image: null, extension: null, research: null, id: null};
        };

        $scope.activate = function (user) {
            
        };

        $scope.desactivate = function (user) {
            
        };
    });
