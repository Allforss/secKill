var prefix = "/web/goods";
var now = new Date();
$("#startTime").datetimepicker({
    minView: "month",
    language:  'zh',
    format: 'Y-m-d H:i',
    dateFormat:'Y-m-d',
    timeFormat:'H:i',
    closeOnDateSelect: true,
    todayBtn: true,
    pickerPosition: "bottom-left"
}).on('changeDate', function (ev) {
    console.log("ev:" + ev);
    if (ev.date) {
        $(endSelector).datepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $(endSelector).datepicker('setStartDate', null);
    }
});


$("#endTime").datetimepicker({
    minView: "month",
    language: 'zh',
    format: 'Y-m-d H:i',
    dateFormat:'Y-m-d',
    timeFormat:'H:i',
    closeOnDateSelect: true,
    todayBtn: true,
    pickerPosition: "bottom-left"
}).on('changeDate', function (ev) {
    console.log("ev:" + ev);
    if (ev.date) {
        $('#startDate').datepicker('setEndDate', new Date(ev.date.valueOf()))
    } else {
        $('#startDate').datepicker('setEndDate', new Date());
    }
});