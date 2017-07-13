/**
 * Created by Wang on 16/10/17.
 */
var pagination;
$(function() {
    // initData();
});

function initData() {
    $.ajax({
        processData: false,
        type: 'GET',
        url: "/president/index",
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            $('#eventApplyCount').empty();
            // var tdHtml = "";
            // tdHtml += "<tr><td>" + data.eventApplyCount + "</td>";
            // tdHtml +="<td>" +" 待处理活动申请 "+ "</td></tr>";
            // $('#eventApplyCount').html(tdHtml);
        },
        error: function(err){
            console.log(err)
        }
    });
}
