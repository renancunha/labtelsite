'use strict';

angular.module('labelsiteApp')
    .controller('ContentDetailController', function ($scope, $rootScope, $stateParams, entity, Content) {
        $scope.content = entity;
        $scope.load = function (id) {
            Content.get({id: id}, function(result) {
                $scope.content = result;
            });
        };
        $rootScope.$on('labelsiteApp:contentUpdate', function(event, result) {
            $scope.content = result;
        });
    });
