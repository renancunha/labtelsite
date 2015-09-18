'use strict';

angular.module('labelsiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('projects', {
                parent: 'site',
                url: '/list_projects',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/projects/projects.html',
                        controller: 'ListProjectsController'
                    },
                },
                resolve: {

                }
            })
            .state('projects.view', {
                parent: 'site',
                url: '/projects/{id}',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/projects/view.html',
                        controller: 'ViewProjectsController'
                    },
                },
                resolve: {
                    entity: ['$stateParams', 'Project', function($stateParams, Project) {
                        return Project.get({id : $stateParams.id});
                    }]
                }
            });
    });
