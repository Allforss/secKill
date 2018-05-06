var prefix = "/web/order";
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
        url: prefix + "/queryList", // 服务器数据的加载地址
        contentType : "application/x-www-form-urlencoded",
        // search: true, // 是否显示搜索框
        // formatSearch: function () {
        //     return "搜索商品名";
        // },
        showRefresh: false,
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
            field: 'orderId', // 列字段名
            title: '订单id', // 列标题
            align: 'center',
        }, {
            field: 'goods.name',
            title: '商品名',
            align: 'center',
        }, {
            field: 'goods.price',
            title: '价格（￥）',
            align: 'center',
            formatter:function (value, row, index) {
                return formatMoney((parseFloat(value)/ 100).toFixed(2));
            }
        },{
            field: 'user.userId',
            title: '用户id',
            align: 'center',
        }, {
            field: 'user.name',
            title: '用户姓名',
            align: 'center',
        }, {
            field: 'user.userPhone',
            title: '联系电话',
            align: 'center',
        }, {
            field: 'user.address',
            title: '收货地址',
            align: 'center',
        }, {
            field: 'orderState',
            title: '订单状态',
            align: 'center',
            formatter:function (value, row, index) {
                if(0==value){
                    return "下单失败";
                } else if(1 == value){
                    return "下单成功";
                } else{
                    return "已发货";
                }
            }
        },{
            field: 'createTime',
            title: '下单时间',
            align: 'center',
        },{
            field: 'orderId',
            title: '操作',
            align: 'center',
            formatter: function (value, row, index) {
                var orderState = row.orderState;
                var orderId = row.orderId;
                console.log("orderId=" + orderId);
                var e = "";
                if(2==orderState){
                    e = '<a class="btn btn-info" th:href="#">已发货</a>';
                } else if(1 == orderState){
                    e = '<a class="btn btn-info" th:href="#" onclick="changeState(\'' +
                        orderId + '\')">发货</a>';
                }
                return e ;
            }
        }]
    });
}

function changeState(orderId){
    console.log("changeState orderId=" + orderId);
    var url = "/web/order/update";
    var params = {"orderId":orderId,"orderState":2};
    $.post(url,params,function (result) {
        if(result && result.state == 1){
            alert("发货成功！");
            reLoad();
        } else {
            alert("发货失败，请稍后重试！");
        }
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
