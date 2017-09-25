var app = angular.module('app', ['ngRoute', 'ngCookies', 'app.services'])
    .config(
        ['$routeProvider', '$locationProvider', '$httpProvider', function ($routeProvider, $locationProvider, $httpProvider) {

            $routeProvider.when('/uploadImage', {
                templateUrl: 'partials/imageUpload.html',
                controller: UploadImageController
            });

            $routeProvider.when('/edit/:id', {
                templateUrl: 'partials/edit.html',
                controller: EditController
            });

            $routeProvider.when('/login', {
                templateUrl: 'partials/login.html',
                controller: LoginController
            });
            
            $routeProvider.when('/register', {
                templateUrl: 'partials/register.html',
                controller: RegisterController
            });
            
            $routeProvider.when('/userDetails/:id', {
            	templateUrl: 'partials/userDetails.html',
            	controller: UserDetailsController
            });
            
            $routeProvider.when('/userRepository/:id', {
            	templateUrl: 'partials/userRepository.html',
            	controller: UserRepositoryController
            });
            
            $routeProvider.otherwise({
                templateUrl: 'partials/main.html',
                controller: MainController
            });

            $locationProvider.hashPrefix('!');

            /* Register error provider that shows message on failed requests or redirects to login page on
             * unauthenticated requests */
            $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
                    return {
                        'responseError': function (rejection) {
                            var status = rejection.status;
                            var config = rejection.config;
                            var method = config.method;
                            var url = config.url;

                            if (status == 401) {
                                $location.path("/login");
                            } else {
                                $rootScope.error = method + " on " + url + " failed with status " + status;
                            }

                            return $q.reject(rejection);
                        }
                    };
                }
            );

            /* Registers auth token interceptor, auth token is either passed by header or by query parameter
             * as soon as there is an authenticated user */
            $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
                    return {
                        'request': function (config) {
                            var isRestCall = config.url.indexOf('rest') == 0;
                            if (isRestCall && angular.isDefined($rootScope.accessToken)) {
                                var accessToken = $rootScope.accessToken;
                                if (appConfig.useAccessTokenHeader) {
                                    config.headers['X-Access-Token'] = accessToken;
                                } else {
                                    config.url = config.url + "?token=" + accessToken;
                                }
                            }
                            return config || $q.when(config);
                        }
                    };
                }
            );

        }]
    ).run(function ($rootScope, $location, $cookieStore, UserService) {

    /* Reset error when a new view is loaded */
    $rootScope.$on('$viewContentLoaded', function () {
        delete $rootScope.error;
        delete $rootScope.info;
    });

    $rootScope.hasRole = function (role) {

        if ($rootScope.user === undefined) {
            return false;
        }

        if ($rootScope.user.roles[role] === undefined) {
            return false;
        }

        return $rootScope.user.roles[role];
    };

    $rootScope.logout = function () {
        delete $rootScope.user;
        delete $rootScope.accessToken;
        $cookieStore.remove('accessToken');
        $location.path("/login");
    };

    /* Try getting valid user from cookie or go to login page */
    var originalPath = $location.path();
    $location.path("/login");
    var accessToken = $cookieStore.get('accessToken');
    if (accessToken !== undefined) {
        $rootScope.accessToken = accessToken;
        UserService.get(function (user) {
            $rootScope.user = user;
            $rootScope.avatar = user.avatar;
            $location.path(originalPath);
        });
    }

    $rootScope.initialized = true;
}).directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

function MainController($rootScope, $scope, PostService) {
    $scope.posts = PostService.query();

    $scope.imageMouseOver = function (event) {
		var likeEle = angular.element( event.target.nextElementSibling );
		likeEle.removeClass('hidden');
	};
	
	$scope.imageMouseLeave = function () {
		var likeEle = angular.element( event.target.nextElementSibling );
		likeEle.addClass('hidden');
	};
}

