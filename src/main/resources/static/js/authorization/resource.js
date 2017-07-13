/**
 * Created by Ellie on 2016/11/22.
 */
$(function () {


    initTreeGrid();

    $(function () {
        $("#resourceForm").validation({icon: true});
        $("#add").on('click', function (event) {
            if ($("#resourceForm").valid(this) == false) {
                return false;
            } else {
                /**************数据处理*************/
                doAdd();
            }
        })
        //修改角色信息
        $("#update").on('click', function (event) {
            if ($("#resourceForm").valid(this) == false) {
                return false;
            } else {
                /**************数据处理*************/
                doUpdate();
            }
        })
    });
})

function initTreeGrid() {
    $.ajax({
        processData: false,
        url: "/resource/findResourceTable",
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            var tdHtml = "";
            $.each(data.obj, function (i, resource) {
                if (resource.parentId == 0) {
                    tdHtml += '<tr class="treegrid-' + resource.id + '"><td><a>' + (resource.name || "") + '</a></td>';
                } else {
                    tdHtml += '<tr class="treegrid-' + resource.id + ' treegrid-parent-' + resource.parentId + '"><td></i><a>' + (resource.name || "") + '</a></td>';
                    tdHtml += '<td class="hidden" id="resourceId" name="resourceId" value="' + resource.id + '"></td>'
                    tdHtml += '<td>' + (resource.url || "") + '</td>';
                    tdHtml += "<td>" + (resource.permission || "") + "</td>";
                    tdHtml += "<td>" + (resource.sort || "") + "</td>";
                    switch (resource.type) {
                        case 1:
                            tdHtml += "<td>菜单</td>";
                            break;
                        case 2:
                            tdHtml += "<td>按钮</td>";
                            break;
                        case 3:
                            tdHtml += "<td>超链接</td>";
                            break;
                        case 4:
                            tdHtml += "<td>列表</td>";
                            break;
                        default:
                            break;
                    }
                    tdHtml +=
                        "<td><button style='margin-right: 2px;' class='btn true-color' onclick='add(" + resource.id + ")' >添加下级菜单</button>" +
                        "<button style='margin-right: 2px;' class='btn true-color' onclick='update(" + resource.id + ")' >修改</button>" +
                        "<button style='margin-right: 2px;' class='btn delete-btn-bg' onclick='deleteResource(" + resource.id + ")'>删除</button>" +
                        "</td></tr>";
                }
                
                $('#treeTableTbody').html(tdHtml);
            })
            $("#resourceTreeTable").treegrid({

                initialState: "expanded",
                expanderTemplate: '<span class="treegrid-expander"></span>',
                indentTemplate: '<span class="treegrid-indent"></span>',
                treeColumn: 0,
                expanderExpandedClass: 'treegrid-expander-expanded',
                expanderCollapsedClass: 'treegrid-expander-collapsed',
                // expanderExpandedClass: 'glyphicon glyphicon-minus',
                // expanderCollapsedClass: 'glyphicon glyphicon-plus',
                onCollapse: function() {//合并时触发事件

                },
                onExpand: function() {//展开子节点时触发事件

                }
            });
        }
    })
}
function add(parentId) {
    $("#resourceForm").validation({icon: false});
    $("#sort").val("");
    $("#url").val("");
    $("#parentId").val("");
    $("#resourceName").val("");
    $("#permission").val("");
    $.ajax({
        processData: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/resource/selectAll",
        success: function (data) {
            var tdHtml = "";
            // tdHtml += "<option selected='selected' value='' >全部资源</option>";
            $.each(data.obj, function (i, resource) {
                if (resource.id == parentId) {
                    tdHtml += "<option selected ='selected' value=" + resource.id + ">" + resource.name + "</option>";
                } else {
                    tdHtml += "<option value=" + resource.id + ">" + resource.name + "</option>";
                }

            });
            $('#parentId').html(tdHtml);
            $('#selectGroup').modal("show");
            $("#myModalLabel").html("添加");
            $("#update").hide();
            $("#add").show();
        }
    })
}

