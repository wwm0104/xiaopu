/**
 * Created by ycy on 2016/11/8.
 */
var pagination;
$(function() {
    var page=1;
    var row=10;
    getAllChanllengeList(page,row);
    pagination = $('#challengeListPagination').bootpag({
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
        getAllChanllengeList(num,row);
    });
});

$(function () {
    $("#typeList").select2();
})

function getAllChanllengeList(page,row){
    var id = $("#topicId").val();
    var type = $("#typeList").val();
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    var str = $("#reservationtime").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/AllChanllengeList",
        contentType: 'application/json',
        data: JSON.stringify({page:page,row:row,id:id,type:type,startTime:startTime,endTime:endTime}),
        dataType: 'json',
        success: function(data){
            $('#challengeList').empty();
            var tag = "";
            var tdHtml = "";
            $.each(data.obj.list, function(i, topic){
                if(topic.type == 1){
                    tag ="图文";
                }
                if(topic.type == 2){
                    tag ="视频";
                }
                tdHtml +='<tr><td>'+ topic.userId + '</td>'
                tdHtml += "<td>" + topic.nickName + "</td>";
                tdHtml +="<td>" + topic.realName + "</td>";
                tdHtml +="<td>" + tag + "</td>";
                if(topic.tipoffSum == null){
                    tdHtml +="<td></td>";
                }else {
                    tdHtml +="<td style='color: red'>" + topic.tipoffSum + "</td>";
                }
                tdHtml +="<td>" + topic.createTime + "</td>";
                tdHtml +="<td>" + topic.tags + "</td>";
                tdHtml +="<td>" + topic.recommend + "</td>";
                tdHtml +="<td>" + topic.voteCnt + "</td>";
                tdHtml +="<td><input type='button' class='btn btn-block delete-btn-bg btn-sm' id='deleteButton' value='删除' onclick='deleteInfo("+topic.topicId+")'></td>>"
            });
            $('#challengeList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#challengeListPagination").empty();
                $('#challengeList').html("<tr><td colspan='10' style='text-align: center'>暂无数据</td></tr>");
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
            }
        }
    })
}

function deleteInfo(topicId){
    $("#delcfmModel").modal("show");
    $("#delid").val(topicId)
}

function deleteTopic() {
    var id =$("#delid").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/deleteTopic",
        contentType: 'application/json',
        data: JSON.stringify({id:id}),
        dataType: 'json',
        success: function(data){
            $("#delcfmModel").modal("hide");
            if(data.resultCode == 1){
                layer.msg("已删除");
                $("#joinCnt").text($("#joinCnt").text()-1)
                getAllChanllengeList();
            }else {
                layer.msg("删除失败，请稍后重试")
            }
        }
    })
}

$(function () {
    $("#startTime").datetimepicker({
        format: 'yyyy/mm/dd hh:ii',
        language: 'zh-CN', //汉化
        autoclose: true //选择日期后自动关闭
    }).on('changeDate', function (ev) {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        if (endTime == "") {
            $("#endTime").focus();
            return false;
        } else {
            var sTime = Date.parse(startTime);
            var eTime = Date.parse(endTime);
            if (sTime > eTime) {
                layer.msg("结束时间不能大于开始时间");
                $("#endTime").val("");
                $("#endTime").focus();
                return;
            } else {
                getAllChanllengeList()
            }
        }
    });

    $("#endTime").datetimepicker({
        format: 'yyyy/mm/dd hh:ii',
        language: 'zh-CN', //汉化
        autoclose: true //选择日期后自动关闭
    }).on('changeDate', function (ev) {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        if (startTime == "") {
            $("#endTime").val("");
            $("#startTime").focus();
            return false;
        } else {
            var sTime = Date.parse(startTime);
            var eTime = Date.parse(endTime);
            if (sTime > eTime) {
                layer.msg("结束时间不能大于开始时间");
                $("#endTime").val("");
                $("#endTime").focus();
                return;
            } else {
                getAllChanllengeList()
            }
        }
    });
});