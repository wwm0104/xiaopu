/**
 * Created by ycy on 2016/11/9.
 */
/**
 * Created by ycy on 2016/11/8.
 */
var pagination;
$(function () {
    var page = 1;
    var row = 10;
    getPresenList(page, row);
    pagination = $('#presenListPagination').bootpag({
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
        getPresenList(num, row);
    });
});

$(function () {
    $("#typeList").select2();
})

function showModel() {
    $("#delcfmModel").modal('show');
}

function getPresenList(page, row) {
    ;
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/PrizeTake/getAwardPresen",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row}),
        dataType: 'json',
        success: function (data) {
            $('#presenList').empty();
            var tdHtml = "";
            $.each(data.obj.list, function (i, presen) {
                tdHtml += "<tr><td onclick='clickEvent(" + presen.userId + ")'>" + presen.id + "</td>"
                tdHtml += "<td onclick='clickEvent(" + presen.userId + ")'>" + presen.realName + "</td>";
                tdHtml += "<td onclick='clickEvent(" + presen.userId + ")'>" + presen.mobile + "</td>";
                tdHtml += "<td onclick='clickEvent(" + presen.userId + ")'>" + presen.officialName + "</td>";
                tdHtml += "<td onclick='clickEvent(" + presen.userId + ")'>" + presen.officialMobile + "</td>";
                tdHtml += "<td onclick='clickEvent(" + presen.userId + ")'>" + presen.remarks + "</td>";
                tdHtml += "<td>" + presen.awardCnt + "</td>";
                if (presen.available == 1) {
                    tdHtml += "<td><input type='button' class='btn tinyong-bg btn-sm' id='tiyong' value='停用' onclick='updatePresen(" + presen.id + ",0)'><input type='button' class='btn qiyong-bg btn-sm' id='huifu' value='恢复' style='display: none' onclick='updatePresen(" + presen.id + ",1)'></td>>"

                } else {
                    tdHtml += "<td><input type='button' class='btn tinyong-bg btn-sm' id='tiyong' value='停用' style='display: none' onclick='updatePresen(" + presen.id + ",0)'><input type='button' class='btn qiyong-bg btn-sm' id='huifu' value='恢复' onclick='updatePresen(" + presen.id + ",1)'></td>>"
                }
            });
            $('#presenList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#presenListPagination").empty();
                $('#presenList').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
                $("#export").hide();
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
                $("#export").show();
            }
        }
    })
}

function checkPhone(thisObj) {
    var phone = $(thisObj).val();
    if (!(/^1[34578]\d{9}$/.test(phone))) {
        layer.msg("手机号码有误，请重填");
        return false;
    }
}

var checkIndex = 2;

function checkUserInfo() {
    var realName = $("#realName").val();
    var mobile = $("#mobile").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/PrizeTake/checkUserInfo",
        contentType: 'application/json',
        data: JSON.stringify({realName: realName, mobile: mobile}),
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.resultCode == 1) {
                checkIndex = 1;
            }
        }
    });
}


function createPresen() {
    var realName = $("#realName").val();
    var mobile = $("#mobile").val();
    var officialName = $("#officialName").val();
    var officialMobile = $("#officialMobile").val();
    var remarks = $("#remarks").val();
    if ("" == realName) {
        layer.msg("请填写真实姓名");
        return false;
    }
    if ("" == mobile) {
        layer.msg("请填写电话");
        return false;
    }
    checkUserInfo();
    if (checkIndex == 2) {
        layer.msg("该姓名与手机不匹配或不是校谱用户");
        return false;
    }
    if (!(/^1[34578]\d{9}$/.test(mobile))) {
        layer.msg("手机号码有误，请重填");
        return false;
    }
    if (!(/^1[34578]\d{9}$/.test(officialMobile))) {
        layer.msg("负责人手机号码有误，请重填");
        return false;
    }
    if ("" == officialName) {
        layer.msg("请填写官方负责人姓名");
        return false;
    }
    if ("" == officialMobile) {
        layer.msg("请填写官方负责人电话");
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/PrizeTake/createPresen",
        contentType: 'application/json',
        data: JSON.stringify({
            realName: realName,
            mobile: mobile,
            officialName: officialName,
            officialMobile: officialMobile,
            remarks: remarks
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                $("#delcfmModel").modal("hide");
                layer.msg("创建成功");
                $("#realName").val("");
                $("#mobile").val("");
                $("#officialName").val("");
                $("#officialMobile").val("");
                $("#remarks").val("");
                getPresenList(1,10);
            } else {
                layer.msg("创建失败")
            }
        }
    });
}

function updatePresen(id, available) {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/PrizeTake/updatePresen",
        contentType: 'application/json',
        data: JSON.stringify({id: id, available: available}),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                getPresenList(1, 10);
                layer.msg("成功")
            } else {
                layer.msg("修改失败")
            }
        }
    });
}

function clickEvent(userId) {
    window.location.href="/admin/PrizeTake/toAwardList/"+userId;

}