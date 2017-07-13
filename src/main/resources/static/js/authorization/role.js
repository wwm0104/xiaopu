/**
 * Created by Ellie on 2016/11/10.
 */
var pagination;
$(function () {
    var page = 1;
    var row = 10;

    initRoleList(page, row);

    pagination = $('#roleListPagination').bootpag({
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
        initRoleList(num, row);
    });

    $("#keyWords").bind('keydown', function (e) {      //绑定回车查询事件
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            searchRole()
        }
    });
    $(function () {
        $("#roleForm").validation({icon: true});
        //添加角色信息
        $("#add").on('click', function (event) {
            if ($("#roleForm").valid(this) == false) {
                return false;
            } else {
                /**************数据处理*************/
                doAdd();
            }
        })
        //修改角色信息
        $("#update").on('click', function (event) {
            if ($("#versionForm").valid(this) == false) {
                return false;
            } else {
                /**************数据处理*************/
                doUpdate();
            }
        })
    });
})


function initRoleList(page, row) {
    var roleKey = $("#keyWords").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/role/findAll",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row}),
        dataType: 'json',
        success: function (data) {
            $("#roleList").empty();//清空列表
            var tdHtml = "";
            $.each(data.obj.list, function (i, role) {
                tdHtml += "<tr><td>" + (role.id || "") + "</td>";
                tdHtml += "<td>" + (role.roleKey || "") + "</td>";
                tdHtml += "<td>" + (role.roleName || "") + "</td>";

                tdHtml += "<td><button style='margin-right: 2px;' onclick='update(" + role.id + ")' type='button' class='btn true-color'>修改</button>" +
                    "<button style='margin-right: 2px;' onclick='initJstree(" + role.id + ", \"" + role.roleName + "\")' type='button' class='btn true-color'>赋权</button>" +
                    "<a style='color:#444444;margin-right: 2px;' class='btn true-color'  href='/role/presonalManage?id=" + role.id + "'>人员管理</a>" +
                    "<button onclick='deleteRole(" + role.id + ")'  type='button' class='btn delete-btn-bg'>删除</button></td></tr>";
            });
            $('#roleList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#roleListPagination").hide();
                $('#roleList').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
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
                $("#roleListPagination").show();
            }
        }
    });
}

function add() {
    $("#roleForm").validation({icon: false});
    $("#roleKey").attr("disabled", false);
    //设定值为空
    $("#id").val("");
    $("#roleKey").val("");
    $("#roleName").val("");
    $('#selectGroup').modal("show");
    $("#myModalLabel").html("添加");
    $("#add").show();
    $("#update").hide();
}

function doAdd() {
    var roleKey = $("#roleKey").val();//角色标识
    var roleName = $("#roleName").val();//角色名
    //执行添加ajax
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/role/createRole",
        contentType: 'application/json',
        data: JSON.stringify({roleKey: roleKey, roleName: roleName}),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {//如果data.resultcode为1成功，0失败
                layer.msg(data.msg);
            } else {
                layer.msg(data.msg);
            }
            location.replace(location);
        }
    });
}

function update(id) {
    $("#roleForm").validation({icon: false});
    $("#roleKey").attr("disabled", true);
    $('#selectGroup').modal("show");
    $("#myModalLabel").html("修改");
    $("#add").hide();
    $("#update").show();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/role/findRole/" + id,
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            $("#roleId").val(data.obj.id);//角色id
            $("#roleKey").val(data.obj.roleKey);
            $("#roleName").val(data.obj.roleName);
        }
    });
}

function doUpdate() {
    var roleId = $("#roleId").val();//角色id
    var roleKey = $("#roleKey").val();//角色标识
    var roleName = $("#roleName").val();//角色名
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/role/updateRole",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            roleId: roleId,
            roleKey: roleKey,
            roleName: roleName
        }),
        success: function (data) {
            if (data.resultCode == 1) {//如果data.resultcode为1成功，0失败
                layer.msg(data.msg);
            } else {
                layer.msg(data.msg);
            }
            location.replace(location);
        }
    });
}

function deleteRole(id) {
    $("#delid").val(id);
    $('#delcfmModel').modal();
}

