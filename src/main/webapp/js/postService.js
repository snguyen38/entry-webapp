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

app.service('PostBridgeService', function() {
	  var nickName 
	  imageLink,
	  date,
	  desc,
	  like,
	  dislike;

	  var setNickName = function(value) {
		  nickName = value;
	  };

	  var getNickName = function(){
	      return nickName;
	  };
	  
	  var setImageLink = function(value) {
		  imageLink = value;
	  };
	  
	  var getImageLink = function(){
		  return imageLink;
	  };
	  
	  var setDate = function(value) {
		  date = value;
	  };
	  
	  var getDate = function(){
		  return date;
	  };
	  
	  var setDesc = function(value) {
		  desc = value;
	  };
	  
	  var getDesc = function(){
		  return desc;
	  };
	  
	  var setLike = function(value) {
		  like = value;
	  };
	  
	  var getLike = function(){
		  return like;
	  };
	  
	  var setDislike = function(value) {
		  dislike = value;
	  };
	  
	  var getDislike = function(){
		  return dislike;
	  };

	  return {
		  setNickName: setNickName,
		  getNickName: getNickName,
		  setImageLink: setImageLink,
		  getImageLink: getImageLink,
		  setDate: setDate,
		  getDate: getDate,
		  setDesc: setDesc,
		  getDesc: getDesc,
		  setLike: setLike,
		  getLike: getLike,
		  setDislike: setDislike,
		  getDislike: getDislike
	  };
});