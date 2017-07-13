/**
 * Created by Wang on 16/10/17.
 */
var pagination;
$(function() {
    var page=1;
    var rows=10;
    $("#event").select2({
    });
    $("#status").select2({
        minimumResultsForSearch: Infinity
    });
    initEventData(page,rows);


    pagination = $('#eventMemberListPagination').bootpag({
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
        initEventData(num,rows);
    });
});
function getList() {
    initEventData(1,10)

}


function initEventData(page,rows) {
    var eventId = $("#event").val();
    var status = $("#status").val();
    var keyword = $("#keyword").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/event/eventMember/list",
        contentType: 'application/json',
        data: JSON.stringify({page:page,rows:rows,keyword:keyword,eventId:eventId,status:status}),
        dataType: 'json',
        success: function(data){
            $('#eventMemberList').empty();
            var tdHtml = "";
            $.each(data.obj.list, function(i, eventMember){
                tdHtml += "<tr>";
                tdHtml += "<td>" + eventMember.event.subject + "</td>";
                tdHtml +="<td>" + eventMember.userInfo.realName + "</td>";
                tdHtml +="<td>" + eventMember.userInfo.mobile + "</td>";
                tdHtml +="<td>" + eventMember.userInfo.studentNo + "</td>";
                tdHtml +="<td>" + eventMember.userInfo.schoolName + "</td>";
                if(eventMember.status==1){
                    tdHtml +="<td> 已同意 </td>";
                }else if(eventMember.status==2){
                    tdHtml +="<td> 待处理 </td>";
                }else if(eventMember.status==3){
                    tdHtml +="<td> 已拒绝 </td>";
                }
                if(eventMember.status==2){
                    tdHtml +="<td><input type='button' class='btn btn-primary btn-sm' onclick='checkEventOK("+eventMember.eventId+","+eventMember.memberId+","+1+")' value='通过'/> <input type='button' class='btn btn-primary btn-sm' onclick='checkEventModelShow("+eventMember.eventId+","+eventMember.memberId+","+3+")' value='驳回'/></td>";
                }else{
                    tdHtml +="<td></td>";
                }
                tdHtml +="</tr>";
            });
            $('#eventMemberList').html(tdHtml);
            if (data.obj.pages==0) {
                $("#eventMemberListPagination").hide();
                $('#eventMemberList').html("<tr><td colspan='9' style='text-align: center'>暂无数据</td></tr>");
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
                $("#eventMemberListPagination").show();
            }
        },
        error: function(err){
            console.log(err)
        }
    });
}

function checkEventModelShow(eventId,memberId,status) {
    $("#hideEventId").val(eventId);
    $("#hideMemberId").val(memberId);
    $("#hideStatus").val(status);
    $("#turndownModel").modal('show');
}

function checkEventOK(eventId,memberId,status) {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/event/eventMember/confirm",
        contentType: 'application/json',
        data: JSON.stringify({eventId:eventId,memberId:memberId,status:status}),
        dataType: 'json',
        success: function(data){
            if(data.resultCode==1){
                initEventData(1,10);
            }
        },
        error: function(err){
            console.log(err)
        }
    });
}

function checkEventNO() {
    var eventId = $("#hideEventId").val();
    var memberId = $("#hideMemberId").val();
    var status = $("#hideStatus").val();
    var memo = $("#listDescription").val();
    if(memo ==""){
        layer.msg("驳回原因不能为空")
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/event/eventMember/confirm",
        contentType: 'application/json',
        data: JSON.stringify({eventId:eventId,memberId:memberId,status:status,memo:memo}),
        dataType: 'json',
        success: function(data){
            if(data.resultCode==1){
                $("#turndownModel").modal('hide');
                $("#listDescription").val("");
                initEventData(1,10);
            }
        },
        error: function(err){
            console.log(err)
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
        initEventData(1, 10);
    }
}