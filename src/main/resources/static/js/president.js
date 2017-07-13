var pagination;
$(function() {
	var page=1;
	var row=10;
	getPresidentList(page,row);
	$("#schoolList").select2({ minimumResultsForSearch: Infinity});
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
		getPresidentList(num,row);
	});
});

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
		getPresidentList(1,10);
	}
}

function getPresidentList(page,row){
	var name = $("#seachText").val();
	var status = 0;
	var schoolId= $("#schoolList").val();
	$.ajax({
		processData: false,
		type: 'POST',
		url: "/admin/reviewgroup/getPresidentList",
		contentType: 'application/json',
		data: JSON.stringify({page:page,row:row,realName:name,schoolId:schoolId}),
		dataType: 'json',
		success: function(data){
			$('#presidentList').empty();
			var tdHtml = "";
			$.each(data.obj.list, function(i, president){
				tdHtml += "<tr><td class='president'>" + president.realName + "</td>";
				tdHtml +="<td>" + president.schoolName + "</td>";
				tdHtml +="<td>" + president.allGroup + "</td>";
				tdHtml +="<td>" + president.mobile + "</td>";
				tdHtml +="<td>" + president.qq + "</td>";
				tdHtml +="<td>" + president.studentNo + "</td>";
			});
			$('#presidentList').html(tdHtml);
			if (data.obj.pages == 0) {
				$("#groupListPagination").empty();
				$('#presidentList').html("<tr><td colspan='6' style='text-align: center'>暂无数据</td></tr>");
			}else{
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


