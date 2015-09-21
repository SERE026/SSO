<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>欢迎页</title>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
 	<div>
		<form action="/login/submit">
		username:<input type="text" name="username" >
		<br>
		
		password:<input type="text" name="password" >
		<input type="text" name="service" value="${service }">
<%-- 		<input type="text" name="service1" value="${params.service }"> --%>
		<br>
		
		<input type="submit"  >
		
		</form>	
	</div>
  </body>
</html>