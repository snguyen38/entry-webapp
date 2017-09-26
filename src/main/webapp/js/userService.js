services.factory('UserService', function ($resource) {

    return $resource('rest/user/:action', {},
        {
            authenticate: {
                method: 'POST',
                params: {'action': 'authenticate'},
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            },
            register: {
                method: 'POST',
                params: {'action': 'register'},
                headers: {'Content-Type': 'application/json'}
            },
            getAvatar: {
                method: 'GET',
                params: {'action': 'getAvatar'},
                headers: {'Content-Type': 'text/plain'}
            }
            
        }
    );
});
