var $select;
var urls = [];
/*var pagination;*/
$(function () {

    /**
     * select2 加载事件
     * 使用方法 ：<select id="student" name="student" multiple="true" style="width:257px;"
     class="form-control  required  item_student _select">
     * @type {any}
     *
     *  multiple="true"
     *  _select
     *
     */
    $select = $("._select").select2({minimumResultsForSearch: Infinity});
    var message = $("#message").text();
    if (message != "" && message != "msg") {
        $.jBox.tip(message);
    }

    function progress(b) {
        layer.open({
            area: ['1000px', '40px'],
            scrollbar:false,
            type: 1,
            title: false,
            closeBtn: 0,
            shadeClose: true,
            skin: 'yourclass',
            content: "<div class='progress'> <div class='progress-bar' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:"+b+"%;'>' <span class='sr-only'>"+b+"% 完成</span></div></div>"
        });
    }

    $(".dropzoneImgUpload").dropzone({
        url: "/file/fileUpload",
        maxFiles: 10,
        maxFilesize: 50,
        acceptedFiles: ".png,.jpg",
        /*previewTemplate: document.querySelector('#preview-template').innerHTML,*/
        success: function (data) {
            imgUpload(data,this);
        },
      /* uploadprogress:function(a,b,c){
           /!*layer.load(3);*!/
           progress(b);

        },*/
    });

    $(".dropzoneVideoUpload").dropzone({
        url: "/file/videoUpload",
        maxFiles: 10,
        maxFilesize: 512,
        acceptedFiles: ".mp4",
        success: function (data) {
            videoUpload(data,this);
        }
    });


    $(".dropzoneAudioUpload").dropzone({
        url: "/file/audioUpload",
        maxFiles: 10,
        maxFilesize: 512,
        acceptedFiles: ".mp3",
        success: function (data) {
            audioUpload(data);
        }
    });

    /**
     * 子菜单 弹开
     *  1：点击href  放入COOKIE中
     *
     *  2：取值 设置样式
     *
     *  注 ： 有BUG
     */
    /* $(".sidebar ul li ul li a").click(function(){
     var _this = $(this);
     alert("保存cookie"+_this.attr("href"));
     $.jquery-cookie("_path",_this.attr("href"));
     alert("保存成功cookie"+$.jquery-cookie("_path"));
     });
     var path = $.jquery-cookie("_path");
     if(path != null){
     $(".sidebar ul li ul li a").each(function(){
     var _this = $(this);
     if(_this.attr("href") == path){
     alert(path);
     _this.parent().parent().addClass("menu-open");
     _this.parent().parent().parent().addClass("active");
     }
     });
     }*/
    /**
     * 子菜单 弹开
     *
     *获取路径
     *
     */
    getAllUrl();
    var url = window.location.pathname;
    $(".sidebar ul li ul li a").each(function () {
        var _this = $(this);
        if ($.inArray(url, urls) != -1) {
            if (_this.attr("href") == url && url != '/admin/index' && url != '/president/index') {
                $.cookie("_urlPath", url);
                /*alert($.inArray(url, urls)+"==="+$.cookie("_urlPath"));*/
                _this.parent().parent().addClass("menu-open");
                _this.parent().parent().parent().addClass("active");
                $("._active").removeClass("active")
                return false;
            }
        } else {
            var _url = $.cookie("_urlPath");
           if (_this.attr("href") == _url && _url != '/admin/index' && _url != '/president/index') {
                _this.parent().parent().addClass("menu-open");
                _this.parent().parent().parent().addClass("active");
                $("._active").removeClass("active");
                return false;
            }
        }
    });
    /**
     *
     */
    function getAllUrl() {
        $(".sidebar a").each(function () {
            var url = $(this).attr("href");
            if (url != "#") {
                urls.push(url);
            }

        });

    }


    /**
     * 分页jquery
     * @type {number}
     */
    /*var page = 1;
     var row = 10;
     pagination = $('#Pagination').bootpag({
     total: 0,          // total pages
     page: 1,            // default page
     maxVisible: 10,     // visible pagination
     firstLastUse: true,
     prev: '上一页',
     next: '下一页',
     first: '首页',
     last: '末页',
     leaps: true         // next/prev leaps through maxVisible
     }).on("page", function (event, num) {
     getGroupList(num, row);
     });*/

});
function imgUpload(data,_this) {
    var img =new Image();
    var id = _this.element.id;
    $("#" + id).attr("src", JSON.parse(data.xhr.response).path + JSON.parse(data.xhr.response).obj);
    $("#" + id).attr("value", JSON.parse(data.xhr.response).obj);
    $("#" + id).attr("data-val", JSON.parse(data.xhr.response).obj);
    $("#" + id).attr("data-img-width", data.width);
    $("#" + id).attr("data-img-height", data.height);
   /* layer.closeAll();*/
}

