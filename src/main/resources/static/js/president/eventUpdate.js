/**
 * Created by Wang on 16/10/17.
 */

$(function() {
    $("#group").select2();
    $("#form").validation({icon: true});

    $("#uploadImage2").attr("data-img-width",$("#width2").val());
    $("#uploadImage3").attr("data-img-width",$("#width3").val());
    $("#uploadImage4").attr("data-img-width",$("#width4").val());

    $("#uploadImage2").attr("data-img-height",$("#height2").val());
    $("#uploadImage3").attr("data-img-height",$("#height3").val());
    $("#uploadImage4").attr("data-img-height",$("#height4").val());

    $("#update").on('click', function (){
        if ($("#form").valid(this) == false) {
            return false;
        } else {
            update();
        }
    });


});

function update(){
    var eventId  = $("#eventId").val();
    var schoolId = $("#schoolId").val();
    var schoolName = $("#schoolName").val();
    var organizeId = $("#group").val();
    var organizeName =$("#group").find("option:selected").text();
    var subject = $("#subject").val();
    var address = $("#address").val();
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    var joinType = $("#joinType").val();
    var description = $("#description").val();
    var posterImg = $("#uploadImage1").attr("value");
    var contentImgs = [$("#uploadImage2").attr("value"),$("#uploadImage3").attr("value"),$("#uploadImage4").attr("value")];

    var further = "[";
    for(var i=2; i<5; i++){
        if($("#uploadImage"+i).attr("data-img-width") != null && $("#uploadImage"+i).attr("data-img-width") != "" ){
            further += '{"imageWidth":"';
            further += $("#uploadImage"+i).attr("data-img-width");
            further += '","imageHeight":"';
            further += $("#uploadImage"+i).attr("data-img-height");
            further += '"},';
        }
    }
    if(further.length>1){
        further = further.substring(0,further.length-1);
    }
    further += "]";

    if(startTime==''){
        msg("请选择活动开始时间");
        return false;
    }
    if(endTime==''){
        msg("请选择活动结束时间");
        return false;
    }

    var sTime = Date.parse(startTime);
    var eTime = Date.parse(endTime);
    if (sTime > eTime) {
        msg("起始时间不能大于结束时间");
        return false;
    }

    if(posterImg==''){
        msg("请上传活动海报");
        return false;
    }

    var reqData = {
        eventId:eventId,
        organizeId:organizeId,
        organizeName:organizeName,
        schoolId:schoolId,
        schoolName:schoolName,
        subject:subject,
        address:address,
        startTime:startTime,
        endTime:endTime,
        joinType:joinType,
        description:description,
        posterImg:posterImg,
        contentImgs:contentImgs,
        further:further
    };
    // console.info(reqData);
    // return false;
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/president/event/update",
        contentType: 'application/json',
        data: JSON.stringify(reqData),
        dataType: 'json',
        success: function(data){
            if(data.resultCode==1){
                msg("提交审核成功！");
                setTimeout(function (){
                    window.location.href="/president/event/list/forward";
                }, 1000);
            }
        }
    });
}

function msg(data){
    layer.msg(data,{time:1000});
}