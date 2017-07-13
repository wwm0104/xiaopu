/**
 * Created by Ellie on 2016/12/2.
 */
var pagination;
$(function () {
    var page = 1;
    var row = 10;

    initUserList(page, row);

    pagination = $('#userListPagination').bootpag({
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

    $("#keyWords").bind('keydown', function (e) {      //回车模糊查询事件
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initUserList(page, row);
        }
    });
    $(function () {
        $("#userForm").validation({icon: true});
        $("#add").on('click', function (event) {
            if ($("#userForm").valid(this) == false) {
                return false;
            } else {
                /**************数据处理*************/
                doAdd();
            }
        })
        //修改角色信息
        $("#update").on('click', function (event) {
            if ($("#userInfoForm").valid(this) == false) {
                return false;
            } else {
                /**************数据处理*************/
                doUpdate();
            }
        })
    });
// 加载学校列表
    getSchoolList();
// 加载角色列表
    getRoleList();
})

/**
 * 获取角色列表
 */
function getRoleList() {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/role/findRoleList",
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            $('#roleList').empty();
            var tdHtml = "";
            tdHtml += "<option selected='selected' value='' >全部角色</option>";
            $.each(data.obj, function (i, role) {
                tdHtml += "<option value=" + role.id + ">" + role.roleName + "</option>";
            });
            $('#roleList').html(tdHtml);

        }
    });
}

/**
 * 获取学校列表
 */
function getSchoolList() {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewgroup/schoolList",
        contentType: 'application/json',
        data: "",
        dataType: 'json',
        success: function (data) {
            $('#schoolList1').empty();
            var tdHtml = "";
            tdHtml += "<option selected='selected' value='' >全部学校</option>";
            $.each(data.obj, function (i, school) {
                tdHtml += "<option value=" + school.id + ">" + school.name + "</option>";
            });
            $('#schoolList1').html(tdHtml);
        }
    });
}

function initUserList(page, row) {
    var keyWord = $("#keyWords").val();
    var roleId = $("#roleList").val();
    var schoolId = $("#schoolList1").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/userInfo/manage/findAll",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row, keyWord: keyWord, roleId: roleId, schoolId: schoolId}),
        dataType: 'json',
        success: function (data) {
            $("#userList").empty();//清空列表
            var tdHtml = "";
            $.each(data.obj.list, function (i, userInfo) {
                tdHtml += "<tr><td class='hidden'>" + (userInfo.userId || "") + "</td>";
                tdHtml += "<td>" + (userInfo.realName || "") + "</td>";
                tdHtml += "<td>" + (userInfo.mobile || "") + "</td>";
                tdHtml += "<td>" + (userInfo.schoolName || "") + "</td>";
                tdHtml += "<td>" + (userInfo.nickName || "") + "</td>";
                tdHtml += "<td>" + (userInfo.roleKeys || "") + "</td>";
                if (userInfo.userSex == 1) {
                    userSex = '男';
                } else {
                    userSex = '女';
                }
                tdHtml += "<td>" + (userSex || "") + "</td>";
                tdHtml += "<td><button style='color:#444444;margin-right: 2px;' class='btn true-color' onclick='update(" + userInfo.userId + ")'>编辑</button>" +
                    // "<button  onclick='deleteUser(" + userInfo.userId + ")'  type='button' class='btn delete-btn-bg'>删除</button>" +
                    "</td></tr>";
            });
            $('#userList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#userListPagination").hide();
                $('#userList').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
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
                $("#userListPagination").show();
            }
        }
    });
}

function addSchoolList() {
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
            tdHtml += "<option selected='selected' value='' >全部学校</option>";
            $.each(data.obj, function (i, school) {
                tdHtml += "<option value=" + school.id + ">" + school.name + "</option>";
            });
            $('#schoolList').html(tdHtml);
        }
    });
}
function add() {
    addSchoolList()
    $("#userForm").validation({icon: false});
    $('#selectGroup').modal("show");
    $("#myModalLabel").html("添加");
    $("#add").show();
}

function doAdd() {
    var schoolId = $("#schoolList").val();
    var schoolName = $("#schoolList").find("option:selected").text();
    var realName = $("#realName").val();  //姓名
    var nickName = $("#nickName").val();  //昵称
    var mobile = $("#mobile").val();      //手机
    var password = $("#password").val();            //密码
    var password1 = $("#password1").val();          //确认密码
    var studentNo = $("#studentNo").val();       //学号
    var qq = $("#qq").val();                      //QQ号
    var userSex = $("#userSex").val();           //性别
    var UserInfo = {
        realName: realName,
        mobile: mobile,
        studentNo: studentNo,
        qq: qq,
        userSex: userSex,
        schoolId: schoolId,
        schoolName: schoolName,
        nickName: nickName
    };
    if (password != password1) {
        layer.msg("两次密码不一致！");
    } else {
        //执行添加ajax
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/userInfo/manage/createUser",
            contentType: 'application/json',
            data: JSON.stringify({userInfo: UserInfo, password: password}),
            dataType: 'json',
            success: function (data) {
                if (data.resultCode == 1) {//如果data.resultcode为1成功，0失败
                    layer.msg("用户创建成功");
                } else if (data.resultCode == 11) {
                    layer.msg("手机号为空");
                } else if (data.resultCode == 13) {
                    layer.msg("该用户已存在");
                } else {
                    layer.msg("用户添加失败");
                }
                location.replace(location);
            }
        });
    }

}

