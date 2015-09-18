'use strict';

angular.module('labelsiteApp')
    .factory('Project', function ($resource, DateUtils) {
        return $resource('api/projects/:id', {}, {
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
    .factory('ProjectsResearch', function ($resource) {
        return $resource('api/projects_research', {}, {
            'get': { method: 'GET', isArray: true}
        });
    })
    .factory('ProjectsExtension', function ($resource) {
        return $resource('api/projects_extension', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
