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
            $.each(data.obj, function(i, school){
                tdHtml +="<option value="+school.id+">"+school.name+"</option>";
            });
            $('#schoolList').html(tdHtml);
        }
    });
}

$(function(){
    $("#schoolList").select2();
    $("#categoryList").select2();
})

function getCategoryList(){
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/group/category",
        contentType: 'application/json',
        data: "",
        dataType: 'json',
        success: function(data){
            $('#categoryList').empty();
            var tdHtml = "";
            $.each(data.obj, function(i, category){
                tdHtml +="<option value="+category.id+">"+category.categoryName+"</option>";
            });
            $('#categoryList').html(tdHtml);
        }
    });
}

function upload(flag){
    var file;
    if(flag == 1){
        file = $("#logofile").val();
    }else if(flag == 2){
        file = $("#logofile").val();
    }else if(flag == 3){
        file = $("#logofile").val();
    }else{
        file = $("#logofile").val();
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/file/fileUpload",
        contentType: 'application/json',
        data: JSON.stringify({file:file}),
        dataType: 'json',
        success: function(data){
            alert(data.resultCode)
        }
    });
}
$(function () {
    $("#schoolNameDis").val($("#schoolList").find("option:selected").text());
    $("#categoryNameDis").val($("#categoryList").find("option:selected").text());
})
function categoryfz(){
    $("#categoryNameDis").val($("#categoryList").find("option:selected").text());
}
function schoolfz(){
    $("#schoolNameDis").val($("#schoolList").find("option:selected").text());
}
function createGroup(){
    var schoolId = $("#schoolList").val();
    var schoolName = $("#schoolList").find("option:selected").text();
    var name = $("#groupName").val();
    var slogan = $("#groupSlogan").val();
    var categoryId = $("#categoryList").val();
    var categoryName = $("#categoryList").find("option:selected").text();
    var description = $("#listDescription").val();
    var logoImgUrl = $("#uploadImage1").attr("data-val");
    var contentImgs = $("#uploadImage2").attr("data-val")+","+$("#uploadImage3").attr("data-val")+","+$("#uploadImage4").attr("data-val");
    var status = 0;

    if("" == schoolName){
        layer.msg("请选择学校");
        return false;
    }
    if("" == name){
        layer.msg("请输入社团名称");
        return false;
    }
    if("" == slogan){
        layer.msg("请输入社团口号");
        return false;
    }
    if("" == description){
        layer.msg("请输入社团介绍");
        return false;
    }
    if("" == logoImgUrl){
        layer.msg("请加入社团logo");
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewgroup/creatGroup",
        contentType: 'application/json',
        data: JSON.stringify({schoolId:schoolId,schoolName:schoolName,name:name,slogan:slogan,categoryId:categoryId,categoryName:categoryName,description:description,logoImgUrl:logoImgUrl,contentImgs:contentImgs,status:status}),
        dataType: 'json',
        success: function(data){
            if(data.resultCode == 1){
                layer.msg("发布成功");
                window.location.href="/admin/reviewgroup/unclaimed"
            }else{
                layer.msg("发布失败");
            }
        }
    });
}


/*
 logo 图片上传 / 社团图片上传*/
function file_click(obj) {
    $('#inp_fileToUpload'+obj).click();
}

$(function () {
    $('#inp_fileToUpload1').change(function () {
        var v_this = $(this);
        var fileObj = v_this.get(0).files;
        uploadtest(fileObj, "file_image1");
        console.info("11111");
        return false;
    });
    $('#inp_fileToUpload2').change(function () {
        var v_this = $(this);
        var fileObj = v_this.get(0).files;
        uploadtest(fileObj, "file_image2");
        console.info("11111");
        return false;
    });
    $('#inp_fileToUpload3').change(function () {
        var v_this = $(this);
        var fileObj = v_this.get(0).files;
        uploadtest(fileObj, "file_image3");
        console.info("11111");
        return false;
    });
    $('#inp_fileToUpload4').change(function () {
        var v_this = $(this);
        var fileObj = v_this.get(0).files;
        uploadtest(fileObj, "file_image4");
        console.info("11111");
        return false;
    });
});


function uploadtest(fileObj, file_image) {
    layer.load(0, {shade: false});
    var url = "/file/fileUpload";
    var formdata = new FormData();
    var imgDis = $("#imgDisplay").val();
    formdata.append("file", fileObj[0]);
    jQuery.ajax({
        url: url,
        type: 'post',
        data: formdata,
        cache: false,
        contentType: false,
        processData: false,
        dataType: "json",
        success: function (data) {
            layer.closeAll();
            $("#" + file_image).attr("src", imgDis + data.obj);
            //取文本框的值
            $("#" + "_" + file_image).val( data.obj);
        }
    });
    return false;
}