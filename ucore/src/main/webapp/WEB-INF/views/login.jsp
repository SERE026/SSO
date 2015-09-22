<%@page contentType="text/html;charset=UTF-8" language="java"   %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录账户</title>
</head>
<body>
<div class="wrapper">
    <div class="login_main">
        <div class="login_center">
            <div class="login_center_left">
            <h2>用户登录</h2>
          
               <input  id="nickname" type="text"  class="userInputBox" value="请输入用户名/手机号" 
								onfocus="if(value=='请输入用户名/手机号') {value=''}"
								onblur="if (value=='') {value='请输入用户名/手机号'}"> <br>
               <input  id="pwd" type="password" class="pwdInputBox" />
            
               <input id="loginSubmit" class="su-1"  style="cursor:pointer"  type="button" value="立即登录">

            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/jquery-1.10.2.min.js" ></script>
<script type="text/javascript" src="/js/comm/login4Req.js"></script>
</body>
 <script>
      $(function(){
          
          $("#loginSubmit").click(function(){
        	  var uname = $("#nickname").val();
        	  var pwd = $("#pwd").val();
        	  
        	  logins(uname,pwd);
          })
      })
    </script>

</html>