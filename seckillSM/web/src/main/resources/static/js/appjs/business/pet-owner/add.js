var prefix = "/petOwner";
$(function () {
    validateRule();
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