/**
 * Created by flint
 * date: 24/10/2016
 */
var pagination;
$(function () {
    var page = 1;
    var row = 10;

    $("#schoolList").select2({
        minimumResultsForSearch: Infinity
    });
    $("#sex").select2({
        minimumResultsForSearch: Infinity
    });

    getSchoolList();//加载校园列表
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

    //    星星
    var astar = $('#star').find('span');
    var temp = 0;
    for (var i=0;i< astar.length;i++){
        astar[i].index = i;
        astar[i].onclick = function(){
            temp = this.index + 1;
            clear();
            current(temp);
        }
    }
    //清除所有
    function clear(){
        for(var i = 0; i < astar.length; i++){
            astar[i].style.color = '#ccc';
        }
    }

    //显示当前第几颗星
    function current(temp){
        $("#starRating").val(temp);
        for(var i = 0; i < temp; i++){
            astar[i].style.color = '#ffe952';
        }
    }

    $("#noPass").validation({icon: true});
    $("#down").on('click', function (event) {
        if ($("#noPass").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            down();  //驳回操作
        }
    })

    $("#yesPass").validation({icon: true});
    $("#success").on('click', function (event) {
        if ($("#yesPass").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            success(); //同意操作
        }
    })

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
});



//分页+查询（模糊姓名）
function initPartnerData(page, row) {
    var realName = $("#seachText").val();
    var schoolId = $("#schoolList").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partnerManage/getGroupPartnerLook",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row, realName: realName,schoolId:schoolId}),
        dataType: 'json',
        success: function (data) {
            $('#partnerList').empty();
            var tdHtml = "";
            var statuName = "";
            var styletext = "";
            $.each(data.obj.list, function (i, partner) {
                    tdHtml += "<tr>";
                    tdHtml += "<td>" + partner.groupName + "</td>";
                    tdHtml += "<td>" + partner.schoolName + "</td>";
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
                        tdHtml += '<td><input type="button" id="pass" value="通过" style=" margin-right: 2px;" class="btn true-color" onclick = "tanchuPass('+partner.status+',' + partner.id +')"/><input type="button" id="turndown" style="width:50px;" class="btn delete-btn-bg" value="驳回" onclick = "tanchuOver('+partner.status+',' + partner.id +')"/></td>';
                    }
                    if(partner.status == 3){
                        tdHtml += '<td><input type="button" id="pass" value="重新审核" style=" class="btn btn-primary" onclick = "again('+partner.status+',' + partner.id +')"/></td>';
                    }
                    tdHtml += "</tr>";

            });
            $('#partnerList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#partnerListPagination").hide();
                $('#partnerList').html("<tr><td colspan='10' style='text-align: center'>暂无数据</td></tr>");
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

//驳回弹窗
function tanchuOver(status,id)
{
    $("#noPass").validation({icon: false});
    $("#partnerId").val(id);//绑定id
    $("#partnerStatus").val(status);//绑定状态
    $("#listDescription").val("");//审核内容置空
    $('#turndownModelOver').modal("show");

}

//通过弹窗
function tanchuPass(status,id)
{
    $("#yesPass").validation({icon: false});
    $("#partnerId1").val(id);//绑定id
    $("#partnerStatus1").val(status);//绑定状态
    $("#remarks").val("");//审核内容置空
    $('#turndownModelPass').modal("show");

}

//通过
function success()
{
    var id = $("#partnerId1").val();
    var status=$("#partnerStatus1").val();
    var starRating=$("#starRating").val();
    var remarks=$("#remarks").val();
    var sex=$("#sex").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partnerManage/checkGroupPass",
        contentType: 'application/json',
        data: JSON.stringify({id: id,status: status,starRating:starRating,remarks:remarks,sex:sex}),
        dataType: 'json',
        success: function (data) {
            if(data.resultCode==1){     //1成功 0失败
                layer.msg("已通过！");
                $("#turndownModelPass").modal('hide');
                $("#partnerId1").val("");//id置空
                $("#partnerStatus1").val("");//状态置空
                $("#remarks").val("");
                initPartnerData(1, 10);
            }else{
                layer.msg("请稍后重试！");
            }
        }
    });
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
                $("#turndownModelOver").modal('hide');
                $("#partnerId").val("");//id置空
                $("#partnerStatus").val("");//状态置空
                $("#listDescription").val("");
                initPartnerData(1, 10);
            }else{
                layer.msg("请稍后重试！");
            }
        }
    });
}
//重新审核
function again(status,id) {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/partnerManage/checkAgain",
        contentType: 'application/json',
        data: JSON.stringify({id: id,status: status}),
        dataType: 'json',
        success: function (data) {
            if(data.resultCode==1){     //1成功 0失败
                layer.msg("重新审核！");
                initPartnerData(1, 10);
            }else{
                layer.msg("请稍后重试！");
            }
        }
    });
}

/**
 * 加载校园列表
 */
function getSchoolList(){
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/school/list",
        contentType: 'application/json',
        data: "",
        dataType: 'json',
        success: function(data){
            $('#schoolList').empty();
            var tdHtml = "";
            tdHtml+="<option selected='selected' value='' >全部学校</option>";
            $.each(data.obj, function(i, school){
                tdHtml +="<option value="+school.id+">"+school.name+"</option>";
            });
            $('#schoolList').html(tdHtml);
        }
    });
}






