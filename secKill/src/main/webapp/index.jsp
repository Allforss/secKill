<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/secKill/resource/script/jquery-1.10.2.min.js" type="text/javascript" ></script>

</head>
<body>
	<h1 id="time">${data }</h1>
	<!-- <a href="seckill/list">link</a> -->
	<!-- <script type="text/javascript">
		var h1 = document.getElementById("time");
		h1.onclick = function(){
			$.ajax({
			    url: "http://127.0.0.1:8080/secKill/seckill/index",
			    type: 'GET',
			    dataType: '',
			    data: {
			          
			    },
			    success: function(data){
			         alert("data:"+data);
			         h1.innerText = data;
			    },
			    error: function(){
			          alert("error");
			    }
			 })
		}
			
	</script> -->
</body>
</html>