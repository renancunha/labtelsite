'use strict';

angular.module('labelsiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('team', {
                parent: 'site',
                url: '/team',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/team/team.html',
                        controller: 'TeamController'
                    }
                },
                resolve: {

                }
            });
    });