function EditController($rootScope, $scope, $routeParams, $location, $http, PostService) {
	$scope.imageMouseOver = function () {
		$scope.showLike = true;
	};
	
	$scope.imageMouseLeave = function () {
		$scope.showLike = false;
	};
	
	PostService.get({id: $routeParams.id}).$promise.then(function (result) {
		$scope.post = result.post;
		$scope.username = result.post.username;
		$scope.comments = result.comments;
	});
	
	$scope.updateLikeAndDislike = function (likeNumber, dislikeNumber) {
		var fd = new FormData();
        fd.append('postId', $scope.post.id);
        fd.append('likeNumber', likeNumber);
        fd.append('dislikeNumber', dislikeNumber);
        
        $http.post("/entry-webapp/rest/posts/updateLikeAndDislike", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'X-Access-Token': $rootScope.accessToken}
        }).then(function(response){
        	if (response.data) {
        		$scope.post.likeNumber = likeNumber;
        		$scope.post.dislikeNumber = dislikeNumber;
        	}
        });
	};
}

function UploadImageController($scope, $rootScope, $location, $http) {
    var fd = new FormData();
	$http.post("/entry-webapp/rest/posts/getCategories", fd, {
		transformRequest: angular.identity,
		headers: {'Content-Type': 'application/json', 'X-Access-Token': $rootScope.accessToken}
	})
	.then(function(response) {
		var res = response.data;
		if (res) {
			$scope.categories = res;
		}
	});

	$scope.uploadImage = function () {
        delete $rootScope.error;
        delete $rootScope.info;

		var fd = new FormData();
        fd.append('file', $scope.image);
        fd.append('username', $rootScope.user.name);
        fd.append('category', $scope.imageCategory.categoryName);
        fd.append('description', $scope.description);
        
        $http.post("/entry-webapp/rest/posts/uploadImage", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'X-Access-Token': $rootScope.accessToken}
        })
        .then(function(response){
        	$scope.status = response.data;
        	if (response.data) {
        		$rootScope.info = "Image upload successfully";
        	} else {
                $rootScope.error = "Image upload failed";
            }
        	
        });
	};
}

function LoginController($scope, $rootScope, $location, $cookieStore, $http, UserService, UserBridgeService) {

    $scope.rememberMe = false;

    $scope.login = function () {
        UserService.authenticate($.param({
            username: $scope.username,
            password: $scope.password
        }), function (authenticationResult) {
            var accessToken = authenticationResult.token;
            $rootScope.accessToken = accessToken;
            if ($scope.rememberMe) {
                $cookieStore.put('accessToken', accessToken);
            }
            
            UserBridgeService.setFirstName(authenticationResult.user.firstName);
    		UserBridgeService.setLastName(authenticationResult.user.lastName);
    		UserBridgeService.setEmail(authenticationResult.user.email);
    		UserBridgeService.setPhone(authenticationResult.user.phone);
    		UserBridgeService.setCountry(authenticationResult.user.country);
    		UserBridgeService.setNickName(authenticationResult.user.nickName);
    		
            UserService.get(function (user) {
                $rootScope.user = user;
                $location.path("/");
            });
        });
    };
}

function RegisterController($scope, $rootScope, $location, $http, $cookieStore, UserService, UserBridgeService) {
	var fd = new FormData();
	$http.post("/entry-webapp/rest/countries/getCountries", fd, {
		transformRequest: angular.identity,
		headers: {'Content-Type': 'application/json', 'X-Access-Token': $rootScope.accessToken}
	})
	.then(function(response) {
		var res = response.data;
		if (res) {
			$scope.countries = res;
		}
	});
	
	$scope.register = function () {
		var fd = new FormData();
        fd.append('file', $scope.avatar);
        fd.append('firstName', $scope.firstName);
        fd.append('lastName', $scope.lastName);
        fd.append('password', $scope.password);
        fd.append('email', $scope.email);
        fd.append('phone', $scope.phone);
        fd.append('country', $scope.country);
        fd.append('nickName', $scope.nickName);
        
        $http.post("/entry-webapp/rest/user/register", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'X-Access-Token': $rootScope.accessToken}
        })
        .then(function(response){
        	$scope.status = response.data;
        	if ($scope.status) {
        		UserBridgeService.setFirstName($scope.firstName);
        		UserBridgeService.setLastName($scope.lastName);
        		UserBridgeService.setEmail($scope.email);
        		UserBridgeService.setPhone($scope.phone);
        		UserBridgeService.setCountry($scope.country);
        		UserBridgeService.setNickName($scope.nickName);
        		
        		UserService.authenticate($.param({
                    username: $scope.nickName,
                    password: $scope.password
                }), function (authenticationResult) {
                    var accessToken = authenticationResult.token;
                    $rootScope.accessToken = accessToken;
                    
                    UserService.get(function (user) {
                        $rootScope.user = user;
                        $location.path("/");
                    });
                    
                });
        	}
        });
	};
}

