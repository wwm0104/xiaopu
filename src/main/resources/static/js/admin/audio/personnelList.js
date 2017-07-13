/**
 * Created by xiaohao on 2016/12/7.
 */

var pagination;
$(function(){
    var page=1;
    var row=10;

    $("#schoolList").select2({
        minimumResultsForSearch: Infinity
    });
    $("#positionList").select2({
        minimumResultsForSearch: Infinity
    });
    // $("#schoolList1").select2({
    //     minimumResultsForSearch: Infinity
    // });


    $("#personnelForm").validation({icon: true});
    //添加验证
    $("#add").on('click', function (event) {
        if ($("#personnelForm").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            doAdd();
        }
    })

    initPersonnelList(page,row);        //初始化列表
    getSchoolList();
    getPersonnelList();
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

    $("#realName1").bind('keydown', function (e) {      //回车模糊查询事件
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initPersonnelList(page,row);
        }
    });
});
/**
 * 获取管理员列表
 * @param page
 * @param row
 */
function initPersonnelList(page,row) {
    var realName=$("#realName1").val();
    var schoolId=$("#schoolList").val();
    var positionId=$("#positionList").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/adminAudio/getPersonnelList",
        contentType: 'application/json',
        data: JSON.stringify({page:page,row:row,realName:realName,schoolId:schoolId,positionId:positionId}),
        dataType: 'json',
        success: function (data) {
            $("#personnelList").empty();//清空列表
            var tdHtml = "";
            $.each(data.obj.list, function (i, p) {//循环绑定版本列表
                tdHtml += "<tr><td>" + p.userId + "</td>";
                tdHtml += "<td>" + p.realName + "</td>";
                tdHtml += "<td>" + p.nickName + "</td>";
                tdHtml += "<td>" + p.positionName + "</td>";
                tdHtml += "<td>" + p.mobile + "</td>";
                tdHtml += "<td>" + p.schoolName + "</td>";
                tdHtml += "<td><button onclick='delcfm("+p.userId+")'  type='button' class='btn delete-btn-bg'>删除</button></td>";
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
    $("#personnelForm").validation({icon: false});
    $("#realName").val("");
    $("#mobile").val("");
    $('#addPersonnel').modal();
}


var checkIndex = 2;

function checkUserInfo() {
    var realName = $("#realName").val();
    var mobile = $("#mobile").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/PrizeTake/checkUserInfo",
        contentType: 'application/json',
        data: JSON.stringify({realName: realName, mobile: mobile}),
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("该用户存在，可以添加");
                checkIndex = 1;
            }else{

                layer.msg("用户不存在");
            }
        }
    });
}

function doAdd(){
    var realName = $("#realName").val();
    var mobile = $("#mobile").val();
    var positionId=$("#positionList1").val();
    if(checkIndex==2){
        layer.msg("用户不存在");
    }else{
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/adminAudio/addPersonnel",
            contentType: 'application/json',
            data: JSON.stringify({positionId:positionId,realName:realName,mobile:mobile}),
            dataType: 'json',
            success: function(data){
                if(data.resultCode==1) {//如果data.resultcode为1成功，2失败
                    layer.msg("添加成功");
                }else {
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
        url: "/adminAudio/delPersonnel?id="+id,
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

/**
 * 加载校园列表
 */
function getSchoolList(){
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/school/list",
        contentType: 'application/json',
        data: "",
        dataType: 'json',
        success: function(data){
            $('#schoolList').empty();
            var tdHtml = "";
            tdHtml+="<option selected='selected' value='' >全部学校</option>";
            $.each(data.obj, function(i, school){
                tdHtml +="<option value="+school.id+">"+school.name+"</option>";
            });
            $('#schoolList').html(tdHtml);
        }
    });
}

/**
 * 获取职位列表
 */
function getPersonnelList(){
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/adminAudio/getPositionList",
        contentType: 'application/json',
        data: JSON.stringify({page:1,row:100}),
        dataType: 'json',
        success: function(data){

            $('#positionList').empty();
            var tdHtml = "";
            tdHtml+="<option selected='selected' value='' >全部职位</option>";
            $.each(data.obj.list, function(i, position){
                tdHtml +="<option value="+position.id+">"+position.positionName+"</option>";
            });
            $('#positionList').html(tdHtml);

            $('#positionList1').empty();
            var tdHtml = "";
            $.each(data.obj.list, function(i, position){
                tdHtml +="<option value="+position.id+">"+position.positionName+"</option>";
            });
            $('#positionList1').html(tdHtml);


        }
    });
}

