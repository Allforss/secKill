var prefix = "/petOwner";
$(function () {
    validateRule();
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
            name: {
                required: true
            },
            sex: {
                required: true
            },
            age: {
                required: true
            },
            address: {
                required: true
            },
            identitycard: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入姓名"
            },
            sex: {
                required: icon + "请选择性别"
            },
            age: {
                required: icon + "请输入年龄"
            },
            address: {
                required: icon + "请输入地址"
            },
            identitycard: {
                required: icon + "请输入身份证号"
            }
        }
    })
}