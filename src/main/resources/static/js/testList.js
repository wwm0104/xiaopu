var pagination;
$(function () {
    var page = 1;
    var row = 10;
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
    }).on("page", function (event, num) {
        initData(num, row);
    });

    initData(1, 10);
});
function initData(page, row) {
    var name = $("#seachText").val();
    var status = $("#status").val();
    var schoolId = $("#schoolList").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/testdemo/testList",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row, name: name, status: status, schoolId: schoolId}),
        dataType: 'json',
        success: function (data) {
            $('#groupList').empty();
            $.each(data.obj.list, function (i, group) {
                var _s = "<tr><td>"+group.id+"</td>";
                _s += "<td style='color: #00a7d0'>"+group.name+"</td>";
                _s += "<td>"+group.schoolName+"</td>";
                _s += "<td>"+group.presidentName+"</td>";
                _s += "<td>"+group.mobile+"</td>";
                _s += "<td>"+group.studentNo+"</td>";
                _s += "<td>"+group.joinTime+"</td>";
                _s += "<td>"+group.status+"</td>";
                _s +="<td>123</td>";
                _s += '</tr>';
                $("#groupList").append(_s);
            });
            if (data.obj.pages == 0) {
                $("#groupListPagination").empty();
                layer.msg("暂无数据")
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