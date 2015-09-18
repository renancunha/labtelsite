
'use strict';

angular.module('labelsiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('links', {
                parent: 'site',
                url: '/links',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/links/links.html',
                        controller: 'LinksController'
                    }
                },
                resolve: {

                }
            });
    });
