'use strict';

angular.module('labelsiteApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
