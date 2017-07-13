/**
 * Created by Wang on 16/10/17.
 */
var flag = true;
$(function() {
    $("#group").select2();
    $("#form").validation({icon: true});
    $("#publish").on('click', function (){
        if ($("#form").valid(this) == false) {
            return false;
        } else {
            publish();
        }
    });

});

function publish(){

    var schoolId = $("#schoolId").val();
    var schoolName = $("#schoolName").val();
    var organizeId = $("#group").val();
    var organizeName =$("#group").find("option:selected").text();
    var subject = $("#subject").val();
    var address = $("#address").val();
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    var joinType = $("input:radio[name='joinClaim']:checked").val();
    var description = $("#description").val();
    var posterImg = $("#uploadImage1").attr("data-val");
    var contentImgs = [$("#uploadImage2").attr("data-val"),$("#uploadImage3").attr("data-val"),$("#uploadImage4").attr("data-val")];
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

    var ticket = $("#electronicTicket").val();
    var ticketCnt;
    if(ticket == 1){
        ticketCnt = $("#ticketNum").val();
    }

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
        further:further,
        ticket:ticket,
        ticketCnt:ticketCnt
    };
    // console.info(reqData);
    // return false;
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/president/event/publish",
        contentType: 'application/json',
        data: JSON.stringify(reqData),
        dataType: 'json',
        success: function(data){
            if(data.resultCode==1){
                msg("发布成功！");
                setTimeout(function (){
                    window.location.href="/president/event/list/forward";
                }, 1000);

            }
        }
    });
}

function msg(data){
    layer.msg(data,{time:2000});
}

function changeCheckBox() {
    if ( $("#electronicTicket").prop("checked")==true ){
        $("#electronicTicket").val(1);
        $("#ticketNumShow").show();
    }else{
        $("#electronicTicket").val(0);
        $("#ticketNumShow").hide();
    }
}