function UserDetailsController($scope, $routeParams, $rootScope, $location, $http, $cookieStore, UserService, UserBridgeService) {
	var fd = new FormData();
	$http.post("/entry-webapp/rest/countries/getCountries", fd, {
		transformRequest: angular.identity,
		headers: {'Content-Type': 'application/json', 'X-Access-Token': $rootScope.accessToken}
	})
	.then(function(response) {
		var res = response.data;
		if (res) {
			$scope.countries = res;
		}
	});
	
	fd = new FormData();
	fd.append('id', $routeParams.id);
	
	$http.post("/entry-webapp/rest/user/getUserById", fd, {
		transformRequest: angular.identity,
		headers: {'Content-Type': undefined, 'X-Access-Token': $rootScope.accessToken}
	})
	.then(function(response) {
		var res = response.data;
		if (res) {
			$scope.firstName = res.firstName;
			$scope.lastName = res.lastName;
			$scope.email = res.email;
			$scope.phone = res.phone;
			$scope.country = res.country;
			$scope.nickName = res.nickName;
		}
	});
	
	/*$scope.firstName = UserBridgeService.getFirstName();
	$scope.lastName = UserBridgeService.getLastName();
	$scope.email = UserBridgeService.getEmail();
	$scope.phone = UserBridgeService.getPhone();
	$scope.country = UserBridgeService.getCountry();
	$scope.nickName = UserBridgeService.getNickName();*/
	
	
	$scope.updateUser = function () {
		var fd = new FormData();

        fd.append('file', $scope.avatar);
        fd.append('firstName', $scope.firstName);
        fd.append('lastName', $scope.lastName);
        fd.append('password', $scope.password);
        fd.append('phone', $scope.phone);
        fd.append('country', $scope.country);
        fd.append('nickName', $scope.nickName);
        
        $http.post("/entry-webapp/rest/user/updateUser/"+ $routeParams.id, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'X-Access-Token': $rootScope.accessToken}
        })
	    .then(function(response){
	    	var res = response.data;
	    	if (res) {
	    		$rootScope.user.name = res.username;
	    		
	    		$http({ method: "GET",
		    	    url: "/entry-webapp/rest/user/getAvatar",
		    	    params: {username: $scope.nickName}
            	}).then(function(response) {
				   		$rootScope.avatar = response.data;
				  	}
				);
	    	}
	    });
	};
}

function UserRepositoryController($scope, $routeParams, $rootScope, $location, $http, $cookieStore, UserService, UserBridgeService) {
	var fd = new FormData();
	
	fd.append('userId', $routeParams.id);
	
	$http.post("/entry-webapp/rest/posts/getPostByUser", fd, {
		transformRequest: angular.identity,
		headers: {'Content-Type': undefined, 'X-Access-Token': $rootScope.accessToken}
	})
	.then(function(response) {
		var res = response.data;
		if (res) {
			$scope.posts = res;
		}
	});
	
	$scope.deletePosts = function () {
        $scope.postSelectedArray = [];
        $scope.postSelectedIndex = [];
        var removed = 0;
        angular.forEach($scope.posts, function(post) {
            if (!!post.post.selected) $scope.postSelectedArray.push(post.post.id);
        })
          
        var fd = new FormData();
        fd.append('postIds', JSON.stringify($scope.postSelectedArray));
        
        $http.post("/entry-webapp/rest/posts/deletePosts", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'X-Access-Token': $rootScope.accessToken}
        }).then(function(response){
            if (response.data) {
                angular.forEach($scope.posts, function(post) {
                    angular.forEach($scope.postSelectedArray, function(p) {
                        if (post.post.id == p) {
                            var index = $scope.posts.indexOf(post);
                            $scope.postSelectedIndex.push(index);
                        }
                    })
                })

                angular.forEach($scope.postSelectedIndex, function(index) {
                    $scope.posts.splice(index - removed, 1);
                    removed = removed + 1;
                })
            }
        });  
    };
}

var services = angular.module('app.services', ['ngResource']);

services.factory('PostService', function ($resource) {

    return $resource('rest/posts/:id', {id: '@id'});
});

services.factory('CommentService', function ($resource) {

    return $resource('rest/comments/:id', {id: '@id'});
});