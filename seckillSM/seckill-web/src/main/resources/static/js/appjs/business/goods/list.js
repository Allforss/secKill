var prefix = "/web/goods";
$(function () {
    load();
});
function formatMoney(num){
    var source = String(num).split(".");//按小数点分成2部分
    source[0] = source[0].replace(new RegExp('(\\d)(?=(\\d{3})+$)','ig'),"$1,");//只将整数部分进行都好分割
    return source.join(".");//再将小数部分合并进来
}

function load() {
    $('#exampleTable').bootstrapTable({
        height: getHeight(),
        method: 'post', // 服务器数据的请求方式 get or post
        url: prefix + "/list", // 服务器数据的加载地址
        contentType : "application/x-www-form-urlencoded",
        // search: true, // 是否显示搜索框
        // formatSearch: function () {
        //     return "搜索商品名";
        // },
        showRefresh: true,
        // showToggle: true,
        showColumns: false,
        showExport: false,
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
        pageSize: 9, // 如果设置了分页，每页数据条数
        pageList: [15, 30, 60, 100],
        pageNumber: 1, // 如果设置了分布，首页页码
        // "server"
        queryParams: function (params) {
            // console.log("params:offset=" + params.offset + ",limit=" + params.offset);
            return {
                // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                name: $('#name').val(),
                offset: params.offset,
                limit: params.limit
            };
        },
        responseHandler: function (resp) {
            return resp.data;
        },
        // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
        // queryParamsType = 'limit' ,返回参数必须包含
        // limit, offset, search, sort, order 否则, 需要包含:
        // pageSize, pageNumber, searchText, sortName,
        // sortOrder.
        // 返回false将会终止请求
        columns: [{
            field: 'goodsId', // 列字段名
            title: '商品id', // 列标题
            align: 'center',
        }, {
            field: 'name',
            title: '商品名',
            align: 'center',
        }, {
            field: 'number',
            title: '库存',
            align: 'center',
        },{
            field: 'price',
            title: '秒杀价格',
            align: 'center',
            formatter:function (value, row, index) {
                return formatMoney((parseFloat(value)/ 100).toFixed(2));
            }
        }, {
            field: 'description',
            title: '商品描述',
            align: 'center',
        }, {
            field: 'startTime',
            title: '秒杀开始时间',
            align: 'center',
        },{
            field: 'endTime',
            title: '秒杀结束时间',
            align: 'center',
        },{
            field: 'createTime',
            title: '创建时间',
            align: 'center',
        },{
            field: 'updateTime',
            title: '更新时间',
            align: 'center',
        },{
            field: 'goodsId',
            title: '操作',
            align: 'center',
            formatter: function (value, row, index) {
                var e = '<a  class="btn btn-primary btn-sm" href="#" mce_href="#" title="编辑" onclick="edit(\''
                    + row.goodsId
                    + '\')"><i class="fa fa-edit "></i></a> ';
                return e ;
            }
        }]
    });
}
function add() {
    // iframe层
    layer.open({
        type: 2,
        title: '增加商品',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '544px'],
        content: prefix + '/add' // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '商品修改',
        maxmin: true,
        shadeClose: false,
        area: ['800px', '580px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}


function searchByName() {
    var name = $('#name').val();
    console.log("name=" + name);
    reLoad();
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}
