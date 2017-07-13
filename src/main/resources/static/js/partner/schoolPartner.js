/**
 * Created by flint
 * date: 24/10/2016
 */
var pagination;
$(function () {
    var page = 1;
    var row = 10;



    $("#starRating").select2({      //下拉框样式
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
    var realName = $("#seachText").val();
    var starRating=$("#starRating").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partnerManage/getSchoolPartner",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row, realName: realName,starRating:starRating}),
        dataType: 'json',
        success: function (data) {
            $('#partnerList').empty();
            var tdHtml = "";
            var sex = "";
            var arr=[];
            $.each(data.obj.list, function (i, partner) {
                    tdHtml += "<tr>";
                    tdHtml += "<td><a style='color: black;' href='/partnerManage/schoolDetails/"+partner.id+"'>" + partner.realName + "</a></td>";
                    if(partner.sex==1)
                    {
                        sex="男";
                    }
                    if(partner.sex==2)
                    {
                        sex="女";
                    }

                    tdHtml += "<td><a style='color: black;' href='/partnerManage/schoolDetails/"+partner.id+"'>" + sex + "</a></td>";
                    tdHtml += "<td><a style='color: black;' href='/partnerManage/schoolDetails/"+partner.id+"'>" + partner.userCnt + "</a></td>";
                    tdHtml += "<td><a style='color: black;' href='/partnerManage/schoolDetails/"+partner.id+"'>" + partner.schoolName + "</a></td>";
                    tdHtml += "<td><a style='color: black;' href='/partnerManage/schoolDetails/"+partner.id+"'>" + partner.mobile + "</a></td>";
                    tdHtml += "<td><a style='color: black;' href='/partnerManage/schoolDetails/"+partner.id+"'>" + partner.joinTime + "</a></td>";
                    tdHtml += "<td><a style='color: black;' href='/partnerManage/schoolDetails/"+partner.id+"'>" + partner.checkTime + "</a></td>";
                    tdHtml+="<td><div id='star"+i+"' style='font-size: 24px; color: #cccccc;'><span class='glyphicon glyphicon-star'></span><span class='glyphicon glyphicon-star'></span> <span class='glyphicon glyphicon-star'></span> <span class='glyphicon glyphicon-star'></span> <span class='glyphicon glyphicon-star'></span></div> </td>";
                    tdHtml += "<td><a style='color: black;' href='/partnerManage/schoolDetails/"+partner.id+"'>" + partner.remarks + "</a></td>";
                    tdHtml += "</tr>";
                    arr[i]=partner.starRating;


            });
            $('#partnerList').html(tdHtml);

            for(var j = 0; j < arr.length; j++){
                var astar = $("#star"+j+"").find('span');
                for(var e=0; e< arr[j]; e++)
                astar[e].style.color = '#ffe952';
            }

            if (data.obj.pages == 0) {
                $("#partnerListPagination").hide();
                $('#partnerList').html("<tr><td colspan='10' style='text-align: center'>暂无数据</td></tr>");
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
            }
        }
    });
}








