'use strict';

angular.module('labelsiteApp')
    .factory('User', function ($resource) {
        return $resource('api/users/:login', {}, {
                'query': {method: 'GET', isArray: true},
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                }
            });
        })
    .factory('UserListActive', function ($resource) {
        return $resource('api/users_actives', {}, {
                'get': {method: 'GET', isArray: true}
            });
        });
