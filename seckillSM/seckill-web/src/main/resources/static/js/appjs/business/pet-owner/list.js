var prefix = "/petOwner";
$(function () {
    load();
});

function load() {
    $('#exampleTable').bootstrapTable({
        height: getHeight(),
        method: 'get', // 服务器数据的请求方式 get or post
        url: prefix + "/list", // 服务器数据的加载地址
        // search: true, // 是否显示搜索框
        // formatSearch: function () {
        //     return "搜索名称";
        // },
        showRefresh: true,
        // showToggle: true,
        showColumns: true,
        showExport: true,
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
        iconSize: 'outline',
        toolbar: '#toolbar',
        striped: true, // 设置为true会有隔行变色效果
        dataType: "json", // 服务器返回的数据类型
        pagination: true, // 设置为true会在底部显示分页条
        // queryParamsType : "limit",
        // //设置为limit则会发送符合RESTFull格式的参数
        singleSelect: false, // 设置为true将禁止多选
        // contentType : "application/x-www-form-urlencoded",
        // //发送到服务器的数据编码类型
        // pageSize: 10, // 如果设置了分页，每页数据条数
        // pageNumber: 1, // 如果设置了分布，首页页码
        // "server"
        queryParams: function (params) {
            return {
                // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                limit: params.limit,
                offset: params.offset
            };
        },
        responseHandler: function (resp) {
            return resp.data;
        },
        columns: [{ // 列配置项
            // 数据类型，详细参数配置参见文档http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
            checkbox: true
            // 列表中显示复选框
        }, {
            field: 'id',
            title: '序号'
        }, {
            field: 'name', // 列字段名
            title: '姓名' // 列标题
        }, {
            field: 'sex',
            title: '性别',
            formatter: function (value, row, index) {
                switch (value) {
                    case false:
                        return "女";
                    case true:
                        return "男";
                    default:
                        return "未知";
                }
            }
        }, {
            field: 'age',
            title: '年龄'
        }, {
            field: 'address',
            title: '住址'
        }, {
            field: 'mail',
            title: '邮件'
        }, {
            field: 'identitycard',
            title: '身份证'
        }, {
            title: '操作',
            field: 'id',
            align: 'center',
            formatter: function (value, row, index) {
                var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                    + row.id
                    + '\')"><i class="fa fa-edit"></i></a> ';
                var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                    + row.id
                    + '\')"><i class="fa fa-remove"></i></a> ';
                return e + d;
            }
        }]
    });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function add() {
    // iframe层
    layer.open({
        type: 2,
        title: '添加新的宠物',
        maxmin: true,
        resize: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add' // iframe的url
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                console.log(r);
                if (r.data === true) {
                    layer.msg("删除成功");
                    reLoad();
                } else {
                    layer.msg(r.msg + "删除失败");
                }
            }
        });
    })
}

function batchRemove() {

    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
    }, function () {
        var ids = new Array();
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        // console.log(ids);
        $.ajax({
            type: 'POST',
            data: JSON.stringify(ids),
            contentType: "application/json",
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.data === true) {
                    layer.msg("删除成功");
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '信息修改',
        maxmin: true,
        resize: true,
        shadeClose: true, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}
