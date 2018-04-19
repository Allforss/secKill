$().ready(function () {
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        save();
    }
});

function getCheckedRoles() {
    var adIds = "";
    $("input:checkbox[name=role]:checked").each(function (i) {
        if (0 == i) {
            adIds = $(this).val();
        } else {
            adIds += ("," + $(this).val());
        }
    });
    return adIds;
}

function save() {
    $("#roleIds").val(getCheckedRoles());
    xy.doAjaxRequest("/sys/user/add", $('#signupForm').serialize(), {
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
            account: {
                required: true,
                minlength: 2,
                remote: {
                    url: "/sys/user/exit", // 后台处理程序
                    type: "GET", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        account: function () {
                            return $("#account").val();
                        }
                    }
                }
            },
            password: {
                required: true,
                minlength: 6
            },
            confirm_password: {
                required: true,
                minlength: 6,
                equalTo: "#password"
            },
            email: {
                required: true,
                email: true
            },
            role: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入姓名"
            },
            account: {
                required: icon + "请输入您的用户名",
                minlength: icon + "用户名必须两个字符以上",
                remote: icon + "用户名已经存在"
            },
            password: {
                required: icon + "请输入您的密码",
                minlength: icon + "密码必须6个字符以上"
            },
            confirm_password: {
                required: icon + "请再次输入密码",
                minlength: icon + "密码必须6个字符以上",
                equalTo: icon + "两次输入的密码不一致"
            },
            email: {
                required: icon + "请输入您的E-mail",
                email: icon + "请输入正确的E-mail"
            },
            role: {
                required: icon + "请至少选择一个角色"
            }
        },
        errorPlacement: function (error, element) { //指定错误信息位置
            if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
                var eid = element.attr('name'); //获取元素的name属性
                error.appendTo(element.parent()); //将错误信息添加当前元素的父结点后面
            } else {
                error.insertAfter(element);
            }
        }
    })
}
