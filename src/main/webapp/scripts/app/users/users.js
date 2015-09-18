'use strict';

angular.module('labelsiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('users', {
                parent: 'site',
                url: '/users',
                data: {
                    roles: ['ROLE_USER', 'ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/users/users.html',
                        controller: 'UsersController'
                    }
                },
                resolve: {

                }
            });
    });
