/**
 * Created by flint
 * date: 24/10/2016
 */
var pagination;
$(function () {

    var page = 1;
    var row = 10;
    $("#add").hide();
    $("#update").hide();
    $("#remark1").hide();
    $("#code").select2({      //下拉框样式
        minimumResultsForSearch: Infinity
    });
    $("#joinTime").select2({      //下拉框样式
        minimumResultsForSearch: Infinity
    });
    $("#schoolList").select2({      //下拉框样式
        minimumResultsForSearch: Infinity
    });

    //    星星
    var astar = $('#star').find('span');
    //清除所有
    function clear(){
        for(var i = 0; i < astar.length; i++){
            astar[i].style.color = '#ccc';
        }
    }
    current();
    //显示当前第几颗星
    function current(){
        var temp=$("#starRating").text();
        for(var i = 0; i < temp; i++){
            astar[i].style.color = '#ffe952';
        }
    }

    getSchoolList(); //获取校园列表
    initPartnerData(page,row);
    $("#seachBtn").on("click",function(){      //点击模糊查询
        initPartnerData(1,10);
    });

    $("#seachText").bind('keydown', function (e) {      //回车模糊查询事件
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initPartnerData(page,row);
        }
    });




    pagination = $('#partnerListPagination').bootpag({
        total: 0,          // total pages
        page: 1,            // default page
        maxVisible: 10,     // visible pagination
        firstLastUse: true,
        prev: '上一页',
        next: '下一页',
        first: '首页',
        last: '末页',
        leaps: true         // next/prev leaps through maxVisible
    }).on("page", function (event, num) {
        initPartnerData(num, row);
    });
});


//分页+查询（模糊姓名）
function initPartnerData(page, row) {
    var partnerid=$("#id").val();
    var realName=$("#seachText").val();
    var schoolId=$("#schoolList").val();
    var code=$("#code").val();
    var jointime=$("#jointime").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partnerManage/schoolDetailsByPartner",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row, partnerid:partnerid,realName:realName,schoolId:schoolId,code:code,joinTime:jointime}),
        dataType: 'json',
        success: function (data) {
            $('#partnerList').empty();
            var tdHtml = "";
            var count=0;
            $.each(data.obj.list, function (i, user) {

                tdHtml += "<tr>";
                tdHtml += "<td>" + user.code + "</td>";
                tdHtml += "<td>" + user.realName + "</td>";
                tdHtml += "<td>" + user.studentNo + "</td>";
                tdHtml += "<td>" + user.mobile + "</td>";
                tdHtml += "<td>" + user.qq + "</td>";
                tdHtml += "<td>" + user.schoolName + "</td>";
                tdHtml += "<td>" + user.joinTime+ "</td>";
                tdHtml += "</tr>";
                count++;

            });
            $("#count").text(count);
            $('#partnerList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#partnerListPagination").hide();
                $('#partnerList').html("<tr><td colspan='10' style='text-align: center'>暂无数据</td></tr>");
                $("#export").hide();
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
                $("#partnerListPagination").show();
                $("#export").show();
            }
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


function getCode(){
    $("#add").show();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partnerManage/code",
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            $("#code1").val(data.msg);
        }
    });
}
function addCode(){
    var code=$("#code1").val();
    var partnerId=$("#id").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partnerManage/addCode",
        contentType: 'application/json',
        data: JSON.stringify({code:code,partnerId:partnerId}),
        dataType: 'json',
        success: function (data) {
            if(data.resultCode==1) {//如果data.resultcode为1成功，0失败
                layer.msg("添加成功");
            }else {
                layer.msg("添加失败");
            }
            location.replace(location);
        }
    });

}

function toUpdateRemark(){
    $("#remark").hide();
    $("#remarkUpdate").val("");
    $("#remark1").show();
    var remark=$(".remark").text();
    $("#remarkUpdate").val(remark);
    $("#toupdate").hide();
    $("#update").show();
}
function doUpdateRemark(){
    var remarks=$("#remarkUpdate").val();
    var id=$("#id").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partnerManage/updateRemarks",
        contentType: 'application/json',
        data: JSON.stringify({remarks:remarks,id:id}),
        dataType: 'json',
        success: function (data) {
            if(data.resultCode==1) {//如果data.resultcode为1成功，0失败
                layer.msg("修改成功");
            }else {
                layer.msg("修改失败");
            }
            location.replace(location);
        }
    });
}

function doSchoolExcel(){
    var id=$("#id").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partnerManage/ExcelSchool/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            alert(data);
            // if(data.resultCode==1) {//如果data.resultcode为1成功，0失败
            //     layer.msg("修改成功");
            // }else {
            //     layer.msg("修改失败");
            // }
            // location.replace(location);
        }
    });

}





