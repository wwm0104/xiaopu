/**
 * Created by xiaohao on 2016/10/28.
 */

var pagination;
$(function(){
    var page=1;
    var row=10;
    initAdminList(page,row);        //初始化获取管理员列表
    pagination = $('#adminListPagination').bootpag({
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
        initAdminList(num,row);
    });

    $("#realName").bind('keydown', function (e) {      //回车模糊查询事件
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initAdminList(page,row);
        }
    });
});
/**
 * 获取管理员列表
 * @param page
 * @param row
 */
function initAdminList(page,row) {
    var realName=$("#realName").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/AdminUserInfo/list",
        contentType: 'application/json',
        data: JSON.stringify({page:page,row:row,realName:realName}),
        dataType: 'json',
        success: function (data) {
            $("#adminList").empty();//清空列表
            var tdHtml = "";
            $.each(data.obj.list, function (i, userinfo) {//循环绑定版本列表
                tdHtml += "<tr><td>" + userinfo.realName + "</td>";
                tdHtml += "<td>" + userinfo.mobile + "</td>";
                tdHtml += "<td>" + userinfo.studentNo + "</td>";
                tdHtml += "<td>" + userinfo.qq + "</td>";
                // <button style='margin-right: 2px;background-color: #3c8dbc;' onclick='update("+userinfo.userId+")' type='button' class='btn btn-success'>修改</button>
                tdHtml += "<td><button onclick='delcfm("+userinfo.userId+")'  type='button' class='btn delete-btn-bg'>删除</button></td>";
                tdHtml+="</tr>";
            });
            $('#adminList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#adminListPagination").hide();
                $('#adminList').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
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
                $("#adminListPagination").show();
            }
        }
    });

}
/**
 * 跳转至添加管理员界面
 */
function toAdd()
{
    window.location.href="toAdd";
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
        url: "/admin/AdminUserInfo/del?id="+id,
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
