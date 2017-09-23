


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

app.service('UserBridgeService', function() {
	  var firstName,
	  lastName,
	  email,
	  phone,
	  country,
	  nickName;

	  var setFirstName = function(value) {
		  firstName = value;
	  };

	  var getFirstName = function(){
	      return firstName;
	  };
	  
	  var setLastName = function(value) {
		  lastName = value;
	  };
	  
	  var getLastName = function(){
		  return lastName;
	  };
	  
	  var setEmail = function(value) {
		  email = value;
	  };
	  
	  var getEmail = function(){
		  return email;
	  };
	  
	  var setPhone = function(value) {
		  phone = value;
	  };
	  
	  var getPhone = function(){
		  return phone;
	  };
	  
	  var setCountry = function(value) {
		  country = value;
	  };
	  
	  var getCountry = function(){
		  return country;
	  };
	  
	  var setNickName = function(value) {
		  nickName = value;
	  };
	  
	  var getNickName = function(){
		  return nickName;
	  };

	  return {
		  setFirstName: setFirstName,
		  getFirstName: getFirstName,
		  setLastName: setLastName,
		  getLastName: getLastName,
		  setEmail: setEmail,
		  getEmail: getEmail,
		  setPhone: setPhone,
		  getPhone: getPhone,
		  setCountry: setCountry,
		  getCountry: getCountry,
		  setNickName: setNickName,
		  getNickName: getNickName
	  };
});