 'use strict';

angular.module('labelsiteApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-labelsiteApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-labelsiteApp-params')});
                }
                return response;
            },
        };
    });