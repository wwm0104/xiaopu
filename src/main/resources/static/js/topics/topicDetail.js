$(function(){
    //图片轮播

    var urls = $("#urls").val();
    var path =$("#path").val();
    //alert(urls);
    urls = urls.substring(1,urls.length-1).split(",");
    var _tr="";
    var _br="";
    $.each(urls, function(index,value){
        if(urls.length==1){
            _br+="<div class='item active'><img src='"+path+$.trim(value)+"'  style='width:100%;height: 100%;max-height: 180px;min-width: 170px;'/> <div class='carousel-caption'></div></div>";
        }else{
            $("#left").show();
            $("#right").show();
            if(index ==0){
                _tr+="<li data-target='' data-slide-to='"+index+"' class='active'></li>";
                _br+="<div class='item active'><img src='"+path+$.trim(value)+"'  style='width:100%;height: 100%;max-height: 180px;min-width: 170px;'/> <div class='carousel-caption'></div></div>";
            }else{
                _tr+="<li data-target='' data-slide-to='"+index+"'></li>";
                _br+="<div class='item'><img src='"+path+$.trim(value)+"'  style='width:100%;height: 100%;max-height: 180px;min-width: 170px;'/> <div class='carousel-caption'></div></div>";
            }
        }

    });
    $("#example").append(_tr);
    $("#images").append(_br);


    //初始化隐藏
        $("#deltopic").hide();
        $("#delcomment").hide();
        $("#deltopoff").hide();
});
//打开删除框
function openDelete(no,id,ctid){
    if(no==1){
        $("#deltopic").show();
        $("#delcomment").hide();
        $("#deltopoff").hide();
        $("#tid").val(id);
    }
    if(no==2){
        $("#delcomment").show();
        $("#deltopic").hide();
        $("#deltopoff").hide();
        $("#cid").val(id);
        $("#ctid").val(ctid);
    }
    if(no==3){
        $("#deltopoff").show();
        $("#deltopic").hide();
        $("#delcomment").hide();
        $("#tfid").val(id);
    }
    $("#deltopicModel").modal("show");
}


function deleteTopic() {
    var topicId = $("#tid").val();
    if (topicId == "") {
        layer.msg("删除失效");
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/deleteTopic",
        contentType: 'application/json',
        data: JSON.stringify({
            id: topicId
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("删除成功")
                window.location.href = "/admin/topics/toList";
            } else {
                layer.msg("删除失败！")
            }

        }
    });
}


function deleteComment() {
    var obj=$("#cid").val();
    var topicId=$("#ctid").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/deleteComment",
        contentType: 'application/json',
        data: JSON.stringify({
            id: obj,
            topicId:topicId
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("删除成功")
                location.reload();
            } else {
                layer.msg("删除失败！")
            }

        }
    });
}
function deleteTipOff(){
    var obj=$("#tfid").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/deleteTipOff",
        contentType: 'application/json',
        data: JSON.stringify({
            id: obj
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("删除成功")
                location.reload();
            } else {
                layer.msg("删除失败！")
            }

        }
    });

}


//打开不显示框
function openhide(obj){
    $("#hideModel").modal("show");
    $("#hideid").val(obj);
}

function updateComment() {
    var obj=$("#hideid").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/updateComment",
        contentType: 'application/json',
        data: JSON.stringify({
            id: obj
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("处理成功")
                location.reload();
            } else {
                layer.msg("处理失败！")
            }

        }
    });

}


