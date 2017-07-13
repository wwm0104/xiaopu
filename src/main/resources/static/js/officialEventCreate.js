function createOfficeEvent(){

}

/***创建活动图片上床*/
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
            $("#" + file_image).attr("src", "http://imgs1.chinaxiaopu.com/" + data.obj);
            //取文本框的值
            $("#" + "_" + file_image).val("http://imgs1.chinaxiaopu.com/" + data.obj);


        }
    });
    return false;
}