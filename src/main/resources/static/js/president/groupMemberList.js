/**
 * Created by Wang on 16/10/17.
 */
var pagination;
$(function() {
    var page=1;
    var rows=10;

    $("#group").select2({
    });
    $("#status").select2({
        minimumResultsForSearch: Infinity
    });
    initGroupData(page,rows);

    $("#group").on("change",function(){
        initGroupData(page,rows);
    });
    $("#status").on("change",function(){
        initGroupData(page,rows);
    });
    $("#search").on("click",function(){
        initGroupData(page,rows);
    });

    $("#close").on("click",function(){
        $("#listDescription").val("");
    });

    $("#keyword").bind('keydown', function (e) {
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initGroupData(page,rows);
        }
    });

    pagination = $('#groupMemberListPagination').bootpag({
        total: 0,          // total pages
        page: 1,            // default page
        maxVisible: 10,     // visible pagination
        firstLastUse: true,
        prev: '上一页',
        next: '下一页',
        first: '首页',
        last: '末页',
        leaps: true         // next/prev leaps through maxVisible
    }).on("page", function(group, num){
        initGroupData(num,rows);
    });
});

function initGroupData(page,rows) {
    var keyword = $("#keyword").val();
    var groupId = $("#group").val();
    var status = $("#status").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/president/groupMember/list",
        contentType: 'application/json',
        data: JSON.stringify({page:page,rows:rows,keyword:keyword,groupId:groupId,status:status}),
        dataType: 'json',
        success: function(data){
            $('#groupMemberList').empty();
            var tdHtml = "";
            $.each(data.obj.list, function(i, groupMember){
                tdHtml += "<tr><td>" + groupMember.group.name + "</td>";
                tdHtml +="<td>" + groupMember.userInfo.realName + "</td>";
                tdHtml +='<td>' + groupMember.userInfo.mobile + '</td>';
                tdHtml +='<td>' + groupMember.userInfo.studentNo + '</td>';
                tdHtml +="<td>" + groupMember.joinTime + "</td>";
                if(groupMember.status==1){
                    tdHtml +="<td> 已同意 </td>";
                }else if(groupMember.status==2){
                    tdHtml +="<td> 待处理 </td>";
                }else if(groupMember.status==3){
                    tdHtml +="<td> 已拒绝 </td>";
                }
                if(groupMember.status==2){
                    tdHtml +="<td><input type='button' class='btn true-color btn-sm' onclick='checkGroupOK("+groupMember.groupId+","+groupMember.memberId+","+1+")' value='通过'/> <input type='button' class='btn delete-btn-bg btn-sm' onclick='checkGroupModelShow("+groupMember.groupId+","+groupMember.memberId+","+3+")' value='驳回'/></td>";
                }else{
                    tdHtml +="<td></td>";
                }
                tdHtml +="</tr>";
            });
            $('#groupMemberList').html(tdHtml);
            if (data.obj.pages==0) {
                $("#groupMemberListPagination").hide();
                $('#groupMemberList').html("<tr><td colspan='7' style='text-align: center'>暂无数据</td></tr>");
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
                $("#groupMemberListPagination").show();
            }
        },
        error: function(err){
            console.log(err)
        }
    });
}

function checkGroupModelShow(groupId,memberId,status) {
    $("#hideGroupId").val(groupId);
    $("#hideMemberId").val(memberId);
    $("#hideStatus").val(status);
    $("#turndownModel").modal('show');
}

function checkGroupOK(groupId,memberId,status) {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/president/groupMember/confirm",
        contentType: 'application/json',
        data: JSON.stringify({groupId:groupId,memberId:memberId,status:status}),
        dataType: 'json',
        success: function(data){
            if(data.resultCode==1){
                initGroupData(1,10);
            }
        },
        error: function(err){
            console.log(err)
        }
    });
}

function checkGroupNO() {
    var groupId = $("#hideGroupId").val();
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
        url: "/president/groupMember/confirm",
        contentType: 'application/json',
        data: JSON.stringify({groupId:groupId,memberId:memberId,status:status,memo:memo}),
        dataType: 'json',
        success: function(data){
            if(data.resultCode==1){
                $("#turndownModel").modal('hide');
                initGroupData(1,10);
            }
        },
        error: function(err){
            console.log(err)
        }
    });
}