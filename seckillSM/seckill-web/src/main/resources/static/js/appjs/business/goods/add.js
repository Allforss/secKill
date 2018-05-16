var prefix = "/web/goods";
var now = new Date();
$("#startTime").datetimepicker({
    minView: "month",
    language:  'zh',
    format: 'Y-m-d H:i:s',
    dateFormat:'Y-m-d',
    timeFormat:'H:i:s',
    closeOnDateSelect: false,
    closeOnTimeSelect: true,
    todayBtn: true,
    // startDate:true,
    pickerPosition: "bottom-left",
});

$("#endTime").datetimepicker({
    minView: "month",
    language: 'zh',
    format: 'Y-m-d H:i:s',
    dateFormat:'Y-m-d',
    timeFormat:'H:i:s',
    closeOnDateSelect: false,
    closeOnTimeSelect: true,
    todayBtn: true,
    // startDate:true,
    pickerPosition: "bottom-left",
});

function addOnSubmit() {
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    console.log("startTime=" + startTime);
    console.log("endTime=" + endTime);
    if(endTime <= startTime){
        alert("秒杀开始时间不能大于结束时间！");
        return;
    }
    var params = $('#addForm').serialize().replace(/\+/g," ");
    console.log("params=" + params.toString());
    $.ajax({
        //几个参数需要注意一下
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        contentType : "application/x-www-form-urlencoded",
        url: prefix + "/insert" ,//url
        data: params,
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.state == 1) {
                // alert("SUCCESS");
                xy.succx("添加成功！");
            }
        },
        error : function(error) {
            // alert("异常！");
            console.log("error" + error.toString());
            xy.errorx("添加失败！");
        }
    });
}
