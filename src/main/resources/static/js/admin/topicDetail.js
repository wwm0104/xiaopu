/**
 * Created by Wang on 16/11/08.
 */
$(function() {
    var topicId = $("#topicId").val();
    var status;
    var reason;
    $("#agree").on("click",function(){
        $('#passModal').modal();
    });
    $("#_agree").on("click",function(){
        status = 1;
        confirm(topicId,status,reason);
    });

    $("#disagree").on("click",function(){
        $('#outModal').modal();
    });
    $("#_disagree").on("click",function(){
        status = 2;
        reason = $("#reason").val();
        if(reason == "" || reason == null){
            layer.msg("请输入驳回原因",{time:1000});
            return false;
        }
        confirm(topicId,status,reason);
    });

});



function confirm(topicId,status,reason) {

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/fight/topic/confirm",
        contentType: 'application/json',
        data: JSON.stringify({topicId:topicId,status:status,reason:reason}),
        dataType: 'json',
        success: function(data){
            if(data.resultCode==1){
                window.location.href="/admin/fight/topicList";
            }
        },
        error: function(err){
            console.log(err)
        }
    });
}
