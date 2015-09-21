var $submitUrl = "http://www.passport.com:9080/login/submit";
var $queryListUrl = "http://www.passport.com:9080/p/queryListUrl";
/**
 * 登录函数
 * @param eventBtn 触发事件按钮
 * @param submitUrl 提交URL地址 当前登录地址
 * @param queryListUrl 查询配置的各个系统的URL地址 （请求地址列表，然后逐个发起请求，目的：储存相同的JSSEIONID来保存同一次会话）
 * @param username 登录用户名
 * @param pwd 登录密码
 */
function login(eventBtn,submitUrl,queryListUrl,username,pwd){
	$(eventBtn).click(function(){
		 $.ajax({
			  type:"post",
			  url:submitUrl,
			  data: {
				  username:username,
				  password:pwd
			  } ,
			  dataType:"text",
			  success:function(data){
				  if(data == "success"){
					  //请求地址列表，然后逐个发起请求（储存相同的JSSEIONID来保存同一次会话）
					  $.ajax({
						 type:"post",
						 url:queryListUrl,
						 dataType:"text", 
						 success:function(data){
							 ajaxSubmit(data);
						 }
					  });
				  }else{
					  alert("输入的用户名或者密码有误！")
				  }
			  }
		 })
	})
}


function ajaxSubmit(data){
	for(var i=0;i<data.listUrl.length;i++){
		$.ajax({
			 type:"post",
			 url:data.listUrl[i],
			 dataType:"text", 
			 success:function(data){
			 }
	  });
	}
}
