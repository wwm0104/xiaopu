
/**
 * Created by xh on 2016/11/12.
 */
var pagination;
$(function() {


    $("#bannerType").select2({
        minimumResultsForSearch: Infinity
    });



    var page=1;
    var row=10;
    getBannerList(page,row);
    pagination = $('#bannerListPagination').bootpag({
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
        getBannerList(num,row);
    });
});

//跳转至添加图片界面
function toAddBanner(){
    window.location.href="/admin/topics/toAddBanner";
}
//跳转至修改图片界面
function toUpdateBanner(id){
    window.location.href="/admin/topics/getBannerById/"+id;
}
//删除提示框
function del(a){
    $("#delModel").modal("show");
    $("#bannerId").val(""); //先清空
    $("#bannerId").val(a);
}

function getBannerList(page,row){
    var bannerType=$("#bannerType").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/getBannerList",
        contentType: 'application/json',
        data: JSON.stringify({page:page,row:row,bannerType:bannerType}),
        dataType: 'json',
        success: function(data){
            $('#bannerList').empty();
            var tdHtml = "";
            var tuijian="";
            var type="";
            $.each(data.obj.list, function(i, banner){
                tdHtml +='<tr>';
                tdHtml += "<td>" + (banner.name || "") + "</td>";

                if(banner.bannerType==6){
                    type="达人榜";
                }
                if(banner.bannerType==5){
                    type="不服榜";
                }
                if(banner.bannerType==4){
                    type="投票排行榜";
                }
                if(banner.bannerType==3){
                    type="活动排行榜";
                }
                if(banner.bannerType==2){
                    type="音频轮播图";
                }
                if(banner.bannerType==1){
                    type="发现轮播图";
                }

                tdHtml += "<td>" + type + "</td>";
                tdHtml += "<td>" + (banner.slogan||"") + "</td>";
                tdHtml += "<td>" + (banner.description||"") + "</td>";
                tdHtml +="<td><img style='width: 50px;height: 30px' src='"+banner.imgsHostDomain+banner.imgUrl+"'/></img></td>";

                if(banner.sort==5){
                    tuijian="NO.1";
                }
                if(banner.sort==4){
                    tuijian="NO.2";
                }
                if(banner.sort==3){
                    tuijian="NO.3";
                }
                if(banner.sort==2){
                    tuijian="NO.4";
                }
                if(banner.sort==1){
                    tuijian="NO.5";
                }
                if(banner.sort==0){
                    tuijian="无";
                }
                tdHtml +="<td>" + tuijian + "</td>";
                // <td><strong>NO.4</strong><button type='button' class='btn bg-color btn-xs' onclick='tuijian(" + topic.id + "," + topic.recommend + ")'>推荐</button></td>
                if(banner.available == 1){
                    tdHtml +="<td><input type='button' class='btn tinyong-bg btn-sm' id='tiyong' value='停用' onclick='updateBanner("+banner.id+",0)'><input type='button' class='btn qiyong-bg btn-sm' id='huifu' value='恢复' style='display: none' onclick='updateBanner("+banner.id+",1)'><input type='button' style='margin-left: 2px;' class='btn xiugai-bg btn-sm' id='toupdate' value='修改' onclick='toUpdateBanner("+banner.id+")'><input type='button' style='margin-left: 2px;' class='btn tinyong-bg btn-sm' value='删除' onclick='del("+banner.id+")'></td>>"

                }else{
                    tdHtml +="<td><input type='button' class='btn tinyong-bg btn-sm' id='tiyong' value='停用' style='display: none' onclick='updateBanner("+banner.id+",0)'><input type='button' class='btn qiyong-bg btn-sm' id='huifu' value='恢复' onclick='updateBanner("+banner.id+",1)'><input type='button' style='margin-left: 2px;' class='btn xiugai-bg btn-sm' id='toupdate' value='修改' onclick='toUpdateBanner("+banner.id+")'><input type='button' style='margin-left: 2px;' class='btn tinyong-bg btn-sm' value='删除' onclick='del("+banner.id+")'></td>>"
                    }
                            });
            $('#bannerList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#bannerListPagination").empty();
                $('#bannerList').html("<tr><td colspan='7' style='text-align: center'>暂无数据</td></tr>");
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

//停用启用
function updateBanner(id,available){
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/availableBanner",
        contentType: 'application/json',
        data: JSON.stringify({id:id,available:available}),
        dataType: 'json',
        success: function(data){
            if(data.resultCode == 1){
                getBannerList(1,10);
                layer.msg("成功")
            }else {
                layer.msg("修改失败")
            }
        }
    });
}

//删除
function delBanner(){
    var id=$("#bannerId").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/delBanner",
        contentType: 'application/json',
        data: JSON.stringify({id:id}),
        dataType: 'json',
        success: function(data){
            if(data.resultCode == 1){
                getBannerList(1,10);
                layer.msg("成功")
            }else {
                layer.msg("修改失败")
            }
        }
    });
}





