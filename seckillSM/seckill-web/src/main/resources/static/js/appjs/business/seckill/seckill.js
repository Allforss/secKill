//存放主要交互逻辑
//JavaScript模块化
var seckill={
	//封装秒杀ajax的URL
	URL : {
		now: function(){
			return "/web/seckill/time/now";
		},
		exposer : function(goodsId){
			return "/web/seckill/exposer?goodsId=" + goodsId;
		},
		execution : function(goodsId,userId,md5){
			return "/web/seckill/execution?goodsId="
				+ goodsId + "&userId=" + userId + "&md5=" + md5;
		}
	},
	handleSeckill : function(goodsId,node){
		node.hide()
			.html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>')
		$.post(seckill.URL.exposer(goodsId),{},function(result){
			//再回调函数中，执行交互流程
			if(result && result['success']){
				var exposer = result['data'];
				if(exposer['exposed']){
					//开启秒杀
					//获取秒杀地址
					var md5 = exposer['md5'];
					var userId = exposer['userId'];
					var killUrl = seckill.URL.execution(goodsId, userId, md5);
					console.log("killUrl:"+killUrl);
					//绑定一次点击事件,防止用户重复点击
					$('#killBtn').one("click",function(){
						//先禁用按钮
						$(this).addClass('disabled');
						$.post(killUrl,{},function(result){
							console.log("result:"+result);
							if(result && result['success']){
								var killResult = result['data'];
								var state = killResult['state'];
								var stateInfo = killResult['stateInfo'];
								console.log("state:"+state);
								console.log("stateInfo:"+stateInfo);
								//显示秒杀结果
                                $('#seckill-icon').hide();
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
					seckill.countdown(goodsId,now,start,end);
				}
			}else{
				console.log("result:"+result);
			}
		});	
	},
	dateFormat: function (strDate) {
		//将CST标准格式时间转化为yyyy-MM-dd HH:mm:ss
        if(null==strDate || ""==strDate){
            return "";
        }
        var dateStr=strDate.trim().split(" ");
        var strGMT = dateStr[0]+" "+dateStr[1]+" "+dateStr[2]+" "+dateStr[5]+" "+dateStr[3]+" GMT+0800";
        var date = new Date(Date.parse(strGMT));
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        var minute = date.getMinutes();
        minute = minute < 10 ? ('0' + minute) : minute;
        var second = date.getSeconds();
        second = second < 10 ? ('0' + second) : second;
        return y+"-"+m+"-"+d+" "+h+":"+minute+":"+second;
    },
	countdown : function(goodsId,nowTime,startTime,endTime){
		console.log("countdown------")
		console.log("goodsId:"+goodsId);
		console.log("nowTime:"+nowTime);
		console.log("startTime:"+startTime);
		console.log("endTime:"+endTime);
		var seckillBox = $('#seckill-box');
		//事件的判断
		if(nowTime >= endTime){
            //秒杀结束
            console.log("秒杀已经结束！");
			seckillBox.html('秒杀已经结束！');
		}else if(nowTime < startTime){
            //秒杀未开始，计时事件绑定
            console.log("秒杀未开始，开始计时!")
			var killTime = new Date(startTime - 0 + 1000);
			//alert("killTime:"+killTime);
			seckillBox.countdown(killTime, function(event){
				//时间格式
				var format = event.strftime('秒杀倒计时 ：%D天 %H时 %M分 %S秒');
				seckillBox.html(format);
			}).on("finish.countdown",function(){
				//获取秒杀地址，控制显示逻辑，执行秒杀
				seckill.handleSeckill(goodsId,seckillBox);
			});
		}else{
			//秒杀中
            console.log("秒杀进行中！")
            $('#seckill-icon').hide();
			seckill.handleSeckill(goodsId,seckillBox);
		}
	},
	//详情页秒杀逻辑
	detail : {
		//详情页初始化
		init : function(params) {
			//手机验证和登录，计时交互
			//规划交互流程
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var goodsId = params['goodsId'];
            console.log("startTime:"+ startTime);
            console.log("endTime:"+ endTime);

            startTime = seckill.dateFormat(startTime);
            endTime = seckill.dateFormat(endTime);
            console.log("startTime:"+ startTime);
            console.log("endTime:"+ endTime);

            startTime = new Date(startTime).getTime();
            endTime = new Date(endTime).getTime();
            console.log("startTime:"+ startTime);
            console.log("endTime:"+ endTime);

			//计时交互
			$.get(seckill.URL.now(), function(result){
				console.log("result=" + result['success']);
				if(result && result['success']){
					var nowTime = result['data'];
					//事件判断，计时交互
					seckill.countdown(goodsId,nowTime,startTime,endTime);
				}else{
					console.log("result:"+result);
				}
			});
		}
		
	}
		
}