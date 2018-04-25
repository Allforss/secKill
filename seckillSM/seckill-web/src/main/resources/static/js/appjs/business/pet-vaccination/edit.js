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
        update();
    }
});

function update() {
    var role = $('#signupForm').serialize();
    $.ajax({
        cache: true,
        type: "POST",
        url: prefix+"/update",
        data: role, // 你的formid
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success: function (r) {
            if (r.data === true) {
                parent.layer.msg("更改成功");
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);

            } else {
                parent.layer.msg(r.msg);
            }

        }
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