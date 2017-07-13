var pagination;
$(function() {
	var page=1;
	var row=10;

	getEventLotteryList(page,row);

	pagination = $('#eventLotteryListPagination').bootpag({
		total: 0,          // total pages
		page: 1,            // default page
		maxVisible: 10,     // visible pagination
		firstLastUse: true,
		prev: '上一页',
		next: '下一页',
		first: '首页',
		last: '末页',
		leaps: true         // next/prev leaps through maxVisible
	}).on("page", function(event, num){
		getEventLotteryList(num,row);
	});
});

function getEventLotteryList(page,row){
	$.ajax({
		processData: false,
		type: 'POST',
		url: "/eventLottery/event/list",
		contentType: 'application/json',
		data: JSON.stringify({page:page,row:row}),
		dataType: 'json',
		success: function(data){
			$('#eventLotteryList').empty();
			var tdHtml = "";
			$.each(data.obj.list, function(i, eventLottery){
				tdHtml += "<tr>";
				tdHtml += "<td>" + eventLottery.id + "</td>";
				tdHtml += "<td>" + eventLottery.eventName + "</td>";
				if(eventLottery.stauts==0){
					tdHtml += "<td ><button type=\"button\" style=\"margin:auto;width: 85px; font-size: 14px;\" class=\"btn btn-block true-color\">活动抽奖</button></td>";
				}else{
					tdHtml += "<td ><button type=\"button\" style=\"margin:auto;width: 85px; font-size: 14px;\" class=\"btn btn-block true-color\">抽奖结果</button></td>";
				}
				tdHtml +="</tr>";
			});
			$('#eventLotteryList').html(tdHtml);

			if (data.obj.pages == 0) {
				$("#eventLotteryListPagination").empty();
				$('#eventLotteryList').html("<tr><td colspan='3' style='text-align: center'>暂无数据</td></tr>");
			} else {
				pagination.bootpag({
					total: data.obj.pages,          // total pages
					page: data.obj.pageNum,            // default page
					maxVisible: 10,     // visible pagination
					firstLastUse: true,
					prev: '上一页',
					next: '下一页',
					first: '首页',
					last: '末页',
					leaps: true         // next/prev leaps through maxVisible
				});
			}
		}
	})
}

