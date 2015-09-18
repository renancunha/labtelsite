'use strict';

angular.module('labelsiteApp')
    .controller('ListProjectsController', function ($scope, Project, ParseLinks) {
        $scope.projects = [];
        $scope.loadAll = function() {
            Project.query(function(result, headers) {
                $scope.projects = result;
            });
        };
        $scope.loadAll();
    });