function findResource() {
    var root = $('#resourceTreeTable').treegrid('getRootNodes').treegrid('expand');
    var parentId = $("#parentId").val();
    var available = $("#available").val();
    console.info(parentId, available, root);
}
function doAdd() {
    // var id = $("#resourceId").val();
    var sort = $("#sort").val();
    var url = $("#url").val();
    var parentId = $("#parentId").val();
    var name = $("#resourceName").val();
    var permission = $("#permission").val();
    var available = $("#available").val();
    var type = $("#type").val();
    $.ajax({
        processDate: false,
        type: "POST",
        url: "/resource/createResource",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            url: url,
            parentId: parentId,
            name: name,
            permission: permission,
            sort: sort,
            available: available,
            type: type
        }),
        success: function (data) {
            if (data.resultCode == 1) {//如果data.resultcode为1成功，0失败
                layer.msg(data.msg);
            } else {
                layer.msg(data.msg);
            }
            location.replace(location);

        }
    })
}

function update(id) {
    $('#parentId').empty();
    $("#resourceForm").validation({icon: false});
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/resource/findResourceByResourceId/" + id,
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            $("#resourceId").val(data.obj.id);//资源id
            // $("#parentId").val(data.obj.parentId);
            $("#resourceName").val(data.obj.name);
            $("#url").val(data.obj.url);
            $("#permission").val(data.obj.permission);
            $("#sort").val(data.obj.sort);
            var tdHtml = "";
            tdHtml += "<option selected='selected' value=" + data.obj.parentId + ">" + data.obj.name + "</option>";
            switch (data.obj.type) {
                // case 1:$("#type").("option[text='菜单']").attr("selected",true); break;
                // case 2:$("#type").("option[text='按钮']").attr("selected",true); break;
                // case 3:$("#type").("option[text='超链接']").attr("selected",true); break;
                // case 4:$("#type").("option[text='列表']").attr("selected",true); break;
                case 1:
                    $("#type").find("option[text='菜单']").attr("selected", true);
                    break;
                case 2:
                    $("#type").find("option[text='按钮']").attr("selected", true);
                    break;
                case 3:
                    $("#type").find("option[text='超链接']").attr("selected", true);
                    break;
                case 4:
                    $("#type").find("option[text='列表']").attr("selected", true);
                    break;
                default:
                    break;
            }
            $('#parentId').html(tdHtml);
            $('#selectGroup').modal("show");
            $("#myModalLabel").html("修改");
            $("#add").hide();
            $("#update").show();
        }
    });
}

function doUpdate() {
    var resourceId = $("#resourceId").val();//资源id
    var resourceName = $("#resourceName").val();//资源名
    var url = $("#url").val();//url地址
    var parentId = $("#parentId").val();
    var permission = $("#permission").val();
    var sort = $("#sort").val();
    var available = $("#available").val();
    var type = $("#type").find("option:selected").text();
    switch (type) {
        case '菜单':
            type = 1;
            break;
        case '按钮':
            type = 2;
            break;
        case '超链接':
            type = 3;
            break;
        case '列表':
            type = 4;
            break;
        default:
            break;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/resource/updateResource",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            id: resourceId,
            parentId: parentId,
            name: resourceName,
            url: url,
            permission: permission,
            sort: sort,
            available: available,
            type: type
        }),
        success: function (data) {
            if (data.resultCode == 1) {//如果data.resultcode为1成功，0失败
                layer.msg(data.msg);
            } else {
                layer.msg(data.msg);
            }
            initTreeGrid();
        }
    });
}


function deleteResource(id) {
    $("#delid").val(id);
    $('#delcfmModel').modal();
}

function urlSubmit() {
    var id = $.trim($("#delid").val());//获取会话中的隐藏属性delid
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/resource/deleteResource/" + id,
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