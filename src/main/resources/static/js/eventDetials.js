function shenhe(status) {
    var id = $("#eventId").val();
    if(status == 1){
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/admin/event/update",
            contentType: 'application/json',
            data: JSON.stringify({id:id,status:status}),
            dataType: 'json',
            success: function(data,status){
                layer.msg('审核通过');
                window.location.href="/admin/event/";
            }
        });
    }
    if(status ==2){
        if($("#listDescription").val() == ""){
            layer.msg("请输入驳回原因");
            return false;
        }
        var memo = $("#listDescription").val();
        // console.info(memo);
        // return false;
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/admin/event/update",
            contentType: 'application/json',
            data: JSON.stringify({id:id,status:status,memo:memo}),
            dataType: 'json',
            success: function(data,status){
                $("#turndownModel").modal('hide');
                layer.msg('已驳回');
                setTimeout("goEventList()", 1500 );
                //window.location.href="/admin/event/";
            }
        });
    }
}
function goEventList() {
    window.location.href='/admin/event/'
}

function tanchuModel() {
    $("#turndownModel").modal('show');
}

$(function () {
    var eventStatus = $("#eventStatus").val();
    if(eventStatus != 0){
        $("#sureButton").hide();
        $("#noSureButton").hide();
    }
    if(eventStatus ==0 && ($("#timeStatusName").val() == "已结束" || $("#timeStatusName").val() == "")){
        $("#statusName").addClass("huiButton");
        layer.msg('活动已过期，不可审核');
        $("#sureButton").removeClass("btn-success");
        $("#noSureButton").removeClass("btn-success");
        $("#sureButton").attr({"disabled":"disabled"});
        $("#noSureButton").attr({"disabled":"disabled"});
    }
    if(eventStatus == 0){
        $("#statusName").addClass("redSpan");
    }
    if(eventStatus == 2){
        $("#statusName").addClass("huiButton");
    }

    $("#top").on("click",function(){
        placedTop();
    });

})

function tanchu(){
    $("#turndowndiv").show();
    $("#imgDiv").hide();
}
function cancel(){
    $("#returndownReason").empty();
    $("#turndowndiv").hide();
    $("#imgDiv").show();

}

function returnList(){
    window.location.href = "/admin/event/"
}

function returnoffList(){
    window.location.href = "/admin/event/official"
}


function divClean() {
    $("#listDescription").val("");
    $("#turndownModel").modal('hide');
}

function placedTop(){
    var id = $("#eventId").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/event/top/"+id,
        dataType: 'json',
        success: function(data){
            console.info(data);
            if(data.resultCode==1){
                layer.msg("置顶成功",{time:1000});
                setTimeout(function (){
                    window.location.href="/admin/event/";
                }, 1000);
            }
        }
    });
}

function officialPlacedTop(){
    var id = $("#eventId").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/event/top/"+id,
        dataType: 'json',
        success: function(data){
            console.info(data);
            if(data.resultCode==1){
                layer.msg("置顶成功",{time:1000});
                setTimeout(function (){
                    window.location.href="/admin/event/official";
                }, 1000);
            }
        }
    });
}