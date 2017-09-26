app.directive('enterEvent', function ($http, $rootScope, CommentService) {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
        	// delete warning message when have input
        	delete scope.commentEmpty;
        	
            if(event.which === 13) {
                scope.$apply(function (){
                	if (scope.comment) {
	                    var fd = new FormData();
	                    fd.append('postId', scope.post.id);
	                    fd.append('username', attrs.enterEvent);
	                    fd.append('content', scope.comment);
	                    
	                    $http.post(window.location.pathname + "rest/comments/postComment", fd, {
	                        transformRequest: angular.identity,
	                        headers: {'Content-Type': undefined, 'X-Access-Token': $rootScope.accessToken}
	                    }).then(function(response){
	                    	if (response.data) {
	                    		scope.comment = '';
	                    		
	                    		//refresh comment list
	                    		var fd = new FormData();
	                            fd.append('postId', scope.post.id);
	                            
	                            $http.post(window.location.pathname + "rest/comments/getCommentsByPost", fd, {
	                                transformRequest: angular.identity,
	                                headers: {'Content-Type': undefined, 'X-Access-Token': $rootScope.accessToken}
	                            }).then(function(response){
	                            	if (response.data) {
	                            		scope.comments  = response.data;
	                            	}
	                            });
	                            
	                    	}
	                    });
                	} else {
                		scope.commentEmpty = true;
                	}
                });
                
                event.preventDefault();
            }
        });
    };
});