'use strict';

angular.module('labelsiteApp')
    .controller('ContentController', function ($scope, Content) {
        $scope.contents = [];
        $scope.loadAll = function() {
            Content.query(function(result) {
               $scope.contents = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Content.get({id: id}, function(result) {
                $scope.content = result;
                $('#deleteContentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Content.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteContentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.content = {name: null, html: null, label: null, id: null};
        };
    });