function update(id) {
    $('#selectGroup1').modal("show");
    $("#myModalLabel1").html("编辑");
    $("#update").show();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/userInfo/manage/findUserInfoById/" + id,
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            $("#userId1").val(data.obj.userId);
            $("#mobile1").val(data.obj.mobile);
            $("#realName1").val(data.obj.realName);
            $("#nickName1").val(data.obj.nickName);
            $("#userSex1").val(data.obj.userSex);
            $("#schoolName").val(data.obj.schoolName);
            loadRole(data.obj.userId);
        }
    });

}

function loadRole(id) {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/role/findRoleList",
        contentType: 'application/json',
        dataType: 'json',
        success: function (allRoles) {
            $.ajax({
                processData: false,
                type: 'POST',
                url: "/role/findRoleListByUserId/" + id,
                contentType: 'application/json',
                dataType: 'json',
                success: function (myRoles) {
                    $("#roleInfo").empty();//清空列表
                    var tdHtml = "";
                    var d = [];
                    $.each(myRoles.obj, function (i, role) {
                        d.push(role.id);
                    });
                    $.each(allRoles.obj, function (i, roles) {
                        var flag = $.inArray(roles.id, $.unique(d));
                        if (flag < 0) {
                            tdHtml += '<option   value="' + roles.id + '" >' + roles.roleName + '</option>';
                        } else {
                            tdHtml += '<option  selected="selected" value="' + roles.id + '" >' + roles.roleName + '</option>';
                        }
                    });
                    $("#roleInfo").html(tdHtml);
                    $("#roleInfo").select2({
                        tags: true,
                        maximumSelectionLength: 6
                    });
                }
            })
        }
    });
}

function doUpdate() {
    var userId = $("#userId1").val();
    var realName = $("#realName1").val();
    var nickName = $("#nickName1").val();
    var roleIdList = $("#roleInfo").val(); // 需要传入roleId的集合
    var userInfo = ({userId: userId, realName: realName, nickName: nickName});
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/userInfo/manage/updateUserInfo",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            userInfo: userInfo,
            roleIdList: roleIdList
        }),
        success: function (data) {
            if (data.resultCode == 1) {//如果data.resultcode为1成功，0失败
                layer.msg(data.msg);
            } else {
                layer.msg(data.msg);
            }
            location.reload();
        }
    });
}

function deleteUser(id) {
    $("#delid").val(id);
    $('#delcfmModel').modal();
}

function urlSubmit() {
    var id = $.trim($("#delid").val());//获取会话中的隐藏属性delid
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/userInfo/manage/deleteUser" + id,
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

function searchUser() {
    var keyWord = $("#keyWords").val();
    var page = 1;
    var row = 10;
    $.ajax({
        processData: false,
        type: "POST",
        contentType: 'application/json',
        url: "/userInfo/manage/getUserInfoList",
        dataType: 'json',
        data: JSON.stringify({
            keyWord: keyWord,
            page: page,
            row: row
        }),
        success: function (data) {
            $("#userList").empty();//清空列表
            var tdHtml = "";
            $.each(data.obj.list, function (i, userInfo) {
                tdHtml += "<tr><td class='hidden'>" + (userInfo.userId || "") + "</td>";
                tdHtml += "<td>" + (userInfo.realName || "") + "</td>";
                tdHtml += "<td>" + (userInfo.mobile || "") + "</td>";
                tdHtml += "<td>" + (userInfo.schoolName || "") + "</td>";
                tdHtml += "<td>" + (userInfo.nickName || "") + "</td>";
                tdHtml += "<td>" + (userInfo.roleKeys || "") + "</td>";
                if (userInfo.userSex == 1) {
                    userSex = '男';
                } else {
                    userSex = '女';
                }
                tdHtml += "<td>" + (userSex || "") + "</td>";
                tdHtml += "<td><button style='color:#444444;margin-right: 2px;' class='btn true-color' onclick='update(" + userInfo.userId + ")'>编辑</button>" +
                    "</td></tr>";
            });
            $('#userList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#userListPagination").hide();
                $('#userlist').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
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
                $("#userListPagination").show();
            }
        }
    })
}

