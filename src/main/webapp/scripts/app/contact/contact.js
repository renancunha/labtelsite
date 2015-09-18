'use strict';

angular.module('labelsiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contact', {
                parent: 'site',
                url: '/contact',
                data: {
                    roles: []
                },
                views: {
                    'alternative-content@': {
                        templateUrl: 'scripts/app/contact/contact.html'
                    }
                },
                resolve: {

                }
            });
    });
