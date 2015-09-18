'use strict';

angular.module('labelsiteApp')
    .factory('Content', function ($resource, DateUtils) {
        return $resource('api/contents/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    })
    .factory('ContentByName', function ($resource) {
        return $resource('api/content_by_name/:name', {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
