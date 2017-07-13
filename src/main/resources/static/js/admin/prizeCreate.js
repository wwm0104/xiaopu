/**
 * Created by Wang on 16/11/07.
 */
var flag = true;
var available = 0;
$(function() {
    $("#prizeType").select2({
        minimumResultsForSearch: Infinity
    });
    $("#prizeType").on("change",function(){
        if($(this).val()==1){
            $("#moneyDiv").show();
        }else{
            $("#moneyDiv").hide();
        }
    });
    $("#form").validation({icon: true});
    $("#create").on('click', function (){
        if ($("#form").valid(this) == false) {
            return false;
        } else {
            create();
        }
    });
    $("#createAndEnable").on('click', function (){
        if ($("#form").valid(this) == false) {
            return false;
        } else {
            available = 1;
            create();
        }
    });

});

function create(){
    var money;
    var type = $("#prizeType").val();
    if(type==1){
        money = $("#money").val();
    }
    var name = $("#name").val();
    var desc = $("#desc").val();
    var stockTotal = $("#stockTotal").val();
    var availableTime = $("#availableTime").val();
    var expireTime = $("#expireTime").val();
    var challengeCnt = $("#challengeCnt").val();
    var voteCnt = $("#voteCnt").val();
    var status = $("input[name='status']:checked").val()==null?"0":$("input[name='status']:checked").val();
    var isPublic = $("input[name='isPublic']:checked").val()==null?"0":$("input[name='isPublic']:checked").val();
    var imgs = [$("#uploadImage1").attr("data-val")];

    // if(availableTime==''){
    //     msg("请选择活动开始时间");
    //     return false;
    // }
    // if(expireTime==''){
    //     msg("请选择活动结束时间");
    //     return false;
    // }
    //
    // var sTime = Date.parse(availableTime);
    // var eTime = Date.parse(expireTime);
    // if (sTime > eTime) {
    //     msg("起始时间不能大于结束时间");
    //     return false;
    // }

    var reqData = {
        type:type,
        name:name,
        money:money,
        stockTotal:stockTotal,
        availableTime:availableTime,
        expireTime:expireTime,
        isPublic:isPublic,
        challengeCnt:challengeCnt,
        voteCnt:voteCnt,
        imgs:imgs,
        desc:desc,
        status:status,
        available:available
    };
    // console.info(reqData);
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/fight/prize/create",
        contentType: 'application/json',
        data: JSON.stringify(reqData),
        dataType: 'json',
        success: function(data){
            if(data.resultCode==1){
                msg("新增成功！");
                setTimeout(function (){
                    window.location.href="/admin/fight/prizeList";
                }, 1000);
            }
        }
    });
}

function msg(data){
    layer.msg(data,{time:2000});
}