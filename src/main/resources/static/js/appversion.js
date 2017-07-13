/**
 * Created by Administrator on 2016/10/24.
 */
var pagination;
$(function() {
    var page=1;
    var row=10;

    //修改下拉框样式
    $("#AppId_list").select2({
        minimumResultsForSearch: Infinity
    });

    initAppVersion(page,row);

    $("#description1").bind('keydown', function (e) {      //回车模糊查询事件
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initAppVersion(page,row);
        }
    });

    pagination = $('#versionListPagination').bootpag({
        total: 0,          // total pages
        page: 1,            // default page
        maxVisible: 10,     // visible pagination
        firstLastUse: true,
        prev: '上一页',
        next: '下一页',
        first: '首页',
        last: '末页',
        leaps: true         // next/prev leaps through maxVisible
    }).on("page", function(event, num){
        initAppVersion(num,row);
    });

    $(function () {
        $("#versionForm").validation({icon: true});
        //添加验证
        $("#add").on('click', function (event) {
            if ($("#versionForm").valid(this) == false) {
                return false;
            } else {
                /**************数据处理*************/
                doAdd();
            }
        })
        //修改验证
        $("#update").on('click', function (event) {
            if ($("#versionForm").valid(this) == false) {
                return false;
            } else {
                /**************数据处理*************/
                doUpdate();
            }
        })
    });

});
/**
 * 获取版本列表
 */
function initAppVersion(page,row) {
    var appId=$("#AppId_list").val();
    var description=$("#description1").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/appVersion/list",
        contentType: 'application/json',
        data: JSON.stringify({page:page,row:row,description:description,appId:appId}),
        dataType: 'json',
        success: function (data) {
            $("#versionList").empty();//清空列表
            var tdHtml = "";
            $.each(data.obj.list, function (i, version) {//循环绑定版本列表
                tdHtml += "<tr><td>" + (version.appName||"") + "</td>";
                tdHtml += "<td>" + (version.verison||"") + "</td>";
                tdHtml += "<td>" + (version.useragent||"") + "</td>";
                tdHtml += "<td>" + (version.useragentName||"") + "</td>";
                tdHtml += "<td>" + (version.downloadUrl||"") + "</td>";
                tdHtml += "<td>" + (version.description||"") + "</td>";
                if(version.isUpdate==1)
                {
                    tdHtml += "<td>是</td>";
                }
                if(version.isUpdate==0)
                {
                    tdHtml += "<td>否</td>";
                }

                tdHtml += "<td><button style='margin-right: 2px;' onclick='update("+version.id+")' type='button' class='btn true-color'>修改</button><button onclick='delcfm("+version.id+")'  type='button' class='btn delete-btn-bg'>删除</button></td></tr>";
            });
            $('#versionList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#versionListPagination").hide();
                $('#versionList').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
            } else {
                pagination.bootpag({
                    total: data.obj.pages,          // total pages
                    page: data.obj.pageNum,            // default page
                    maxVisible: 10,     // visible pagination
                    firstLastUse: true,
                    prev: '上一页',
                    next: '下一页',
                    first: '首页',
                    last: '末页',
                    leaps: true         // next/prev leaps through maxVisible
                });
                $("#versionListPagination").show();
            }
        }
    });

}
/**
 * 点击添加弹出添加框
 */
function add()
{
    $("#versionForm").validation({icon: false});
    //设定值为空
    $("#id").val("");
    $("#appId option:first").prop("selected", 'selected');
    $("#useragent").val("");
    $("#useragentName").val("");
    $("#isUpdate option:first").prop("selected", 'selected');
    $("#version").val("");
    $("#downlodUrl").val("");
    $("#description").val("");
    $('#selectGroup').modal("show");
    $("#myModalLabel").html("添加");
    $("#add").show();
    $("#update").hide();
}
/**
 * 添加操作
 */
function doAdd()
{
    var appId=$("#appId").val();//获取当前选中平台id
    var useragent=$("#useragent").val();//获取当前选中代理id
    var useragentName=$("#useragentName").val();//获取当前选中代理名称
    var isUpdate=$("#isUpdate").val();//获取当前选中更新状态
    var version=$("#version").val();//获取版本号
    var downlodUrl=$("#downlodUrl").val();//获取下载地址
    var description=$("#description").val();//获取更新详情
    if(appId=="-1"||useragent=="-1"||isUpdate=="-1") {
        layer.msg("请选择！");
    }else {
        //执行添加ajax
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/admin/appVersion/add",
            contentType: 'application/json',
            data: JSON.stringify({appId:appId,useragent:useragent,useragentName:useragentName,verison:version,isUpdate:isUpdate,downloadUrl:downlodUrl,description:description}),
            dataType: 'json',
            success: function(data){
                if(data.resultcode==1) {////如果data.resultcode为1成功，2失败
                    layer.msg(data.msg);
                }else {
                    layer.msg(data.msg);
                }
                location.replace(location);
            }
        });
    }


}
/**
 * 点击修改弹出修改框，并获取修改信息
 * @param id
 */
function update(id)
{
    $("#versionForm").validation({icon: false});

    $('#selectGroup').modal("show");
    $("#myModalLabel").html("修改");
    $("#add").hide();
    $("#update").show();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/appVersion/getById?id="+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            $("#appId option[value='"+data.appId+"']").prop("selected","selected");//平台id
            $("#isUpdate option[value='"+data.isUpdate+"']").prop("selected","selected");//是否强制更新
            $("#useragent").val(data.useragent);//代理点
            $("#useragentName").val(data.useragentName);//版本id
            $("#version").val(data.verison);//版本id
            $("#downlodUrl").val(data.downloadUrl);//下载地址
            $("#description").val(data.description)//更新详情
            $("#id").val(data.id);
        }
    });

}
/**
 * 修改操作
 * @param id
 */
function doUpdate()
{
    var id= $("#id").val();//获取主键id
    var appId=$("#appId option:selected").val();//获取当前选中平台id
    var useragent=$("#useragent").val();
    var useragentName=$("#useragentName").val();
    var isUpdate=$("#isUpdate").val();//获取更新操作
    var version=$("#version").val();//获取版本号
    var downlodUrl=$("#downlodUrl").val();//获取下载地址
    var description=$("#description").val();//获取更新详情
    if(appId=="-1"||useragent=="-1"||isUpdate=="-1") {
        layer.msg("请选择！");
    }else {
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/admin/appVersion/update",
            contentType: 'application/json',
            data: JSON.stringify({
                id: id,
                appId: appId,
                useragent: useragent,
                useragentName: useragentName,
                verison: version,
                isUpdate: isUpdate,
                downloadUrl: downlodUrl,
                description: description
            }),
            dataType: 'json',
            success: function (data) {
                if (data.resultcode == 1) {//如果data.resultcode为1成功，2失败
                    layer.msg(data.msg);
                } else {
                    layer.msg(data.msg);
                }
                location.replace(location);
            }
        });
    }
}
/**
 * 删除模态窗（delid：删除id）
 * @param id
 */
function delcfm(id) {
    $('#delid').val(id);//注入delid
    $('#delcfmModel').modal();
}

/**
 * 删除操作
 */
function urlSubmit(){
    var id=$.trim($("#delid").val());//获取会话中的隐藏属性delid
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/appVersion/del?id="+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            if(data.resultcode==1) {//如果data.resultcode为1成功，2失败
                layer.msg(data.msg);
            }else {
                layer.msg(data.msg);
            }
            location.replace(location);
        }
    });

}