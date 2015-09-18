'use strict';

angular.module('labelsiteApp')
    .directive('prettyp', function(){
        return function(scope, element, attrs) {
            $("[rel^='prettyPhoto']").carousel({
    			interval: 8000
    		});
        }
    });
