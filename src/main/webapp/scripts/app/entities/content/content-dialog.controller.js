'use strict';

angular.module('labelsiteApp').controller('ContentDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Content',
        function($scope, $stateParams, $modalInstance, entity, Content) {

        $scope.content = entity;
        $scope.load = function(id) {
            Content.get({id : id}, function(result) {
                $scope.content = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('labelsiteApp:contentUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.content.id != null) {
                Content.update($scope.content, onSaveFinished);
            } else {
                Content.save($scope.content, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
