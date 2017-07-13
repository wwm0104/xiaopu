
/**
 * Created by xh on 2016/11/12.
 */
var pagination;
$(function() {
    var page=1;
    var row=10;
    getSchoolList();
    getVrList(page,row);

    $("#schoolList").select2({
        minimumResultsForSearch: Infinity
    });


    $("#seachBtn").on("click",function(){      //点击模糊查询
        getVrList(1,10);
    });

    $("#seachText").bind('keydown', function (e) {      //回车模糊查询事件
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            getVrList(page,row);
        }
    });
    pagination = $('#vrListPagination').bootpag({
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
        getVrList(num,row);
    });
});


function getVrList(page,row){
    var schoolId=$("#schoolList").val();
    var realName=$("#seachText").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/getVrList",
        contentType: 'application/json',
        data: JSON.stringify({page:page,row:row,schoolId:schoolId,realName:realName}),
        dataType: 'json',
        success: function(data){
            $('#vrList').empty();
            var tdHtml = "";
            $.each(data.obj.list, function(i, vr) {
                tdHtml += '<tr>';
                tdHtml += "<td>" + (vr.realName || "") + "</td>";
                tdHtml += "<td>" + (vr.mobile||"") + "</td>";
                tdHtml += "<td>" + (vr.schoolName||"") + "</td>";
                tdHtml += "<td>" + (vr.studentNo||"") + "</td>";
                tdHtml += "<td>" + vr.appointmentDate + "/" + vr.appointmentTime + "</td>";
                tdHtml += "<td>" + (vr.activityCnt||0) + "</td>";
            });
            $('#vrList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#vrListPagination").empty();
                $("#export").hide();
                $('#vrList').html("<tr><td colspan='6' style='text-align: center'>暂无数据</td></tr>");
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
                $("#export").show();
            }
        }
    })
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







