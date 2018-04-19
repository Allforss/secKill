//var menuTree;

var menuIds;
$(function () {
    getMenuTreeData();
    validateRule();
});
$.validator.setDefaults({
    submitHandler: function () {
        getAllSelectNodes();
        save();
    }
});

function getAllSelectNodes() {
    var ref = $('#menuTree').jstree(true); // 获得整个树
    menuIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组
    $("#menuTree").find(".jstree-undetermined").each(function (i, element) {
        menuIds.push($(element).closest('.jstree-node').attr("id"));
    });
}

function getMenuTreeData() {
    xy.doAjaxRequest("/sys/menu/tree").done(function (resp) {
        loadMenuTree(resp.data);
    });
}

function loadMenuTree(menuTree) {
    $('#menuTree').jstree({
        'core': {
            'data': menuTree
        },
        "checkbox": {
            "three_state": true
        },
        "plugins": ["wholerow", "checkbox"]
    });
    //$('#menuTree').jstree("open_all");
}

function save() {
    $('#menuIds').val(menuIds);
    var role = $('#signupForm').serialize();
    xy.doAjaxRequest("/sys/role/add", role, {
        cache: true,
        type: "POST",
        async: false
    }).done(function(){
        xy.infox("success!");
        window.parent.location.reload();
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            roleName: {
                required: true
            }
        },
        messages: {
            roleName: {
                required: icon + "请输入角色名"
            }
        }
    });
}