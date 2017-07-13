/**
 * Created by ycy on 2016/11/7.
 */
$(function(){
    $("#channel").select2();
    $("#tags").select2();
    //$("#prizes").select2();
    $("#periodType").select2({ minimumResultsForSearch: Infinity});
    //$("#prizesType").select2();
})


function createTopic() {
    var channelId = $("#channel").val();
    var channelName = $("#channel").find("option:selected").text();
    var slogan = $("#slogan").val();
    var desc = $("#listDescription").val();
    var eventId =$("#tags").val();
    if(eventId != null &&  eventId !=0 && eventId != ""){
        var eventIdList = [eventId];
    }else{
        var eventIdList = null;
    }
    //var eventName =$("#tags").find("option:selected").text();;
    var prizeId=$("#prizes").val();
    var periodType=$("#periodType").val();
    var type=$("input[type='radio']:checked").val();
    if(type ==1){
        var imgWidth = $("#uploadImage1").attr("data-img-width");
        var imgHeigth = $("#uploadImage1").attr("data-img-height");
    }
    if(type ==2){
        var imgWidth = $("#uploadImage2").attr("data-img-width");
        var imgHeigth = $("#uploadImage2").attr("data-img-height");
    }
    if(type ==1){
        var urls = $("#uploadImage1").attr("data-val");
    }
    if(type == 2){
        var urls = $("#uploadVedio").attr("data-val");
        var urls1 = $("#uploadImage2").attr("data-val");
    }
    var isOfficial = 1;
    var rewardType =1;
    var isChallenger =1;
    var isPk =1;
    if(type ==1){
        var contentJSON = {"slogan":slogan,"desc":desc,"urls":[urls],"imageWidth":imgWidth,"imageHeight":imgHeigth}
    }
    if(type == 2){
        var contentJSON = {"slogan":slogan,"desc":desc,"urls":[urls,urls1],"imageWidth":imgWidth,"imageHeight":imgHeigth}
    }
    if(eventIdList != null && eventIdList.length > 1){
        layer.msg("活动标签暂最多只允许选择一个，请修改");
        return false;
    }
    if("" == slogan){
        layer.msg("请输入来战标题");
        return false;
    }
    if("" == urls || urls == null){
        layer.msg("请上传图片");
        return false;
    }
    if(("" == urls1 || urls1== null) && type == 2){
        layer.msg("请上传视频封面");
        return false;
    }
    if("" == desc){
        layer.msg("请填写内容");
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/adminCreate",
        contentType: 'application/json',
        data: JSON.stringify({"isOfficial":isOfficial,"rewardType":rewardType,"isChallenger":isChallenger,"isPk":isPk,"channelId":channelId,"channelName":channelName,"slogan":slogan,"desc":desc,"contentJSON":contentJSON,"prizeId":prizeId,"eventIdList":eventIdList,"periodType":periodType,"type":type}),
        dataType: 'json',
        success: function(data){
            if(data.resultCode == 1){
                layer.msg("创建成功");
                window.location.href="/admin/topics/toList";
            }else{
                layer.msg("创建失败");
            }
        }
    });
}


function getPrize() {
    $("#prizes").val();
    $("#prizes").html("");
    //$("#prizes").select2();
    var type = $("#prizesType").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/prize/listByType",
        contentType: 'application/json',
        data: JSON.stringify({type:type,isPublic:2}),
        dataType: 'json',
        success: function(data){
            var optionHtml = "";
            if(data.resultCode == 1){
                    $.each(data.obj, function(i, prize){
                        optionHtml +="<option value="+prize.id +">"+prize.name+"</option>"
                    });
                $("#prizes").html(optionHtml);
            }else{
                layer.msg("获取奖品失败");
            }
        }
    });

}


function showImg() {
    $("#uploadImage1").show();
    $("#uploadVedio").hide();
    $("#uploadImage2").hide();
}
function showVedio() {
    $("#uploadImage1").hide();
    $("#uploadVedio").show();
    $("#uploadImage2").show();
}

//
// function addtags(){
//     var tdHtml = "";
//     var eventId =$("#tags").val();
//     var eventName =$("#tags").find("option:selected").text();
//     var eventIdList = [];
//     $("#tagsList input[type='text']").each(function(){
//         eventIdList[eventIdList.length] = $(this).val();
//     });
//     if(eventIdList.indexOf(eventId) >= 0){
//         layer.msg("选择已存在！");
//         return false;
//     }
//     tdHtml +="<div onclick='delete1(this)' class='col-sm-4'><input type='text' value='"+eventId+"'style='display: none'/><span style='margin: 0 15px 0 0;'>"+eventName+"</span></div>";
//     $("#tagsList").prepend(tdHtml);
// }
//
// function delete1(thisObj) {
//     thisObj.remove();
// }