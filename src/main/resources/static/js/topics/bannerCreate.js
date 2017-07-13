/**
 * Created by xh on 16/11/12.
 */
var available=0;
$(function () {

    $("#bannerType").select2({
        minimumResultsForSearch: Infinity
    });

    $("#sort").select2({
        minimumResultsForSearch: Infinity
    });

    $("#form").validation({icon: true});
    $("#create").on('click', function (){
        if ($("#form").valid(this) == false) {
            return false;
        } else {
            create();
        }
    });
    $("#createAndEnable").on('click', function (){
        if ($("#form").valid(this) == false) {
            return false;
        } else {
            available = 1;
            create();
        }
    });
});
function create(){
    var name=$("#name").val();
    var imgUrl = $("#uploadImage1").attr("data-val");
    var description=$("#description").val();
    var slogan=$("#slogan").val();
    var bannerType=$("#bannerType").val();
    var sort=$("#sort").val();
    if(imgUrl=="" || imgUrl==null){
        msg("请上传图片！");
    }else {
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/admin/topics/addBanner",
            contentType: 'application/json',
            data: JSON.stringify({
                available: available,
                imgUrl: imgUrl,
                description: description,
                slogan: slogan,
                bannerType: bannerType,
                name: name,
                sort:sort
            }),
            dataType: 'json',
            success: function (data) {
                if (data.resultCode == 1) {
                    msg("新增成功！");
                    setTimeout(function () {
                        window.location.href = "/admin/topics/toBannerList";
                    }, 1000);
                }
            }
        });
    }
}
// function changeType()
// {
//     var type=$("#bannerType").val();
//     if(type==1){
//         $("#sortA").show();
//     }else{
//         $("#sortA").hide();
//         $("#sort").val(0);
//     }
// }
function msg(data){
    layer.msg(data,{time:2000});
}