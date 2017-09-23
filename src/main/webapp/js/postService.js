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