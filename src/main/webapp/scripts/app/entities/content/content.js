'use strict';

angular.module('labelsiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('content', {
                parent: 'entity',
                url: '/contents',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Contents'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/content/contents.html',
                        controller: 'ContentController'
                    }
                },
                resolve: {
                }
            })
            .state('content.detail', {
                parent: 'entity',
                url: '/content/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Content'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/content/content-detail.html',
                        controller: 'ContentDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Content', function($stateParams, Content) {
                        return Content.get({id : $stateParams.id});
                    }]
                }
            })
            .state('content.new', {
                parent: 'content',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/content/content-dialog.html',
                        controller: 'ContentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, html: null, label: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('content', null, { reload: true });
                    }, function() {
                        $state.go('content');
                    })
                }]
            })
            .state('content.edit', {
                parent: 'content',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/content/content-dialog.html',
                        controller: 'ContentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Content', function(Content) {
                                return Content.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('content', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
