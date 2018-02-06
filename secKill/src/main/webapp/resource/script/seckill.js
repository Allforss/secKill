//存放主要交互逻辑
//JavaScript模块化
var seckill={
	//封装秒杀ajax的URL
	URL : {
		now: function(){
			return "/secKill/seckill/time/now";
		},
		exposer : function(seckillId){
			return "/secKill/seckill/"+seckillId+"/exposer";
		},
		execution : function(seckillId, md5){
			return "/secKill/seckill/" +seckillId+ "/" + md5 + "/execution";
		}
	},	
	validatePhone : function(phone){
		if(phone && phone.length == 11 && !isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	handleSeckill : function(seckillId,node){
		node.hide()
			.html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>')
		$.post(seckill.URL.exposer(seckillId),{},function(result){
			//再回调函数中，执行交互流程
			if(result && result['success']){
				var exposer = result['data'];
				if(exposer['exposed']){
					//开启秒杀
					//获取秒杀地址
					var md5 = exposer['md5']
					var killUrl = seckill.URL.execution(seckillId, md5);
					console.log("killUrl:"+killUrl);
					//绑定一次点击事件,防止用户重复点击
					$('#killBtn').one("click",function(){
						//先禁用按钮
						$(this).addClass('dissabled');
						$.post(killUrl,{},function(result){
							console.log("result:"+result);
							if(result && result['success']){
								var killResult = result['data'];
								var state = killResult['state'];
								var stateInfo = killResult['stateInfo'];
								console.log("state:"+state);
								console.log("stateInfo:"+stateInfo);
								//显示秒杀结果
								node.html('<span class="label label-success">'+ stateInfo + '</span>')
							}
						});
					});
					node.show();
				}else{
					//未开启秒杀，用户时间有偏差
					var now = exposer['now'];
					var start = exposer['start'];
					var end = exposer['end'];
					seckill.countdown(seckillId,now,start,end);
				}
			}else{
				console.log("result:"+result);
			}
		});	
	},
	countdown : function(seckillId,nowTime,startTime,endTime){
		console.log("seckillId:"+seckillId);
		console.log("nowTime:"+nowTime);
		console.log("startTime:"+startTime);
		console.log("endTime:"+endTime);
		var seckillBox = $('#seckill-box');
		//事件的判断
		if(nowTime > endTime){
			//秒杀结束
			seckillBox.html('秒杀结束！');
		}else if(nowTime < startTime){
			//秒杀未开始，计时事件绑定
			var killTime = new Date(startTime-0 + 1000);
			//alert("killTime:"+killTime);
			seckillBox.countdown(killTime, function(event){
				//时间格式
				var format = event.strftime('秒杀倒计时 ：%D天 %H时 %M分 %S秒');
				seckillBox.html(format);
			}).on("finish.countdown",function(){
				//获取秒杀地址，控制显示逻辑，执行秒杀
				seckill.handleSeckill(seckillId,seckillBox);
			});
		}else{
			//秒杀中
			seckill.handleSeckill(seckillId,seckillBox);
		}
	},
	//详情页秒杀逻辑
	detail : {
		//详情页初始化
		init : function(params) {
			//手机验证和登录，计时交互
			//规划交互流程
			var userPhone = $.cookie('userPhone');
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			console.log("startTime:"+startTime);
			console.log("endTime:"+endTime);
			console.log("userphone:"+userPhone);
			//验证手机号
			if(!seckill.validatePhone(userPhone)){
				//绑定phone
				//控制输出
				var killPhoneModal = $('#killPhoneModal');
				killPhoneModal.modal({
					//显示弹出层
					show:true,
					backdrop:false, //禁止位置关闭
					keyboard:false //关闭键盘事件
				});
				$('#killPhoneBtn').click(function(){
					var inputPhone = $('#killPhoneKey').val();
					console.log("inputPhone:"+inputPhone);
					if(seckill.validatePhone(inputPhone)){
						$.cookie('userPhone',inputPhone,{expires : 7, path :'/secKill' });
						window.location.reload();
					}else{
						$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
					}
				});
			}else{
				//已经登录
				//计时交互
				$.get(seckill.URL.now(), function(result){
					if(result && result['success']){
						var nowTime = result['data'];
						//事件判断，计时交互
						seckill.countdown(seckillId,nowTime,startTime,endTime);
					}else{
						console.log("result:"+result);
					}
					
				});
			}
			
		}
		
	}
		
}