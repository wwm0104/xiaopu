/**
 * Created by daer on 16/8/25.
 */
var pagination;
$(function () {
    var page = 1;
    var row = 10;
    $("#schoolList").select2({ minimumResultsForSearch: Infinity});
    $("#status").select2({ minimumResultsForSearch: Infinity});
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
        getGroupList(num, row);
    });
});

function getSchoolList() {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewgroup/schoolList",
        contentType: 'application/json',
        data: "",
        dataType: 'json',
        success: function (data) {
            $('#schoolList').empty();
            var tdHtml = "";
            tdHtml += "<option value=''>所有大学名称</option>";
            $.each(data.obj, function (i, school) {
                tdHtml += "<option value=" + school.id + ">" + school.name + "</option>";
            });
            $('#schoolList').html(tdHtml);
        }
    });
}


function initGroupData(page, row) {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewgroup/reviewList",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row}),
        dataType: 'json',
        success: function (data) {
            $('#groupList').empty();
            var tdHtml = "";
            var statuName = "";
            var styletext = "";
            $.each(data.obj.list, function (i, group) {
                tdHtml += "<tr><td class='groupId'>" + group.id + "</td>";
                tdHtml += "<td>" + group.name + "</td>";
                tdHtml += "<td>" + group.schoolName + "</td>";
                tdHtml += "<td>" + group.presidentName + "</td>";
                tdHtml += "<td>" + group.mobile + "</td>";
                tdHtml += "<td>" + group.studentNo + "</td>";
                tdHtml += "<td>" + group.joinTime + "</td>";
                if (group.status == 1) {
                    statuName = "审核通过";
                    styletext = 'style="color:green;"'
                } else if (group.status == 3) {
                    statuName = "审核驳回";
                } else if (group.status == 0) {
                    statuName = "未认领"
                } else if (group.status == 2) {
                    statuName = "待审核"
                    styletext = 'style="color:red;"'
                }
                tdHtml += '<td'+styletext+'>' + statuName + '</td>';
                if (group.status == 2) {
                    tdHtml += '<td><input type="button" id="pass" value="通过" style="width:45%" class="btn btn-block true-color" onclick = "review(1,' + group.id + ')"/> <input type="button" id="turndown" style="width:45%" class="btn btn-block delete-btn-bg"  value="驳回" onclick = "tanchu(' + group.id + ')"/></td>';
                }
            });
            $('#groupList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#groupListPagination").empty();
                $('#groupList').html("<tr><td colspan='9' style='text-align: center'>暂无数据</td></tr>");
            }else {
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


function toList() {
    if($("#statusDai").val() != "" && $("#statusDai").val() ==2){
        $("#status").val($("#statusDai").val())
        $("#status").select2({ minimumResultsForSearch: Infinity});
    }
    getGroupList(1, 10);
}

function getGroupList(page, row) {
    var name = $("#seachText").val();
    var status = $("#status").val();
    var schoolId = $("#schoolList").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewgroup/reviewList",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row, name: name, status: status, schoolId: schoolId}),
        dataType: 'json',
        success: function (data) {
            $('#groupList').empty();
            var tdHtml = "";
            var statuName = "";
            var styletext = "";
            $.each(data.obj.list, function (i, group) {
                tdHtml += "<tr><td class='groupId'>" + group.id + "</td>";
                tdHtml += '<td><a style="color:black;" href = "/admin/reviewgroup/goGroupDetails/'+group.id+'">' + group.name + "</a></td>";
                tdHtml += '<td><a style="color:black;" href = "/admin/reviewgroup/goGroupDetails/'+group.id+'">' + group.schoolName + "</a></td>";
                tdHtml += '<td><a style="color:black;" href = "/admin/reviewgroup/goGroupDetails/'+group.id+'">' + group.presidentName + "</a></td>";
                tdHtml += '<td><a style="color:black;" href = "/admin/reviewgroup/goGroupDetails/'+group.id+'">' + group.mobile + "</a></td>";
                tdHtml += '<td><a style="color:black;" href = "/admin/reviewgroup/goGroupDetails/'+group.id+'">' + group.studentNo + "</a></td>";
                tdHtml += '<td><a style="color:black;" href = "/admin/reviewgroup/goGroupDetails/'+group.id+'">' + group.joinTime + "</a></td>";
                if (group.status == 1) {
                    statuName = "审核通过";
                    styletext = "green;";
                } else if (group.status == 3) {
                    statuName = "审核驳回";
                } else if (group.status == 0) {
                    statuName = "未认领"
                } else if (group.status == 2) {
                    statuName = "待审核"
                    styletext = "red;";
                }
                tdHtml += "<td id='status' style='color: "+styletext+"'>" + statuName + "</td>";
                if (group.status == 2) {
                     //tdHtml += '<td><div style="float: left"><input type="button" id="pass" value="通过" style="width:50px;" class="btn btn-block true-color" onclick = "review(1,' + group.id +','+ group.presidentId+')"/></div> <div style="margin-left:60px;"><input type="button" id="turndown" style="width:50px;" class="btn btn-block delete-btn-bg" value="驳回" onclick = "tanchu(' + group.id + ')"/></div></td>';
                     tdHtml += '<td style="font-size: 20px;"><span id="pass" class="fa fa-check" style="color: #00a65a;margin-right: 10px;cursor:pointer;" onclick = "review(1,' + group.id +','+ group.presidentId+')"></span><span class="fa fa-close" style="color: #FF5021;cursor:pointer;" id="turndown" onclick = "tanchu(' + group.id + ')"></span></td>';
                }
                else {
                    tdHtml += "<td></td>";
                }
            });
            $('#groupList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#groupListPagination").empty();
                $('#groupList').html("<tr><td colspan='9' style='text-align: center'>暂无数据</td></tr>");
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

function review(status, id,presidentId) {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewgroup/reviewGroup",
        contentType: 'application/json',
        data: JSON.stringify({id: id, status: status, presidentId:presidentId}),
        dataType: 'json',
        success: function (data, status) {
            layer.msg("审核通过");
            getGroupList(1, 10);
        }
    });
}


function returndown(status) {
    var id = $("#ycId").val();
    var memo = $("#listDescription").val();
    if($("#listDescription").val() == ""){
        layer.msg("请输入驳回原因");
        return false;
    }

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewgroup/reviewGroup",
        contentType: 'application/json',
        data: JSON.stringify({id: id, status: status, memo:memo}),
        dataType: 'json',
        success: function (data, status) {
            layer.msg("已驳回");
            $("#turndownModel").modal('hide');
            $("#listDescription").val("");
            getGroupList(1, 10);
        }
    });
}

function divClean() {
    $("#listDescription").val("");
    $("#turndownModel").modal('hide');
}

function seachList(event) {
    var key = event.which;
    if (key == 13) {
        event.preventDefault();
        getGroupList(1,10);
    }
}
