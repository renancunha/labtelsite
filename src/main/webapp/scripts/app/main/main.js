'use strict';

angular.module('labelsiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'MainController'
                    },
                    'alternative-content@': {
                        templateUrl: 'scripts/app/main/carousel.html',
                        controller: 'CarouselController'
                    }
                },
                resolve: {

                }
            });
    });
