var pagination;
$(function() {
    var page=1;
    var row=10;
    getOfficeEventList(page,row);
    $("#timeStatus").select2({ minimumResultsForSearch: Infinity});
    pagination = $('#groupListPagination').bootpag({
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
        getOfficeEventList(num,row);
    });
});

function seachList(event) {
    var key = event.which;
    if (key == 13) {
        event.preventDefault();
        getOfficeEventList(1,10);
    }
}
function getOfficeEventList(page,row){
    var subject = $("#seachText").val();
    var timeStatus = $("#timeStatus").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/event/list",
        contentType: 'application/json',
        data: JSON.stringify({page:page,row:row,subject:subject,timeStatus:timeStatus,organizeId:0}),
        dataType: 'json',
        success: function(data){
            $('#eventList').empty();
            var tdHtml = "";
            $.each(data.obj.list, function(i, event){
                var timeStatusName ="";
                if(new Date((event.startTime).replace(/-/g,"/"))>new Date()){
                    timeStatusName = "未开始";
                }else if(new Date((event.startTime).replace(/-/g,"/"))<=new Date() && new Date((event.endTime).replace(/-/g,"/"))>=new Date()){
                    timeStatusName = "进行中";
                }else{
                    timeStatusName ="已结束"
                }

                tdHtml +='<tr onclick="detils('+event.id +')">'
                tdHtml += "<td class='eventId'>" + event.id + "</td>";
                tdHtml +='<td><a style="color:black;" href = "/admin/event/goOfficialDetails/'+event.id+'">' + event.subject + "</a></td>";
                tdHtml +='<td><a style="color:black;" href = "/admin/event/goOfficialDetails/'+event.id+'">' + event.address + "</a></td>";
                tdHtml +='<td><a style="color:black;" href = "/admin/event/goOfficialDetails/'+event.id+'">' + event.joinGroup + "</a></td>";
                tdHtml +='<td><a style="color:black;" href = "/admin/event/goOfficialDetails/'+event.id+'">' + event.startTime +"-"+event.endTime +"</a></td>";
                tdHtml +='<td><a style="color:black;" href = "/admin/event/goOfficialDetails/'+event.id+'">' + timeStatusName + "</a></td>";

            });
            $('#eventList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#groupListPagination").empty();
                $('#eventList').html("<tr><td colspan='6' style='text-align: center'>暂无数据</td></tr>");
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
            }
        }
    })
}


