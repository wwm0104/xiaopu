/**
 * Created by xiaohao on 2016/12/7.
 */

var pagination;
$(function(){
    var page=1;
    var row=10;

    $("#positionForm").validation({icon: true});
    //添加验证
    $("#add").on('click', function (event) {
        if ($("#positionForm").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            doAdd();
        }
    })

    initPersonnelList(page,row);        //初始化列表
    pagination = $('#personnelListPagination').bootpag({
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
        initPersonnelList(num,row);
    });

    $("#realName").bind('keydown', function (e) {      //回车模糊查询事件
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initPersonnelList(page,row);
        }
    });
});


var checkIndex = 2;
/**
 * 验证职位
 */
function checkPositionName() {
    var positionName = $("#positionName").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/adminAudio/checkPosition",
        contentType: 'application/json',
        data: JSON.stringify({positionName: positionName}),
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("职位已存在");
            }else{
                checkIndex = 1;
            }
        }
    });
}
/**
 * 获取管理员列表
 * @param page
 * @param row
 */
function initPersonnelList(page,row) {
    var positionName=$("#realName").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/adminAudio/getPositionList",
        contentType: 'application/json',
        data: JSON.stringify({page:page,row:row,positionName:positionName}),
        dataType: 'json',
        success: function (data) {
            $("#personnelList").empty();//清空列表
            var tdHtml = "";
            $.each(data.obj.list, function (i, position) {//循环绑定版本列表
                tdHtml += "<tr><td>" + position.id + "</td>";
                tdHtml += "<td>" + position.positionName + "</td>";
                tdHtml += "<td><button onclick='delcfm("+position.id+")'  type='button' class='btn delete-btn-bg'>删除</button></td>";
                tdHtml+="</tr>";
            });
            $('#personnelList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#personnelListPagination").hide();
                $('#personnelList').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
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
                $("#personnelListPagination").show();
            }
        }
    });

}
/**
 * 跳转至添加管理员界面
 */
function toAdd()
{
    $("#positionForm").validation({icon: false});
    $("#positionName").val("");
    $('#addPosition').modal();
}

function doAdd(){
    var positionName=$("#positionName").val();
    if(checkIndex==2){
        layer.msg("职位已存在");
    }else {
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/adminAudio/addPosition",
            contentType: 'application/json',
            data: JSON.stringify({positionName: positionName}),
            dataType: 'json',
            success: function (data) {
                if (data.resultCode == 1) {//如果data.resultcode为1成功，2失败
                    layer.msg("添加成功");
                } else {
                    layer.msg("添加失败");
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
        url: "/adminAudio/delPosition?id="+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            if(data.resultCode==1) {//如果data.resultcode为1成功，2失败
                layer.msg("删除成功");
            }else {
                layer.msg("删除失败");
            }
            location.replace(location);
        }
    });

}
