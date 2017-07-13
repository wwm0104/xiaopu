/**
 * Created by Wang on 16/11/07.
 */
var pagination;
$(function() {
    var page=1;
    var rows=10;
    $("#type").select2({
        minimumResultsForSearch: Infinity
    });
    $("#timePeriod").select2({
        minimumResultsForSearch: Infinity
    });
    $("#status").select2({
        minimumResultsForSearch: Infinity
    });
    setStatus();
    initTopicData(page,rows);

    $("#type").on("change",function(){
        initTopicData(page,rows);
    });
    $("#status").on("change",function(){
        initTopicData(page,rows);
    });

    $("#search").on("click",function(){
        initTopicData(page,rows);
    });

    $("#keyword").bind('keydown', function (e) {
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            initTopicData(page,rows);
        }
    });

    pagination = $('#topicListPagination').bootpag({
        total: 0,          // total pages
        page: 1,            // default page
        maxVisible: 10,     // visible pagination
        firstLastUse: true,
        prev: '上一页',
        next: '下一页',
        first: '首页',
        last: '末页',
        leaps: true         // next/prev leaps through maxVisible
    }).on("page", function(topic, num){
        initTopicData(num,rows);
    });
});

function initTopicData(page,rows) {
    var type = $("#type").val();
    var status = $("#status").val();
    var keyword = $("#keyword").val();
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();

    // if(startTime==''){
    //     layer.msg("请选择活动开始时间");
    //     return false;
    // }
    // if(endTime==''){
    //     layer.msg("请选择活动结束时间");
    //     return false;
    // }
    //
    var sTime = Date.parse(startTime);
    var eTime = Date.parse(endTime);
    if (sTime > eTime) {
        layer.msg("起始时间不能大于结束时间");
        return false;
    }

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/fight/topic/list",
        contentType: 'application/json',
        data: JSON.stringify({page:page,rows:rows,type:type,status:status,keyword:keyword,startTime:startTime,endTime:endTime}),
        dataType: 'json',
        success: function(data){
            $('#topicList').empty();
            // console.info(data);
            var tdHtml = "";
            $.each(data.obj.list, function(i, topic){
                tdHtml += "<tr>";
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'>"+topic.nickName+"</a></td>";
                if(topic.realName==null){
                    tdHtml += "<td ><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'> 校谱官方 </a></td>";
                }else{
                    tdHtml += "<td ><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'>"+topic.realName+"</a></td>";
                }
                if(topic.schoolName==null){
                    tdHtml += "<td ><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'>  </a></td>";
                }else{
                    tdHtml += "<td ><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'>"+topic.schoolName+"</a></td>";
                }
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'>"+topic.tags+"</a></td>";
                if (topic.type == 1) {
                    tdHtml += "<td><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'><span class='fa fa-file-picture-o' style='color: green;'></span></a></td>";
                } else if (topic.type == 2) {
                    tdHtml += "<td><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'><span class='fa fa-file-movie-o' style='color: red;'></a></span></td>";
                } else {
                    tdHtml += "<td><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'><span class='fa fa-file-movie-o' style='color: yellow;'></span></a></td>";
                }
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'>"+topic.createTime+"</a></td>";
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'>"+topic.challengeCnt+"</a></td>";
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/topicDetail/"+topic.id+"'>"+topic.name+"</a></td>";
                if(topic.status == 0){
                    tdHtml += "<td ><a style='color: #00a157;' href='/admin/fight/topicDetail/"+topic.id+"'> 待审核 </a></td>";
                }else if(topic.status == 2){
                    tdHtml += "<td ><a style='color: red;' href='/admin/fight/topicDetail/"+topic.id+"'> 未通过 </a></td>";
                }
                tdHtml +="</tr>";
            });
            $('#topicList').html(tdHtml);
            if (data.obj.pages==0) {
                $("#topicListPagination").hide();
                $('#topicList').html("<tr><td colspan='9' style='text-align: center'>暂无数据</td></tr>");
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
                $("#topicListPagination").show();
            }
        },
        error: function(err){
            console.log(err)
        }
    });
}

function setStatus() {
     if($("#statusDai").val() != "" && $("#statusDai").val() ==0){
         $("#status").val($("#statusDai").val())
         $("#status").select2({
             minimumResultsForSearch: Infinity
         });
    }
}