function urlSubmit() {
    var id = $.trim($("#delid").val());//获取会话中的隐藏属性delid
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/role/deleteRole/" + id,
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {//如果data.resultcode为1成功，2失败
                layer.msg(data.msg);
            } else {
                layer.msg(data.msg);
            }
            location.replace(location);
        }
    });
}

function initJstree(id, roleName) {
    //初始化之前需要先清空资源树 否则不会生成新的树
    $('#manage').data('jstree', false).empty();
    // $('#manage').empty().removeClass("jstree jstree-1 jstree-default-responsive");
    $('#resourcesModel').modal("show");
    $('#userRoleId').val(id);
    $('#userRoleName').val(roleName);
    $.ajax({
        cache: false,
        processData: false,
        type: 'POST',
        url: "/resource/findResource",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({roleId: id, roleName: roleName}),
        success: function (data) {
            // console.info(data);
            $('#manage').jstree({
                'core': {
                    'data': data
                },
                "themes": {
                    "dots": true,
                    "responsive": false
                },
                "plugins": ["checkbox", "types"],
                'types': {                         //图片的显示格式
                    "default": {
                        "icon": "fa fa-folder tree-item-icon-color icon-lg"
                    },
                    "file": {
                        "icon": "fa fa-file tree-item-icon-color icon-lg"
                    }
                },
                "checkbox": {
                    // "cascade": "",
                    // "three_state": false,
                    // "tie_selection": true,
                    // "whole_node": true,
                    // "keep_selected_style": false
                }
            });
        }
    });

}

function saveUserResource() {
    var resourceList = $('#manage').jstree(true).get_selected();
    var roleId = $("#userRoleId").val();
    // console.info(roleId, resourceList);
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "/resource/updateRoleResource",
        dataType: 'json',
        data: JSON.stringify({resourceList: resourceList, roleId: roleId}),
        success: function (data) {
            if (data.resultCode == 1) {//如果data.resultcode为1成功，2失败
                layer.msg(data.msg);
            } else {
                layer.msg(data.msg);
            }
            location.replace(location);
        }
    });
}
function searchRole() {
    var keyWord = $("#keyWords").val();
    var page = 1;
    var row = 10;
    $.ajax({
        processData: false,
        type: "POST",
        contentType: 'application/json',
        url: "/role/getRoleList",
        dataType: 'json',
        data: JSON.stringify({keyWord: keyWord, page: page, row: row}),
        success: function (data) {
            $("#roleList").empty();//清空列表
            var tdHtml = "";
            $.each(data.obj.list, function (i, role) {
                tdHtml += "<tr><td>" + (role.id || "") + "</td>";
                tdHtml += "<td>" + (role.roleKey || "") + "</td>";
                tdHtml += "<td>" + (role.roleName || "") + "</td>";

                tdHtml += "<td><button style='margin-right: 2px;' onclick='update(" + role.id + ")' type='button' class='btn true-color'>修改</button>" +
                    "<button style='margin-right: 2px;' onclick='initJstree(" + role.id + ",\"" + role.roleName + "\")'  type='button' class='btn true-color'>赋权</button>" +
                    "<a style='color:#444444;margin-right: 2px;' class='btn true-color'  href='/role/presonalManage?id=" + role.id + "'>人员管理</a>" +
                    "<button onclick='deleteRole(" + role.id + ")'  type='button' class='btn delete-btn-bg'>删除</button></td></tr>";
            });
            $('#roleList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#roleListPagination").hide();
                $('#roleList').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
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
                $("#roleListPagination").show();
            }
        }
    })

}

function saveResource(resourceId, roleId) {
    $.ajax({
        processData: false,
        type: "POST",
        contentType: 'application/json',
        url: "/role/addResources",
        dataType: 'json',
        data: JSON.stringify({resourceId: resourceId, roleId: roleId}),
        success: function (data) {
            if (data.resultCode == 1) {//如果data.resultcode为1成功，2失败
                layer.msg(data.msg);
            } else {
                layer.msg(data.msg);
            }
            location.replace(location);
        }
    })
}