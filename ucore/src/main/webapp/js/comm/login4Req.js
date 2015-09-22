var loginUrl = "http://www.passport.com:9080/login/submit";
/**
 * 登录函数
 * @param eventBtn 触发事件按钮
 * @param submitUrl 
 * 		   1. 提交URL地址 passport.com
 * 		   2. 返回请求地址列表 
 * 		   3. 然后逐个发起请求，目的：储存相同的JSSEIONID来保存同一次会话）
 * @param username 登录用户名
 * @param pwd 登录密码
 */
function logins(username,password){
	 $.ajax({  
        url:loginUrl,  
        dataType:'jsonp',  
	    data:{
    	  username:username,
    	  password:password
	    },  
        jsonp:"jsonpCallback",
        success:function(result) {  
        	if (result && result.listUrl) {
        		$.each(result.listUrl, function () {
        			$.getJSON(this) 
        		})
        	}
        },  
        timeout:3000  
    });  
}

