var prefix = "/petInfo";
$(function () {
    validateRule();
    $(".dial").knob({
        width: "85",
        height: "85"
    });
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
            name: {
                required: true
            },
            sex: {
                required: true
            },
            age: {
                required: true
            },
            weight: {
                required: true
            },
            health: {
                required: true
            },
            breed: {
                required: true
            },
            birthday: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入宠物名"
            },
            sex: {
                required: icon + "请选择性别"
            },
            age: {
                required: icon + "请输入年龄"
            },
            weight: {
                required: icon + "请输入体重"
            },
            health: {
                required: icon + "请输入健康程度"
            },
            breed: {
                required: icon + "请输入品种"
            },
            birthday: {
                required: icon + "请输入生日"
            }
        }
    })
}