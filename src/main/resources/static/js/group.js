/**
 * Created by daer on 16/8/25.
 */
var pagination;
$(function() {
    var page=1;
    var row=10;
    initGroupData(page,row);
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
        initGroupData(num,row);
    });
});

function initGroupData(page,row) {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "http://apiservie.proxy.chinaxiaopu.cn/admin/group/list",
        contentType: 'application/json',
        data: JSON.stringify({page:page,row:row}),
        dataType: 'json',
        success: function(data){
            $('#groupList').empty();
            var tdHtml = "";
            $.each(data.obj.list, function(i, group){
                tdHtml += "<tr><td>" + group.name + "</td>";
                tdHtml +="<td>" + group.schoolName + "</td>";
                tdHtml +="<td>" + group.categoryName + "</td>";
                tdHtml +="<td>" + group.slogan + "</td>";
                tdHtml +="<td>" + group.presidentName + "</td>";
                tdHtml +="<td>" + group.status + "</td></tr>";
            });
            $('#groupList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#groupListPagination").empty();
                $('#groupList').html("<tr><td colspan='7' style='text-align: center'>暂无数据</td></tr>");
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
    });
}
