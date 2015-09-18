'use strict';

angular.module('labelsiteApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


