'use strict';

angular.module('labelsiteApp')
    .controller('CarouselCtrl', function ($scope, Principal) {
        $scope.myInterval = 5000;
        $scope.noWrapSlides = false;
        var slides = $scope.slides = [];
        $scope.addSlide = function() {
            var newWidth = 600 + slides.length + 1;
            slides.push({
                image: '/assets/images/slider/bg' + (slides.length+1) +'.jpg',
                text: 'Prensa didática',
                subtext: 'Prensa Didática monitorada e controlada online via dispositivos móveis para determinação de propriedades mecânicas úteis ao processo de conformação mecânica'
            });
        };
        for (var i=0; i<2; i++) {
            $scope.addSlide();
        }
    });
