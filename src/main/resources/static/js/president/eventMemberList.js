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

    $("#event").on("change",function(){
        initEventData(page,rows);
    });
    $("#status").on("change",function(){
        initEventData(page,rows);
    });
    $("#search").on("click",function(){
        initEventData(page,rows);
    });

    $("#close").on("click",function(){
        $("#listDescription").val("");
    });

    $("#keyword").bind('keydown', function (e) {
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initEventData(page,rows);
        }
    });

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

function initEventData(page,rows) {
    var keyword = $("#keyword").val();
    var eventId = $("#event").val();
    var status = $("#status").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/president/eventMember/list",
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
                tdHtml +="<td>" + eventMember.group.schoolName + "</td>";
                tdHtml +="<td>" + eventMember.group.name + "</td>";
                if(eventMember.isGroupMember==1){
                    tdHtml +="<td> 是 </td>";
                }else{
                    tdHtml +="<td> 否 </td>";
                }
                if(eventMember.status==1){
                    tdHtml +="<td> 已同意 </td>";
                }else if(eventMember.status==2){
                    tdHtml +="<td> 待处理 </td>";
                }else if(eventMember.status==3){
                    tdHtml +="<td> 已拒绝 </td>";
                }
                if(eventMember.status==2){
                    tdHtml +="<td style='text-align:left;'><input type='button' class='btn true-color btn-sm' onclick='checkEventOK("+eventMember.eventId+","+eventMember.memberId+","+1+")' value='通过'/> ";
                    tdHtml +="<input type='button' class='btn delete-btn-bg btn-sm' onclick='checkEventModelShow("+eventMember.eventId+","+eventMember.memberId+","+3+")' value='驳回'/>";
                    if(eventMember.isGroupMember!=1){
                        tdHtml +="<span>(通过将吸纳为社员)</span>";
                    }
                    tdHtml +="</td>";
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
        url: "/president/eventMember/confirm",
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

    if(memo == "" || memo == null){
        layer.msg("请输入驳回原因",{time:1000});
        return false;
    }

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/president/eventMember/confirm",
        contentType: 'application/json',
        data: JSON.stringify({eventId:eventId,memberId:memberId,status:status,memo:memo}),
        dataType: 'json',
        success: function(data){
            if(data.resultCode==1){
                $("#turndownModel").modal('hide');
                initEventData(1,10);
            }
        },
        error: function(err){
            console.log(err)
        }
    });
}