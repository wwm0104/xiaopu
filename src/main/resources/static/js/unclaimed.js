var pagination;
$(function() {
	var page=1;
	var row=10;
	$("#schoolList").select2({ minimumResultsForSearch: Infinity});
	$("#creater").select2({ minimumResultsForSearch: Infinity});
	getUnclaimedGroupList(page,row);
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
		getUnclaimedGroupList(num,row);
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
		getUnclaimedGroupList(1, 10);
	}
}
function getUnclaimedGroupList(page,row){
	$("#allchecked").attr("checked", false); //重置全选为取消状态
	var name = $("#seachText").val();
	var status = 0;
	var schoolId= $("#schoolList").val();
	var createrStatus = $("#creater").val();
	$.ajax({
		processData: false,
		type: 'POST',
		url: "/admin/reviewgroup/unclamiledList",
		contentType: 'application/json',
		data: JSON.stringify({page:page,row:row,name:name,status:status,schoolId:schoolId,createrStatus:createrStatus}),
		dataType: 'json',
		success: function(data){
			$('#groupList').empty();
			var tdHtml = "";
			$.each(data.obj.list, function(i, group){
				tdHtml +='<tr><td><input type ="checkbox" class="checkbox" name="checkbox" value="'+group.id+'"/></td>'
				tdHtml += "<td class='groupId'>" + group.id + "</td>";
				tdHtml +="<td>" + group.name + "</td>";
				tdHtml +="<td>" + group.schoolName + "</td>";
				tdHtml +="<td>" + group.joinTime + "</td>";
				tdHtml +="<td>" + (group.presidentName || "平台创建") + "</td>";
			});
			$('#groupList').html(tdHtml);
			if (data.obj.pages == 0) {
				$("#groupListPagination").empty();
				$("#checkBoxId").hide();
				$("#delete").hide();
				$('#groupList').html("<tr><td colspan='6' style='text-align: center'>暂无数据</td></tr>");
			} else {
				$("#checkBoxId").show();
				$("#delete").show();
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

function deleteInfo(){
	$("#delcfmModel").modal("show");
}

function deletegroup(){
	$("#delcfmModel").modal("hide");
	var idList=[];
	var arrChk=$("input[name='checkbox']:checked");
	$(arrChk).each(function(){
		idList[idList.length] = this.value;
	});
	$.ajax({
		processData: false,
		type: 'POST',
		url: "/admin/reviewgroup/deleteGroup",
		contentType: 'application/json',
		data: JSON.stringify({idList:idList}),
		dataType: 'json',
		success: function(data){
			layer.alert("删除成功");
			getUnclaimedGroupList(1,10)
		}
	});
}