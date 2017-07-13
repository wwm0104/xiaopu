/**
 * Created by daer on 2016/10/18.
 */

var joinClaim;

$(function () {
});

function changeJoinClaim(type) {
    if (type==joinClaim){
        return false;
    }
    joinClaim = type;
    if (joinClaim==1){
        $("#groupJoinDiv").show();
    } else {
        $("#groupJoinDiv").hide();
        $("#stList").empty();
    }
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

function showGroupModel() {
    groupAndSchool();
    $("#selectGroup").modal('show');
}

function getSchoolList(){
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewgroup/schoolList",
        contentType: 'application/json',
        data: "",
        dataType: 'json',
        success: function(data){
            $('#schoolList').empty();
            var tdHtml = "";
            tdHtml +="<option value=''>所有大学名称</option>";
            $.each(data.obj, function(i, school){
                tdHtml +="<option value="+school.id+">"+school.name+"</option>";
            });
            $('#schoolList').html(tdHtml);
        }
    });
}
function seachList(event) {
    var key = event.which;
    if (key == 13) {
        event.preventDefault();
        groupAndSchool(1,10);
    }
}

function groupAndSchool(){
    var name = $("#searchGroupText").val();
    var schoolId = $("#schoolList").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewgroup/groupNameList",
        contentType: 'application/json',
        data: JSON.stringify({
            name:name,
            schoolId:schoolId
        }),
        dataType: 'json',
        success: function(data){
            $('#groupAndSchool').empty();
            var groupIdList = [];
            var selectList=[];
            $("#stList input[type='text']").each(function(){
                groupIdList[groupIdList.length] = $(this).val();
            });
            $("#groupSelected input[type='text']").each(function(){
                selectList[selectList.length] = $(this).val();
            });
            var tdHtml = "";
            $.each(data.obj, function(i, group){
                if(groupIdList.indexOf(group.id.toString()) == -1 && selectList.indexOf(group.id.toString()) == -1) {
                    tdHtml += "<div onclick='goRight(this)'><input type='text' value='" + group.id + "'style='display: none'/><span style='margin: 0 15px 0 0;'>" + group.name + "</span><span style='float: right'>" + group.schoolName + "</span></div>";
                }
            });
            $('#groupAndSchool').html(tdHtml);
        }
    });
}

function goRight(thisObj){
    var t = $(thisObj).html();
    var tdHtml = "";
    tdHtml +="<div onclick='goLeft(this)'>"+t+"</div>";
    $('#groupSelected').prepend(tdHtml);
    $(thisObj).remove();

}

function goLeft(thisObj){
    var t = $(thisObj).html();
    var tdHtml = "";
    tdHtml +="<div onclick='goRight(this)'>"+t+"</div>";
    $('#groupAndSchool').prepend(tdHtml);
    $(thisObj).remove();
}

function grantFun(){
    var s = $('#groupSelected').html();
    $("#stList").html("");
    $("#stList").append(s);
    $("#selectGroup").modal('hide');
}

function sureCreate(){
    var organizeId =0;
    var organizeName="校谱官方"
    var subject = $("#subject").val();
    var address = $("#address").val();
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    var description = $("#listDescription").val();
    var posterImg = $("#uploadImage1").attr("data-val");
    var contentImgs = "";
    if($("#uploadImage2").attr("data-val") != null && $("#uploadImage2").attr("data-val") != ""){
        contentImgs = $("#uploadImage2").attr("data-val");
    }
    if($("#uploadImage3").attr("data-val") != null && $("#uploadImage3").attr("data-val") != ""){
        contentImgs = contentImgs+","+$("#uploadImage3").attr("data-val");
    }
    if($("#uploadImage4").attr("data-val") != null && $("#uploadImage4").attr("data-val") != ""){
        contentImgs = contentImgs+","+$("#uploadImage4").attr("data-val");
    }
    var img2 = '{"imageWidth":"'+$("#uploadImage2").attr("data-img-width")+'","imageHeight":"'+$("#uploadImage2").attr("data-img-height")+'"}'
    var img3 = '{"imageWidth":"'+$("#uploadImage3").attr("data-img-width")+'","imageHeight":"'+$("#uploadImage3").attr("data-img-height")+'"}'
    var img4 = '{"imageWidth":"'+$("#uploadImage4").attr("data-img-width")+'","imageHeight":"'+$("#uploadImage4").attr("data-img-height")+'"}'

    var furtherList = [];
    if($("#uploadImage2").attr("data-img-width") != null && $("#uploadImage2").attr("data-img-width") != 0 && $("#uploadImage2").attr("data-img-width") != ""){
        furtherList.push(img2);
    }
    if($("#uploadImage3").attr("data-img-width") != null &&$("#uploadImage3").attr("data-img-width") != 0 && $("#uploadImage3").attr("data-img-width") != ""){
        furtherList.push(img3);
    }
    if($("#uploadImage4").attr("data-img-width") != null && $("#uploadImage4").attr("data-img-width") != 0 && $("#uploadImage4").attr("data-img-width") != ""){
        furtherList.push(img4);
    }
    var groupIdList = [];
    $("#stList input[type='text']").each(function(){
        groupIdList[groupIdList.length] = $(this).val();
    });
    var ticket = $("#electronicTicket").val();
    var ticketCnt;
    if(ticket == 1){
        ticketCnt = $("#ticketNum").val();
    }

    if("" == subject){
        layer.msg("请输入活动标题");
        return false;
    }
    if("" == address){
        layer.msg("请输入活动地点");
        return false;
    }
    if("" == startTime){
        layer.msg("请输入活动开始时间");
        return false;
    }
    if("" == endTime){
        layer.msg("请输入活动结束时间");
        return false;
    }
    var sTime = Date.parse(startTime);
    var eTime = Date.parse(endTime);
    if (sTime >= eTime) {
        layer.msg("起始时间不能大于或等于结束时间");
        return false;
    }
    if("" == description){
        layer.msg("请输入活动介绍");
        return false;
    }
    if("" == posterImg){
        layer.msg("请加入活动海报");
        return false;
    }

    var reqData = {
        organizeName:organizeName,
        organizeId:organizeId,
        subject:subject,
        address:address,
        startTime:startTime,
        endTime:endTime,
        description:description,
        posterImg:posterImg,
        contentImgs:contentImgs,
        groupIdList:groupIdList,
        furtherList:furtherList,
        ticket:ticket,
        ticketCnt:ticketCnt
    }
    // console.info(reqData);
    // return false;
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/event/create",
        contentType: 'application/json',
        data: JSON.stringify(reqData),
        dataType: 'json',
        success: function(data){
            if(data.resultCode == 1){
                layer.msg("发布成功");
                window.location.href="/admin/event/official";
            }else{
                layer.msg("发布失败，请稍后再试");
            }
        }
    });



}


/*
 logo 图片上传 / 社团图片上传*/
function file_click(obj) {
    $('#inp_fileToUpload'+obj).click();
}



function uploadtest(fileObj, file_image) {
    layer.load(0, {shade: false});
    var url = "/file/fileUpload";
    var formdata = new FormData();
    var imgDis = $("#imgDisplay").val();
    formdata.append("file", fileObj[0]);
    jQuery.ajax({
        url: url,
        type: 'post',
        data: formdata,
        cache: false,
        contentType: false,
        processData: false,
        dataType: "json",
        success: function (data) {
            layer.closeAll();
            $("#" + file_image).attr("src", imgDis + data.obj);
            //取文本框的值
            $("#" + "_" + file_image).val( data.obj);
        }
    });
    return false;
}