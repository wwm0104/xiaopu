/**
 * Created by Wang on 16/11/08.
 */
var available = 0;
$(function() {
    $("#enable").on('click', function (){
        available = 1;
        update();
    });
    $("#disable").on('click', function (){
        available = 0;
        update();
    });
});

function update(){
    var prizeId = $("#prizeId").val();

    var reqData = {
        prizeId:prizeId,
        available:available
    };
    // console.info(reqData);
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/fight/prize/enableOrDisable",
        contentType: 'application/json',
        data: JSON.stringify(reqData),
        dataType: 'json',
        success: function(data){
            if(data.resultCode==1){
                setTimeout(function (){
                    window.location.href="/admin/fight/prizeDetail/"+prizeId;
                });
            }
        }
    });
}