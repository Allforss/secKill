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
        update();
    }
});

function update() {
    xy.doAjaxRequest(prefix + "/edit", $('#signupForm').serialize(), {
        cache: true,
        type: "POST"
    }).done(function () {
        xy.succx("保存成功");
        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
        parent.reLoad();
        parent.layer.close(index);
    });
}

function validate() {
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