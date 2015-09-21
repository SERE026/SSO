<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>欢迎页</title>
<script language="javascript" type="text/javascript" src="/js/jquery.js"></script>
<script language="javascript" type="text/javascript" src="/js/jquery.min.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
 	<div>
 		<span>系统版本 1.0</span><br>
		<span>测试版本</span>
	
	</div>
  </body>
</html>
<iframe id="myframe" width="100%" frameBorder="0" src="http://www.web2.com:9080/ticket" scrolling="no">

</iframe>
 
<script type="text/javascript">

	
	function valTicket(){
		/** $.ajax({
			  type:"post", 
			  url:"http://www.web2.com:9080/ticket",
			  cache:false,
			  data:{ 
				  inviteCode:top_inviteCode
			   },
			  dataType:"text",
			  success:function(data){
			  }
		});
		**/
	}
	
	valTicket();



</script>