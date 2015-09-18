'use strict';

angular.module('labelsiteApp')
    .controller('LinksController', function ($scope, Principal, Content, ContentByName) {
    	$scope.isAdmin = false;
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
            Principal.isInRole('ROLE_ADMIN')
	        .then(function(result) {
	            if (result) {
	                $scope.isAdmin = true;
	            }
	        });
        });

        $scope.content = null;

        ContentByName.get({name: 'links_page'}, function (result, headers) {
			$scope.html = result.html;
			$scope.content = result;
		});

        // Editor options.
		$scope.options = {
			language: 'en',
			allowedContent: true,
			entities: false,
			toolbar_Full: [
			    { name: 'document',    groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source', 'Save', 'NewPage', 'DocProps', 'Preview', 'Print', 'Templates', 'document' ] },
			    // On the basic preset, clipboard and undo is handled by keyboard.
			    // Uncomment the following line to enable them on the toolbar as well.
			    { name: 'clipboard',   groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', 'Undo', 'Redo' ] },
			    { name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Find', 'Replace', 'SelectAll', 'Scayt' ] },
			    { name: 'insert', items: [ 'CreatePlaceholder', 'Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe', 'InsertPre' ] },
			    { name: 'forms', items: [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
			    '/',
			    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', 'RemoveFormat' ] },
			    { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align' ], items: [ 'NumberedList', 'BulletedList', 'Outdent', 'Indent', 'Blockquote', 'CreateDiv', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', 'BidiLtr', 'BidiRtl' ] },
			    { name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
			    '/',
			    { name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
			    { name: 'colors', items: [ 'TextColor', 'BGColor' ] },
			    { name: 'tools', items: [ 'UIColor', 'Maximize', 'ShowBlocks' ] },
			    { name: 'about', items: [ 'About' ] }
			  ],
			  toolbar: 'Full'
		};

		$scope.onBlur = function () {
			if($scope.content != null) {
				$scope.content.html = $scope.html;
                $scope.content.name = 'links_page';
				Content.update($scope.content, function () {
					
				});
			}
		}
    });
