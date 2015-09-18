'use strict';

angular.module('labelsiteApp')
    .controller('ProjectDetailController', function ($scope, $rootScope, $stateParams, entity, Project, User) {
        $scope.project = entity;
        $scope.load = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
            });
        };
        $rootScope.$on('labelsiteApp:projectUpdate', function(event, result) {
            $scope.project = result;
        });
    });
