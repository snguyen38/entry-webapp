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
            
            $routeProvider.when('/userRepository', {
            	templateUrl: 'partials/userRepository.html',
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

function MainController($rootScope, $scope, BlogPostService, PostService) {

    $scope.blogPosts = BlogPostService.query();
    $scope.posts = PostService.query();

    $scope.deletePost = function (blogPost) {
        blogPost.$remove(function () {
            $scope.blogPosts = BlogPostService.query();
        });
    };
}

function EditController($scope, $routeParams, $location, BlogPostService, PostService) {

    /*$scope.blogPost = BlogPostService.get({id: $routeParams.id});

    $scope.save = function () {
        $scope.blogPost.$save(function () {
            $location.path('/');
        });
    };*/
	
	PostService.get({id: $routeParams.id}).$promise.then(function (result) {
		$scope.post = result.post;
		$scope.fullName = result.fullName;
		$scope.comments = result.comments;
	});
	
	
}

function UploadImageController($scope, $rootScope, $location, $http, BlogPostService) {
	$scope.uploadImage = function () {
		var fd = new FormData();
        fd.append('file', $scope.image);
        fd.append('category', $scope.imageCategories);
        fd.append('description', $scope.description);
        
        $http.post("/entry-webapp/rest/posts/uploadImage", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .then(function(response){
        	$scope.status = response.data;
        	if (response.data) {
        		$rootScope.info = "Image upload successfully";
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
            	$rootScope.userId = authenticationResult.user.id;
                $rootScope.user = user;
                $rootScope.avatar = user.avatar;
                $location.path("/");
            });
            
            /*$http({ method: "GET",
		    	    url: "/entry-webapp/rest/user/getAvatar",
		    	    params: {username: $scope.username}
            	}).then(function(response) {
			   		$rootScope.avatar = response.data;
			  	}
			);*/
        });
    };
}

function RegisterController($scope, $rootScope, $location, $http, $cookieStore, UserService, UserBridgeService) {
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
            headers: {'Content-Type': undefined}
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
                    	$rootScope.userId = authenticationResult.user.id;
                        $rootScope.user = user;
                        $rootScope.avatar = user.avatar;
                        $location.path("/");
                    });
                    
                    /*$http({ method: "GET",
        		    	    url: "/entry-webapp/rest/user/getAvatar",
        		    	    params: {username: $scope.nickName}
                    	}).then(function(response) {
        			   		$rootScope.avatar = response.data;
        			  	}
        			);*/
                });
        	}
        });
	};
}

function UserDetailsController($scope, $routeParams, $rootScope, $location, $http, $cookieStore, UserService, UserBridgeService) {
	$scope.firstName = UserBridgeService.getFirstName();
	$scope.lastName = UserBridgeService.getLastName();
	$scope.email = UserBridgeService.getEmail();
	$scope.phone = UserBridgeService.getPhone();
	$scope.country = UserBridgeService.getCountry();
	$scope.nickName = UserBridgeService.getNickName();
	
	
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
            headers: {'Content-Type': undefined}
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

var services = angular.module('app.services', ['ngResource']);

services.factory('BlogPostService', function ($resource) {

    return $resource('rest/blogposts/:id', {id: '@id'});
});

services.factory('PostService', function ($resource) {

    return $resource('rest/posts/:id', {id: '@id'});
});