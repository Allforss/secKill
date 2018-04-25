var prefix = "/sys/menu";
$(function () {
    validateRule();

    //打开图标列表
    $("#ico-btn").click(function () {
        var $icon = $("#icon");
        parent.layer.open({
            type: 2,
            title: '图标列表',
            content: '/FontIcoList.html',
            area: ['480px', '90%'],
            success: function (layero, index) {
                // console.log(parent.layer.getChildFrame('.ico-list', index));
                parent.layer.getChildFrame('.ico-list i', index).click(function () {
                    $icon.val($(this).attr('class'));
                    parent.layer.close(index);
                });
            }
        });
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
            type: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入菜单名"
            },
            type: {
                required: icon + "请选择菜单类型"
            }
        }
    })
}