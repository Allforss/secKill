<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>秒杀详情页</title>
	<%@include file="common/head.jsp"%>
</head>
<body>
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading">${seckill.name}</div>
		</div>
		<div class="panel-body">
			<h2 class="text-danger">
				<!-- 显示图标  -->
				<span class="glyphicon glyphicon-time"></span>
				<!-- 展示倒计时 -->
				<span class="glyphicon" id="seckill-box"></span>
			</h2>
		</div>
	</div>
	<!-- 登录弹出层，输入电话 -->
	<div id="killPhoneModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphion-phone"></span>
					</h3>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" name="killPhone" id="killPhoneKey"
								placeholder="填手机号" class="form-control">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<!-- 验证信息 -->
					<span id="killPhoneMessage" class="glyphicon"></span>
					<button type="button" id="killPhoneBtn" class="btn btn-success">
						<span class="glyphicon glyphicon-phone"></span>
						Submit
					</button>
				</div>
			</div>
		</div>
	</div>
	

</body>
<!-- jquery文件，务必在bootstrap.min.js之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js" type="text/javascript" ></script>
<!-- 最新的Bootstrap核心JQuery文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js" type="text/javascript" ></script>

<!-- 使用CDN获取公共JS http://www.bootcdn.cn -->
<!-- jQuery cookie操作插件 -->
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!-- jQuery coutdown倒计时插件 -->
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
<script src="/secKill/resource/script/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		//使用EL表达式传入参数
		seckill.detail.init({
			seckillId : "${seckill.seckillId }",
			startTime : "${seckill.startTime.time}",
			endTime : "${seckill.endTime.time }",
		});
	});
</script>


</html>