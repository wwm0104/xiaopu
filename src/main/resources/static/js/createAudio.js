/**
 * Created by ycy on 2016/12/6.
 */

Date.prototype.Format = function(fmt)
{ //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

function secToTime(time) {
    var timeStr;
    var hour = 0;
    var minute = 0;
    var second = 0;
    if (time <= 0)
        return "00:00";
    else {
        minute = parseInt(time / 60);
        if (minute < 60) {
            second = time % 60;
            timeStr = unitFormat(minute) + ":" + unitFormat(second);
        } else {
            hour = parseInt(minute / 60);
            if (hour > 99)
                return "99:59:59";
            minute = minute % 60;
            second = time - hour * 3600 - minute * 60;
            timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
        }
    }
    return timeStr;
}

function unitFormat(i) {
    var retStr;
    if (i >= 0 && i < 10)
        retStr = "0" + i;
    else
        retStr = "" + i;
    return retStr;
}


function createAudio() {
    var channelId = $("#channel").val();
    var channelName = $("#channel").find("option:selected").text();
    var slogan = $("#slogan").val();
    var desc = $("#listDescription").val();
    var isDelete = $(":radio[name='isDelete']:checked").val();
    var startTime = $("#startTime").val();
    var audioTime=parseInt(document.getElementById("uploadAudio").duration);

    var str = "";
    // if(audioTime < 60){
    //     str = "0:" + ( audioTime%60<10?("0"+audioTime%60):audioTime%60 );
    // }else if(audioTime >= 60 && audioTime < 3600){
    //     str = ""+( parseInt(audioTime/60) + ":" + audioTime%60==0?"00":(audioTime%60<10? ("0"+audioTime%60) :audioTime%60) );
    // }else if(audioTime>=3600){
    //     str = ""+ ( parseInt(audioTime/3600)
    //     +":"
    //     +parseInt((audioTime%3600)/60)==0?"00":(parseInt((audioTime%3600)/60)<10?"0"+parseInt((audioTime%3600)/60):parseInt((audioTime%3600)/60))
    //     +":"
    //     +(audioTime%3600)%60==0?"00":((audioTime%3600)%60<10?"0"+(audioTime%3600)%60:(audioTime%3600)%60) );
    // }
    //
    str = secToTime(audioTime);
    if(audioTime == null || audioTime ==""){
        str = "0";
    }
    //音频路径
    var urls = $("#uploadAudio").attr("data-val");

    //banner路径
    var url1 = $("#uploadImage1").attr("data-val");
    var url2 = $("#uploadImage2").attr("data-val");
    var url3 = $("#uploadImage3").attr("data-val");

    //介绍图片路径
    var url4 = $("#uploadImage4").attr("data-val");

    //介绍音频封面
    var url5 = $("#uploadImage5").attr("data-val");

    var imgWidth = $("#uploadImage4").attr("data-img-width");
    var imgHeigth = $("#uploadImage4").attr("data-img-height");





    var furtherList = [];
    if($("#uploadImage1").attr("data-img-width") != null && $("#uploadImage1").attr("data-img-width") != 0 && $("#uploadImage1").attr("data-img-width") != ""){
        furtherList.push(url1);
    }
    if($("#uploadImage2").attr("data-img-width") != null &&$("#uploadImage2").attr("data-img-width") != 0 && $("#uploadImage2").attr("data-img-width") != ""){
        furtherList.push(url2);
    }
    if($("#uploadImage3").attr("data-img-width") != null && $("#uploadImage3").attr("data-img-width") != 0 && $("#uploadImage3").attr("data-img-width") != ""){
        furtherList.push(url3);
    }

    var isOfficial = 0;
    //var rewardType =1;
    var isChallenger =-1;
    var isPk =0;
    var contentJSON = {"slogan":slogan,"desc":desc,"posterImgs":[url4],"coverImg":[url5],"bannerImgs":furtherList,"urls":[urls],"audioTime":str,"imageWidth":imgWidth,"imageHeight":imgHeigth}
    if(urls == null || urls == ""){
        layer.msg("请上传音频");
        return false;
    }
    if(url4 == null || url4 == ""){
        layer.msg("请上传音频介绍图片");
        return false;
    }

    if(url5 == null || url5 == ""){
        layer.msg("请上传音频封面图片");
        return false;
    }
    if(isDelete==-1) {
        if (startTime == null || startTime == "") {
            layer.msg("请选择上线时间");
            return false;
        }
        if (new Date(startTime).getTime() <= new Date().getTime()) {
            layer.msg("上线时间必须大于当前时间");
            return false;
        }
    }

    var reqData = {
        "onlineTime":startTime,
        "type":3,
        "isDelete":isDelete,
        "isOfficial":isOfficial,
        "isChallenger":isChallenger,
        "isPk":isPk,
        "channelId":channelId,
        "channelName":channelName,
        "slogan":slogan,
        "desc":desc,
        "audioContentJSON":contentJSON
    };



    $.ajax({
        processData: false,
        type: 'POST',
        url: "/anchorManage/create",
        contentType: 'application/json',
        data: JSON.stringify(reqData),
        dataType: 'json',
        success: function(data){
            if(data.resultCode == 1){
                layer.msg("创建成功");
                window.location.href="/anchorManage/toAnchorAudioList";
            }else{
                layer.msg("创建失败");
            }
        }
    });
}

function changeCheckBox() {

    if ( $(":radio[name='isDelete']:checked").val()==1 ){
        $("#onLineTimeDiv").hide();
        $("#startTime").val(new Date().Format("yyyy/MM/dd hh:mm"));
    }else{
        $("#onLineTimeDiv").show();
        $("#startTime").val(null);
    }
}