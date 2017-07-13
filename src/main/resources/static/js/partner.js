/**
 * Created by flint
 * date: 24/10/2016
 */
var pagination;
$(function () {
    var page = 1;
    var row = 10;

    // $("#status").select2({      //状态下拉框样式
    //     minimumResultsForSearch: Infinity
    // });
    // $("#type").select2({        //类型下拉框样式
    //     minimumResultsForSearch: Infinity
    // });
    initPartnerData(page,row);
    $("#seachBtn").on("click",function(){      //点击模糊查询
        initPartnerData(1,10);
    });

    $("#seachText").bind('keydown', function (e) {      //回车模糊查询事件
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initPartnerData(page,row);
        }
    });

    pagination = $('#partnerListPagination').bootpag({
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
        initPartnerData(num, row);
    });
    //验证驳回信息不能为空
    $("#noPass").validation({icon: true});
    $("#down").on('click', function (event) {
        if ($("#noPass").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            down();
        }
    })
});



//分页+查询（模糊姓名）
function initPartnerData(page, row) {
    var realName = $("#seachText").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partner/getSchoolPartner",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row, realName: realName}),
        dataType: 'json',
        success: function (data) {
            $('#partnerList').empty();
            var tdHtml = "";
            var statuName = "";
            var styletext = "";
            $.each(data.obj.list, function (i, partner) {
                    tdHtml += "<tr>";
                    tdHtml += "<td>" + partner.realName + "</td>";
                    tdHtml += "<td>" + partner.mobile + "</td>";
                    tdHtml += "<td>" + partner.joinTime + "</td>";
                    if (partner.status == 2) {
                        statuName = "审核通过";
                        styletext = "green;";
                    }else
                    if (partner.status == 3) {
                        statuName = "审核未通过";
                        styletext = "orange;";
                    }else if (partner.status == 1) {
                        statuName = "待审核";
                        styletext = "red;";
                    }
                    tdHtml += "<td id='status' style='color: "+styletext+"'>" + statuName + "</td>";
                    if (partner.status == 1) {
                        tdHtml += '<td><input type="button" id="pass" value="通过" style=" margin-right: 2px;" class="btn btn-primary" onclick = "success('+partner.status+',' + partner.id +')"/><input type="button" id="turndown" style="width:50px;" class="btn btn-danger" value="驳回" onclick = "tanchu('+partner.status+',' + partner.id +')"/></td>';
                    }
                    if(partner.status == 3){
                        tdHtml += '<td><input type="button" id="pass" value="重新审核" style=" class="btn btn-primary" onclick = "success('+partner.status+',' + partner.id +')"/></td>';
                    }
                    tdHtml += "</tr>";

            });
            $('#partnerList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#partnerListPagination").hide();
                $('#partnerList').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
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
                $("#partnerListPagination").show();
            }
        }
    });
}
//通过
function success(status,id)
{
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewpartner/checkPass",
        contentType: 'application/json',
        data: JSON.stringify({id: id,status: status}),
        dataType: 'json',
        success: function (data) {
            if(data.resultCode==1){     //1成功 0失败
                layer.msg("已通过！");
                initPartnerData(1, 10);
            }else{
                layer.msg("请稍后重试！");
            }
        }
    });
}

//驳回弹窗
function tanchu(status,id)
{
    $("#noPass").validation({icon: false});
    $("#partnerId").val(id);//绑定id
    $("#partnerStatus").val(status);//绑定状态
    $("#listDescription").val("");//审核内容置空
    $('#turndownModel').modal("show");

}
//驳回
function down() {
    var id = $("#partnerId").val();
    var status=$("#partnerStatus").val();
    var checkInfo = $("#listDescription").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewpartner/checkRefused",
        contentType: 'application/json',
        data: JSON.stringify({id: id,status: status, checkInfo:checkInfo}),
        dataType: 'json',
        success: function (data) {
            if(data.resultCode==1){     //1成功 0失败
                layer.msg("已驳回！");
                $("#turndownModel").modal('hide');
                $("#partnerId").val("");//id置空
                $("#partnerStatus").val("");//状态置空
                initPartnerData(1, 10);
            }else{
                layer.msg("请稍后重试！");
            }
        }
    });
}





