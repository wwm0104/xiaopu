/**
 * Created by ycy on 2016/12/6.
 */
var pagination;
$(function() {
    var page=1;
    var row=10;
    getAudioList(page,row);
    $("#status").select2({ minimumResultsForSearch: Infinity});
    $("#timeStatus").select2({ minimumResultsForSearch: Infinity});
    pagination = $('#audioListPagination').bootpag({
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
        getAudioList(num,row);
    });
});

function seachList(event) {
    var key = event.which;
    if (key == 13) {
        event.preventDefault();
        getEventList(1, 10);
    }
}

function getAudioList(page,row){
    // var subject = $("#seachText").val();
    // var status = $("#status").val();
    // var schoolId= $("#schoolList").val();
    // var timeStatus = $("#timeStatus").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/anchorManage/audioList",
        contentType: 'application/json',
        data: JSON.stringify({page:page,row:row}),
        dataType: 'json',
        success: function(data){
            $('#audioList').empty();
            var tdHtml = "";
            $.each(data.obj.list, function(i, audio){
                var statusName="";
                var styletext = "";
                if(audio.status == 0){
                    statusName = "待审核"
                    styletext = 'style="color:red;"'
                }
                if(audio.status == 1){
                    statusName = "审核通过"
                    styletext = 'style="color:green;"'
                }
                if(audio.status == 2){
                    statusName = "驳回"
                    styletext = 'style="color:#888888;"'
                }
                if(audio.status==0 && new Date((audio.expireTime).replace(/-/g,"/"))<=new Date() && audio.isDelete == -1){
                    statusName = "待审核(已失效)"
                    styletext = 'style="color:#c9c9c9;"'
                }
                tdHtml += "<tr>";
                tdHtml += "<td>" + audio.id + "</td>";
                tdHtml +='<td><a style="color:black;" href = "/anchorManage/toAnchorAudioDetail/'+audio.id+'"><img width="50" height="40" src="'+$("#imgsDomain").val()+JSON.parse(audio.content).coverImg[0]+'"/></a></td>';
                tdHtml +='<td><a style="color:black;" href = "/anchorManage/toAnchorAudioDetail/'+audio.id+'">' + audio.slogan + "</a></td>";
                tdHtml +='<td><a style="color:black;" href = "/anchorManage/toAnchorAudioDetail/'+audio.id+'">' + audio.channelName + "</a></td>";
                tdHtml +='<td><a style="color:black;" href = "/anchorManage/toAnchorAudioDetail/'+audio.id+'">' + audio.creatorNickname + "</a></td>";
                tdHtml +='<td><a style="color:black;" href = "/anchorManage/toAnchorAudioDetail/'+audio.id+'">' + audio.expireTime + "</a></td>";
                tdHtml +='<td '+styletext+'>' + statusName + "</td>";
                if(audio.further != "" && audio.further != null){
                    tdHtml +='<td '+styletext+'>' + JSON.parse(audio.further).reason + "</td>";
                }else {
                    tdHtml +='<td '+styletext+'>' + "" + "</td>";
                }

                tdHtml += "</tr>";
            });
            $('#audioList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#audioListPagination").empty();
                $('#audioList').html("<tr><td colspan='8' style='text-align: center'>暂无数据</td></tr>");
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

