/**
 * Created by xh on 16/11/15.
 */
$(function () {

    $("#uploadImage1").attr("data-val",$("#imgUrl").val());  //修改初始化图片地址

    var sort=$("#sort1").val();
    var type1=$("#type1").val();
    $("#sort").val(sort); //设置当前推荐选中项
    $("#bannerType").val(type1); //设置图片类型选中项


    //判断是否为发现轮播图
    // if(type1==1){
    //     $("#sortA").show();
    // }else{
    //     $("#sortA").hide();
    // }

    $("#bannerType").select2({
        minimumResultsForSearch: Infinity
    });

    $("#sort").select2({
        minimumResultsForSearch: Infinity
    });

    $("#available").select2({
        minimumResultsForSearch: Infinity
    });



    $("#form").validation({icon: true});
    $("#update").on('click', function (){
        if ($("#form").valid(this) == false) {
            return false;
        } else {
            update();
        }
    });
});
function update(){
    var id=$("#bannerId").val();
    var name=$("#name").val();
    var imgUrl = $("#uploadImage1").attr("data-val");
    var description=$("#description").val();
    var slogan=$("#slogan").val();
    var bannerType=$("#bannerType").val();
    var available=$("#available").val();
    var sort=$("#sort").val();
    if(imgUrl=="" || imgUrl==null){
        msg("请上传图片！");
    }else {
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/admin/topics/updateBanner",
            contentType: 'application/json',
            data: JSON.stringify({
                id: id,
                available: available,
                imgUrl: imgUrl,
                description: description,
                slogan: slogan,
                bannerType: bannerType,
                name: name,
                sort: sort
            }),
            dataType: 'json',
            success: function (data) {
                if (data.resultCode == 1) {
                    msg("修改成功！");
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