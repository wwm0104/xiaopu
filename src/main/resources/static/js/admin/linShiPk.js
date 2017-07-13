/**
 * Created by ycy on 2016/11/16.
 */
function initPkData(page,rows) {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/PrizeTake/pkLinShiFinsh",
        contentType: 'application/json',
        data: "",
        dataType: 'json',
        success: function(data){
            $('#pkList').empty();
            console.info(data);
            var tdHtml = "";
            $.each(data.obj, function(i, pk){
                tdHtml += "<tr>";
                tdHtml +="<td>"+pk.id+"</td>"
                tdHtml +="<td>"+pk.slogan+"</td>"
                tdHtml +="<td><input type='button' value='结算' onclick='finishPk("+pk.id+")'/></td>"
                tdHtml +="</tr>";
            });
            $('#pkList').html(tdHtml);
        },
        error: function(err){
            console.log(err)
        }
    });
}

function finishPk(id){
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/prize/finish",
        contentType: 'application/json',
        data: JSON.stringify({id:id}),
        dataType: 'json',
        success: function(data){
            if(data.resultCode == 1){
            layer.msg("结算成功");}
            window.location.reload();
        },
        error: function(err){
            console.log(err)
        }
    });
}