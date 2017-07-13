/**
 * Created by Ellie on 2016/12/13.
 */
var pagination;
var pagination1;
$(function () {
    var page = 1;
    var row = 10;


    pagination = $('#userRoleListPagination').bootpag({
        total: 0,          // total pages
        page: 1,            // default page
        maxVisible: 10,     // visible pagination
        firstLastUse: true,
        prev: '上一页',
        next: '下一页',
        first: '首页',
        last: '末页',
        leaps: true
    }).on("page", function (event, num) {
        initUserList(num, row);
    });

    pagination1 = $('#userNotRoleListPagination').bootpag({
        total: 0,          // total pages
        page: 1,            // default page
        maxVisible: 10,     // visible pagination
        firstLastUse: true,
        prev: '上一页',
        next: '下一页',
        first: '首页',
        last: '末页',
        leaps: true
    }).on("page", function (event, num) {
        searchUserNotInRole(num, row);
    });

    initUserList(page, row);
    searchUserNotInRole(page, row)

    $("#keyWords").bind('keydown', function (e) {      //回车模糊查询事件
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            searchUserNotInRole(page, row)
        }
    });
})


function initUserList(page, row) {
    var roleId = $("#roleId").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/userInfo/manage/findRoleUserInfoList",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row, roleId: roleId}),
        dataType: 'json',
        success: function (data) {
            $("#userRoleList").empty();//清空列表
            var tdHtml = "";
            $.each(data.obj.list, function (i, userInfo) {
                tdHtml += "<tr><td id='userId' class='hidden'>" + (userInfo.userId || "") + "</td>";
                tdHtml += "<td>" + (userInfo.realName || "") + "</td>";
                tdHtml += "<td>" + (userInfo.mobile || "") + "</td>";
                tdHtml += "<td>" + (userInfo.nickName || "") + "</td>";
                if (userInfo.userSex == 1) {
                    userSex = '男';
                } else {
                    userSex = '女';
                }
                tdHtml += "<td>" + (userSex || "") + "</td>";
                tdHtml += "<td ><button  onclick='deleteUser(" + userInfo.userId + ")'  type='button' class='btn delete-btn-bg'>删除</button></td>" +
                    "</td></tr>";
            });
            $('#userRoleList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#userRoleListPagination").hide();
                $('#userRoleList').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
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
                $("#userRoleListPagination").show();
            }
        }
    });
}

function searchUserNotInRole(page, row) {
    var roleId = $("#roleId").val();
    var keyWord = $("#keyWords").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/userInfo/manage/findUserNotInRole",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({roleId: roleId, keyWord: keyWord, page: page, row: row}),
        success: function (data) {
            $("#userNotRoleList").empty();//清空列表
            var tdHtml = "";
            $.each(data.obj.list, function (i, userInfo) {
                tdHtml += "<tr><td class='hidden'>" + (userInfo.userId || "") + "</td>";
                tdHtml += "<td>" + (userInfo.realName || "") + "</td>";
                tdHtml += "<td>" + (userInfo.mobile || "") + "</td>";
                tdHtml += "<td>" + (userInfo.nickName || "") + "</td>";
                if (userInfo.userSex == 1) {
                    userSex = '男';
                } else {
                    userSex = '女';
                }
                tdHtml += "<td>" + (userSex || "") + "</td>";
                tdHtml += "<td><button type='button' class='btn true-color' onclick='add(" + userInfo.userId + ")'>添加</button>" +
                    "</td></tr>";
            });
            $('#userNotRoleList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#userNotRoleListPagination").hide();
                $('#userNotRoleList').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
            } else {
                pagination1.bootpag({
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
                $("#userNotRoleListPagination").show();
            }
        }
    });
}

function deleteUser(id) {
    $("#delid").val(id);
    $('#delcfmModel').modal();
}

function urlSubmit() {
    var userId = $.trim($("#delid").val());
    var roleId = $("#roleId").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/userInfo/manage/deleteUserRole",
        contentType: 'application/json',
        data: JSON.stringify({
            userId: userId,
            roleId: roleId
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("删除成功")
            } else {
                layer.msg("删除失败")
            }
            initUserList(1, 10);
            searchUserNotInRole(1, 10);
        }
    });
}
function add(id) {
    var roleName = $("#roleName").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/role/addRoleByUserId",
        contentType: 'application/json',
        data: JSON.stringify({
            userId: id, roleName: roleName
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("添加成功")
            } else {
                layer.msg("添加失败")
            }
            initUserList(1, 10);
            searchUserNotInRole(1, 10);
        }
    });
}