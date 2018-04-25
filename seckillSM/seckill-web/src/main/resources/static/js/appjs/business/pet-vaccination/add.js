var prefix = "/petVaccinate";
$(function () {
    validateRule();
    $(".input-group.date").datepicker({
        format: "yyyy-mm-dd",
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        calendarWeeks: true,
        autoclose: true
    });
});
$.validator.setDefaults({
    submitHandler: function () {
        submit01();
    }
});

function submit01() {

    xy.doAjaxRequest(prefix + "/add", $('#signupForm').serialize(), {
        cache: true,
        type: "POST"
    }).done(function () {
        xy.infox("success!");
        window.parent.location.reload();
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            type: {
                required: true
            },
            petid: {
                required: true
            },
            status: {
                required: true
            },
            createtime: {
                required: true
            }
        },
        messages: {
            type: {
                required: icon + "请输入疫苗类型"
            },
            petid: {
                required: icon + "请输入宠物id"
            },
            status: {
                required: icon + "请输入接种情况"
            },
            createtime: {
                required: icon + "请输入接种时间"
            }
        }
    })
}