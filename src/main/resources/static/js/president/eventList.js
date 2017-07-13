/**
 * Created by Wang on 16/10/17.
 */
var pagination;
$(function() {
    var page=1;
    var rows=10;
    $("#confirmStatus").select2({
        minimumResultsForSearch: Infinity
    });
    $("#timeStatus").select2({
        minimumResultsForSearch: Infinity
    });
    initEventData(page,rows);

    $("#confirmStatus").on("change",function(){
        initEventData(page,rows);
    });
    $("#timeStatus").on("change",function(){
        initEventData(page,rows);
    });
    $("#search").on("click",function(){
        initEventData(page,rows);
    });

    $("#keyword").bind('keydown', function (e) {
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initEventData(page,rows);
        }
    });

    pagination = $('#eventListPagination').bootpag({
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
        initEventData(num,rows);
    });
});

function initEventData(page,rows) {
    var keyword = $("#keyword").val();
    var status = $("#confirmStatus").val();
    var timePoint = $("#timeStatus").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/president/event/list",
        contentType: 'application/json',
        data: JSON.stringify({page:page,rows:rows,keyword:keyword,status:status,timePoint:timePoint}),
        dataType: 'json',
        success: function(data){
            $('#eventList').empty();
            var tdHtml = "";
            $.each(data.obj.list, function(i, event){
                tdHtml += "<tr>";
                tdHtml += "<td><a style='color: black;' href='/president/event/detail/"+event.id+"'>" + event.subject + "</a></td>";
                tdHtml += "<td><a style='color: black;' href='/president/event/detail/"+event.id+"'>" + event.group.schoolName + "</a></td>";
                tdHtml += "<td><a style='color: black;' href='/president/event/detail/"+event.id+"'>" + event.group.name + "</a></td>";
                tdHtml += "<td><a style='color: black;' href='/president/event/detail/"+event.id+"'>" + event.startTime +"<br>"+ event.endTime + "</a></td>";
                if(event.status==1){
                    tdHtml +="<td ><a style='color: black' href='/president/event/detail/"+event.id+"'> 审核通过 </a></td>";
                }else if(event.status==0){
                    tdHtml +="<td ><a style='color: red' href='/president/event/detail/"+event.id+"'> 待审核 </a></td>";
                }else if(event.status==2){
                    tdHtml +="<td ><a style='color: orange' href='/president/event/detail/"+event.id+"'> 审核未通过 </a></td>";
                }
                if(event.timeStatus==1){
                    tdHtml +="<td><a style='color: black;' href='/president/event/detail/"+event.id+"'> 未开始 </a></td>";
                }else if(event.timeStatus==2){
                    tdHtml +="<td><a style='color: black;' href='/president/event/detail/"+event.id+"'> 进行中 </a></td>";
                }else if(event.timeStatus==3){
                    tdHtml +="<td><a style='color: black;' href='/president/event/detail/"+event.id+"'> 已结束 </a></td>";
                }
                if(event.timeStatus==1 && event.status==1){
                    tdHtml +="<td><a href='/president/eventMember/listInit/"+event.id+"/2'><span style='color: red'>"+ event.untreatedApplyCount +"</span>/"+ event.applyCount +"申请人"+"</span></td>";
                }
                if(event.timeStatus==3){
                    tdHtml +="<td>"+ event.untreatedApplyCount +"/"+ event.applyCount +"申请人"+"</td>";
                }else{
                    tdHtml +="<td></td>";
                }
                tdHtml +="</tr>";
            });
            $('#eventList').html(tdHtml);
            if (data.obj.pages==0) {
                $("#eventListPagination").hide();
                $('#eventList').html("<tr><td colspan='7' style='text-align: center'>暂无数据</td></tr>");
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
                $("#eventListPagination").show();
            }
        },
        error: function(err){
            console.log(err)
        }
    });
}