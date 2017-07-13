var pagination;
$(function() {
	var page=1;
	var row=10;
	$("#schoolList").select2({ minimumResultsForSearch: Infinity});
	$("#status").select2({ minimumResultsForSearch: Infinity});
	$("#timeStatus").select2({ minimumResultsForSearch: Infinity});
	pagination = $('#groupListPagination').bootpag({
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
		getEventList(num,row);
	});
});
function toList() {
	if($("#statusDai").val() != "" && $("#statusDai").val() ==0){
		$("#status").val(0)
		$("#status").select2({ minimumResultsForSearch: Infinity});
	}
	getEventList(1, 10);
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
		getEventList(1, 10);
	}
}

function getEventList(page,row){
	var subject = $("#seachText").val();
	var status = $("#status").val();
	var schoolId= $("#schoolList").val();
	var timeStatus = $("#timeStatus").val();
	$.ajax({
		processData: false,
		type: 'POST',
		url: "/admin/event/list",
		contentType: 'application/json',
		data: JSON.stringify({page:page,row:row,subject:subject,status:status,schoolId:schoolId,timeStatus:timeStatus}),
		dataType: 'json',
		success: function(data){
			$('#eventList').empty();
			var tdHtml = "";
			$.each(data.obj.list, function(i, event){
				var statusName="";
				var styletext = "";
				var styleTr ="";
				if(event.status == 0){
					statusName = "待审核"
					styletext = 'style="color:red;"'
				}
				if(event.status == 0 && new Date((event.startTime).replace(/-/g,"/"))<=new Date()){
					statusName = "待审核(已失效)"
					styletext = 'style="color:#c9c9c9;"'
				}
				if(event.status == 1){
					statusName = "审核通过"
					styletext = 'style="color:green;"'
				}
				if(event.status == 2){
					statusName = "审核未通过"
					styletext = 'style="color:#888888;"'
				}
				var timeStatusName ="";
				if(new Date((event.startTime).replace(/-/g,"/"))>new Date()){
					timeStatusName = "未开始";
					if(event.status == 2){
						timeStatusName="";
						// styleTr = 'style="background: #d3d3d3;"';
					}
				}else if(new Date((event.startTime).replace(/-/g,"/"))<=new Date() && new Date((event.endTime).replace(/-/g,"/"))>=new Date()){
					timeStatusName = "进行中";
					if(event.status == 0 || event.status == 2){
						timeStatusName="";
						//styleTr = 'style="background: #d3d3d3;"';
					}
				}else{
					timeStatusName ="已结束"
					if(event.status == 0 || event.status == 2){
						timeStatusName="";
						//styleTr = 'style="background: #d3d3d3;"';
					}
				}
				tdHtml +='<tr '+styleTr+'>';
				tdHtml += "<td class='eventId'>" + event.id + "</td>";
				tdHtml +='<td><a style="color:black;" href = "/admin/event/goDetails/'+event.id+'">' + event.subject + "</a></td>";
				tdHtml +='<td><a style="color:black;" href = "/admin/event/goDetails/'+event.id+'">' + event.schoolName + "</a></td>";
				tdHtml +='<td><a style="color:black;" href = "/admin/event/goDetails/'+event.id+'">' + event.organizeName + "</a></td>";
				tdHtml +='<td><a style="color:black;" href = "/admin/event/goDetails/'+event.id+'">' + event.startTime +"-"+event.endTime + "</a></td>";
				tdHtml +='<td '+styletext+'>' + statusName + "</td>";
				tdHtml += '<td><a style="color:black;" href = "/admin/event/goDetails/' + event.id + '">' + timeStatusName + "</a></td>";
			});
			$('#eventList').html(tdHtml);
			if (data.obj.pages == 0) {
				$("#groupListPagination").empty();
				$('#eventList').html("<tr><td colspan='7' style='text-align: center'>暂无数据</td></tr>");
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

