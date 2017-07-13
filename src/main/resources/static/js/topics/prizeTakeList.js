var pagination;
$(function () {
    var page = 1;
    var row = 10;
    initPrizeTakeData(1, 10);
    $("#topicType").select2({
        minimumResultsForSearch: Infinity
    });

    $("#hasTake").select2({
        minimumResultsForSearch: Infinity
    });
    $("#seachBtn").on("click",function(){      //点击模糊查询
        initPrizeTakeData(1,10);
    });

    $("#seachText").bind('keydown', function (e) {      //回车模糊查询事件
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initPrizeTakeData(page,row);
        }
    });
    pagination = $('#Pagination').bootpag({
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
        initPrizeTakeData(num, row);
    });


});
function initPrizeTakeData(page, row) {
    var hasTake = $("#hasTake").val();
    var topicType = $("#topicType").val();
    var takeTime = $("#takeTime").val();
    var time = $("#endTime").val();
    var userId=$("#userId").val();
    var realName=$("#seachText").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/PrizeTake/getPrizeTakeList",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row, hasTake: hasTake, topicType: topicType,takeTime:takeTime,time:time,userId:userId,realName:realName}),
        dataType: 'json',
        success: function (data) {
            $('#prizeTakeList').empty();
            var tdHtml = "";
            var topicType = "";
            var hasTake = "";
            var styletext = "";
            var styletext1 = "";
            $.each(data.obj.list, function (i, prizeTake) {

                tdHtml += "<tr>";
                tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>" + prizeTake.pkId + "</a></td>";
                tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>" + prizeTake.rewardNickName + "</a></td>";
                tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>" + prizeTake.rewardRealName + "</a></td>";

                tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>" + (prizeTake.schoolName || "") + "</a></td>";
                if (prizeTake.groups == null || prizeTake.groups == "") {
                    tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>无</a></td>";
                } else {
                    tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>" + prizeTake.groups + "</a></td>";
                }
                if (prizeTake.topicType == 1) {
                    tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'><span class='fa fa-file-picture-o' style='color: green;'></span></a></td>";
                } else if (prizeTake.topicType == 2) {
                    tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'><span class='fa fa-file-movie-o' style='color: red;'></a></span></td>";
                } else {
                    tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'><span class='fa fa-file-movie-o' style='color: yellow;'></span></a></td>";
                }
                tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>" + prizeTake.slogan + "</a></td>";
                tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>" + prizeTake.expireTime + "</a></td>";
                tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>1</a></td>";
                tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>" + prizeTake.prizeName + "</a></td>";
                if (prizeTake.hasTake == 1) {
                    hasTake = "领取";
                    styletext = "green";
                }
                if (prizeTake.hasTake == 0) {
                    hasTake = "未领";
                    styletext = "red";
                }
                tdHtml += "<td><a style='color: " + styletext + ";' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>" + hasTake + "</a></td>";
                tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>" + (prizeTake.awardName || "待发奖") + "</a></td>";
                tdHtml += "<td><a style='color: black;' href='/admin/PrizeTake/getTakeDetails/" + prizeTake.pkId + "'>" + (prizeTake.takeTime || "待发奖") + "</a></td>";
                tdHtml += "</tr>";

            });
            $('#prizeTakeList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#partnerListPagination").hide();
                $('#prizeTakeList').html("<tr><td colspan='13' style='text-align: center'>暂无数据</td></tr>");
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