function videoUpload(data,_this) {
    var id = _this.element.id;
    $("#" + id).attr("src", JSON.parse(data.xhr.response).path + JSON.parse(data.xhr.response).obj);
    $("#" + id).attr("value", JSON.parse(data.xhr.response).obj);
    $("#" + id).attr("data-val", JSON.parse(data.xhr.response).obj);

}


function audioUpload(data) {
    //var id = _this.element.id;
    $("#uploadAudio").attr("src", JSON.parse(data.xhr.response).path + JSON.parse(data.xhr.response).obj);
    $("#uploadAudio").attr("value", JSON.parse(data.xhr.response).obj);
    $("#uploadAudio").attr("data-val", JSON.parse(data.xhr.response).obj);
    $(".dz-success-mark").hide();
    $(".dz-error-mark").hide();
}
// Dropzone.options.myAwesomeDropzone = false;
/**
 * 图片上传事件封装
 *
 * 使用方法：
 *<div class="col-sm-2 dropz">
 <img id="uploadImage1" th:src="@{/img/cjhdtianjia.png}" height="164" width="177"  th:value="@{e.shortName}" onclick="upload(this)"/>
 </div>
 *1: div 中添加dropz  calss样式
 * 2：点击事件 获取id 并加载插件
 *
 * @param _this
 */
// function upload(_this) {
//     Dropzone.autoDiscover = false;
//     console.log(1111);
//     var id = $(_this).attr("id");
//     var num = $(_this).attr("data-img-val");
//     $("#" + id).dropzone({
//         url: "/file/fileUpload",
//         maxFiles: 10,
//         maxFilesize: 50,
//         acceptedFiles: ".png,.jpg",
//         success: function (data) {
//             console.info(data);
//             console.info(data.width, data.height);
//             $("#width"+num).val(data.width);
//             $("#height"+num).val(data.height);
//             $("#" + id).attr("src", JSON.parse(data.xhr.response).path + JSON.parse(data.xhr.response).obj);
//             $("#" + id).attr("value", JSON.parse(data.xhr.response).obj);
//             $("#" + id).attr("data-val", JSON.parse(data.xhr.response).obj);
//             //     $("#" + id).parent().parent().removeClass('has-error').addClass('has-success');//glyphicon-ok
//             //     $("#" + id).next().removeClass('glyphicon-remove').addClass('glyphicon-ok').attr("style", "display:block;");
//             //     //alert($("#" + id).next());
//             //     $("#" + id).next().next().attr("style", "display:none;");
//             //     $("#" + id).next().next().attr("data-bv-result",'VALID');
//             //     //alert($("#" + id).next().next());
//             //     $(".btn").removeAttr("disabled");
//         }
//     });
// }
//
//
// function videoUpload(_this) {
//     var id = $(_this).attr("id");
//     $("#" + id).dropzone({
//         url: "/file/videoUpload",
//         maxFiles: 10,
//         maxFilesize: 512,
//         acceptedFiles: ".mp4",
//         success: function (data) {
//             console.info(data.xhr.response);
//             console.info(JSON.parse(data.xhr.response).obj);
//             $("#" + id).attr("src", JSON.parse(data.xhr.response).path + JSON.parse(data.xhr.response).obj);
//             $("#" + id).attr("value", JSON.parse(data.xhr.response).obj);
//             $("#" + id).attr("data-val", JSON.parse(data.xhr.response).obj);
//             // $("#" + id).parent().parent().removeClass('has-error').addClass('has-success');//glyphicon-ok
//             // $("#" + id).next().removeClass('glyphicon-remove').addClass('glyphicon-ok').attr("style", "display:block;");
//             // $("#" + id).next().next().attr("style", "display:none;");
//             // $("#" + id).next().next().attr("data-bv-result",'VALID');
//             // $(".btn").removeAttr("disabled");
//         }
//     });
// }











