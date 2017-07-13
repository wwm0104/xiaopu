/**
 * Created by flint
 * date: 24/10/2016
 */
var pagination;
$(function () {
    var page = 1;
    var row = 10;
    $("#update").hide();
    $("#remark1").hide();
    $("#timePoint").select2({      //下拉框样式
        minimumResultsForSearch: Infinity
    });
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
    var groupId=$("#groupId").val();
    var timePoint=$("#timePoint").val();
    var subject=$("#seachText").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partnerManage/groupDetailsByGroup",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row, organizeId:groupId,subject:subject,timePoint:timePoint}),
        dataType: 'json',
        success: function (data) {
            $('#partnerList').empty();
            var tdHtml = "";
            var statuName = "";
            var myDate = new Date();
            var count=0;
            $.each(data.obj.list, function (i, partner) {

                    tdHtml += "<tr>";
                    tdHtml += "<td>" + partner.subject + "</td>";
                    tdHtml += "<td>" + partner.joinCnt + "</td>";
                    tdHtml += "<td>无</td>";
                    if(partner.timeStatus==1){
                        statuName="未开始";
                    }else if(partner.timeStatus==2){
                        statuName="进行中";
                    }else if(partner.timeStatus==3){
                        statuName="已结束";
                    }
                    tdHtml += "<td>"+statuName+"</td>";
                    tdHtml += "<td>" + partner.startTime+'-'+partner.endTime+ "</td>";
                    tdHtml += "</tr>";
                count++;

            });
            $("#count").text(count);
            $('#partnerList').html(tdHtml);
            if (data.obj.pages == 0 || data.obj.list.length == 0) {
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

function doGroupExcel(){
    var groupId=$("#groupId").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partnerManage/ExcelGroup/"+groupId,
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






