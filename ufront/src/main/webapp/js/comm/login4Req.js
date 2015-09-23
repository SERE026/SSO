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
	 var if_id = "iframeId";
	 var inner_iframe = $("<div id=\"\""+if_id+"></div>");
	 $("body").append(inner_iframe);
	 $("#"+if_id).empty();
	 
	 
	 $.ajax({  
        url:loginUrl,  
        dataType:'jsonp',  
	    data:{
    	  username:username,
    	  password:password
	    },  
        jsonp:"jsonpCallback",
        success:function(result) {  
        	if (result && result.sso) {
        		$.each(result.sso, function () {
        			inner_iframe.append("<iframe src="+this.split("/")[0]+" style=\"display:none\"> </iframe> ");
        			//alert(this+"?JSESSIONID_SIGN="+result.jsessionid)
        			$.ajax({  
        		        url:this+"?JSESSIONID_SIGN="+result.jsessionid,  
        		        dataType:'jsonp',  
        		        jsonp:"jsonpCallback",
        		        success:function(result) {  
        		        },  
        		        timeout:3000  
        			});
        		})
        	}
        },  
        timeout:3000  
    });  
}

