/**
 * Created by wyh on 2016/12/6.
 */
var pagination;
$(function () {
    var page = 1;
    var row = 10;
    getAudioList(page, row);
    $("#status").select2({minimumResultsForSearch: Infinity});
    // $("#timeStatus").select2({ minimumResultsForSearch: Infinity});
    pagination = $('#audioListPagination').bootpag({
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
        getAudioList(num, row);
    });
});

function seachList(event) {
    var key = event.which;
    if (key == 13) {
        event.preventDefault();
        getAudioList(1, 10);
    }
}

function toDetail(id) {
    window.location.href = 'toAudioDetail/' + id;
}


function getAudioList(page, row) {
    var slogan = $("#seachText").val();
    var status = $("#status").val();
    var topSort = $("#topSort").attr("data-val");
    var Sort = $("#recommentSort").attr("data-val");
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/adminAudio/audioCheckList",
        contentType: 'application/json',
        data: JSON.stringify({page: page, row: row, slogan: slogan, status: status,topSort:topSort,recommentSort:Sort}),
        dataType: 'json',
        success: function (data) {
            $('#audioList').empty();
            var tdHtml = "";
            var statusName="";
            var styletext = "";
            $.each(data.obj.list, function (i, audio) {
                // console.info( audio.id+"-->"+JSON.parse(audio.content).posterImgs[0]);
                tdHtml += "<tr>";
                tdHtml += "<td>" + audio.id + "</td>";
                tdHtml += '<td><a style="color:black;" href = "/adminAudio/toAudioDetail/' + audio.id + '"><img width="50" height="40" src="' + $("#imgsDomain").val() + (JSON.parse(audio.content).coverImg[0] || "") + '"/></a></td>';
                tdHtml += '<td><a style="color:black;" href = "/adminAudio/toAudioDetail/' + audio.id + '">' + (audio.slogan || "") + "</a></td>";
                tdHtml += '<td><a style="color:black;" href = "/adminAudio/toAudioDetail/' + audio.id + '">' + (audio.channelName || "") + "</a></td>";
                tdHtml += '<td><a style="color:black;" href = "/adminAudio/toAudioDetail/' + audio.id + '">' + (audio.creatorNickname || "") + "</a></td>";
                tdHtml += '<td><a style="color:black;" href = "/adminAudio/toAudioDetail/' + audio.id + '">' + (audio.expireTime || "") + "</a></td>";
                if (audio.status == 1) {
                    statusName = "审核通过"
                    styletext = 'style="color:green;"'
                }
                if (audio.status == 2) {
                    statusName = "审核未通过"
                    styletext = 'style="color:#888888;"'
                }
                if (audio.status == 0) {
                    statusName = "待审核"
                    styletext = 'style="color:red;"'
                }
                if(audio.status==0 && audio.isDelete==-1 && new Date((audio.expireTime).replace(/-/g,"/"))<=new Date()){
                    statusName = "待审核(已失效)";
                    styletext = 'style="color:#c9c9c9;"'
                }
                tdHtml +='<td '+styletext+'>' + statusName + "</td>";
                if (audio.isDelete == -1 && audio.status==1) {
                    tdHtml += "<td><a style='color: red' href='/adminAudio/toAudioDetail/" + audio.id + "'> 未上线 </a></td>";
                } else if(audio.isDelete == 1 && audio.status==1){
                    tdHtml += "<td><a style='color: green' href='/adminAudio/toAudioDetail/" + audio.id + "'> 已上线 </a></td>";
                }else{
                    tdHtml += "<td></td>";
                }

                if (audio.status == 1) {
                    tdHtml += '<td><input type="button" id="pass" value="查看详情" style=" margin-right: 2px;" class="btn true-color" onclick="toDetail(' + audio.id + ')"/></td>" ';
                } else {
                    tdHtml += '<td><input type="button" id="pass" value="查看详情" style=" margin-right: 2px;" class="btn true-color" onclick="toDetail(' + audio.id + ')"/></td>" ';
                }

                if (audio.status == 1 && audio.topSort==5 && audio.isDelete == 1) {
                    tdHtml += "<td><strong>NO.1</strong><button type='button' class='btn bg-color btn-xs' onclick='topTj(" + audio.id + "," + audio.topSort + ")'>置顶</button></td>";
                } else if (audio.status == 1  && audio.topSort==4 && audio.isDelete == 1) {
                    tdHtml += "<td><strong>NO.2</strong><button type='button' class='btn bg-color btn-xs' onclick='topTj(" + audio.id + "," + audio.topSort + ")'>置顶</button></td>";
                } else if (audio.status == 1 && audio.topSort==3 && audio.isDelete == 1) {
                    tdHtml += "<td><strong>NO.3</strong><button type='button' class='btn bg-color btn-xs' onclick='topTj(" + audio.id + "," + audio.topSort + ")'>置顶</button></td>";
                } else if (audio.status == 1 && audio.topSort==2 && audio.isDelete == 1) {
                    tdHtml += "<td><strong>NO.4</strong><button type='button' class='btn bg-color btn-xs' onclick='topTj(" + audio.id + "," + audio.topSort + ")'>置顶</button></td>";
                } else if (audio.status == 1 && audio.topSort==1 && audio.isDelete == 1) {
                    tdHtml += "<td><strong>NO.5</strong><button type='button' class='btn bg-color btn-xs' onclick='topTj(" + audio.id + "," + audio.topSort + ")'>置顶</button></td>";
                } else if (audio.status == 1 && audio.isDelete == 1) {
                    tdHtml += "<td><button type='button' class='btn bg-color btn-xs' onclick='topTj(" + audio.id + "," + audio.TopSort + ")'>置顶</button></td>";
                } else {
                    tdHtml += '<td></td>';
                }
                if (audio.status == 1 && audio.recommentSort==5 && audio.isDelete == 1) {
                    tdHtml += "<td><strong>NO.1</strong><button type='button' class='btn bg-color btn-xs' onclick='recommentTj(" + audio.id + "," + audio.recommentSort + ")'>推荐</button></td>";
                } else if (audio.status == 1  && audio.recommentSort==4 && audio.isDelete == 1) {
                    tdHtml += "<td><strong>NO.2</strong><button type='button' class='btn bg-color btn-xs' onclick='recommentTj(" + audio.id + "," + audio.recommentSort + ")'>推荐</button></td>";
                } else if (audio.status == 1 && audio.recommentSort==3 && audio.isDelete == 1) {
                    tdHtml += "<td><strong>NO.3</strong><button type='button' class='btn bg-color btn-xs' onclick='recommentTj(" + audio.id + "," + audio.recommentSort + ")'>推荐</button></td>";
                } else if (audio.status == 1 && audio.recommentSort==2 && audio.isDelete == 1) {
                    tdHtml += "<td><strong>NO.4</strong><button type='button' class='btn bg-color btn-xs' onclick='recommentTj(" + audio.id + "," + audio.recommentSort + ")'>推荐</button></td>";
                } else if (audio.status == 1 && audio.recommentSort==1 && audio.isDelete == 1) {
                    tdHtml += "<td><strong>NO.5</strong><button type='button' class='btn bg-color btn-xs' onclick='recommentTj(" + audio.id + "," + audio.recommentSort + ")'>推荐</button></td>";
                } else if (audio.status == 1 && audio.isDelete == 1) {
                    tdHtml += "<td><button type='button' class='btn bg-color btn-xs' onclick='recommentTj(" + audio.id + "," + audio.recommentSort + ")'>推荐</button></td>";
                } else {
                    tdHtml += '<td></td>';
                }
                tdHtml += "</tr>";
            });
            $('#audioList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#audioListPagination").empty();
                $('#audioList').html("<tr><td colspan='12' style='text-align: center'>暂无数据</td></tr>");
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
/**
 * 打开隐藏域
 */
function recommentTj(id, reId) {
    $("#top_1").removeClass("btn-success");
    $("#top_2").removeClass("btn-success");
    $("#top_3").removeClass("btn-success");
    $("#top_4").removeClass("btn-success");
    $("#top_5").removeClass("btn-success");
    $("#p_id").val("");
    layer.open({
        type: 1,
        skin: 'layui-layer-demo', //样式类名
        closeBtn: 1, //不显示关闭按钮
        anim: 2,
        area: ['420px', '240px'],
        title: '推荐到',
        shadeClose: true, //开启遮罩关闭
        content: $('#recommentBox'),
        success: function (layero, index) {
            $("#p_id").val(id);
            if (reId != 0) {
                $("#re_Id").val(reId);
                $("#top_" + reId).addClass(" btn-success")
            }
        }
    });

}

/**
 * 打开 推荐隐藏域
 */
function topTj(id, reId) {
    $("#top1").removeClass("btn-success");
    $("#top2").removeClass("btn-success");
    $("#top3").removeClass("btn-success");
    $("#top4").removeClass("btn-success");
    $("#top5").removeClass("btn-success");
    $("#pid").val("");
    layer.open({
        type: 1,
        skin: 'layui-layer-demo', //样式类名
        closeBtn: 1, //不显示关闭按钮
        anim: 2,
        area: ['420px', '240px'],
        title: '置顶到',
        shadeClose: true, //开启遮罩关闭
        content: $('#tuijianBox'),
        success: function (layero, index) {
            $("#pid").val(id);
            if (reId != 0) {
                $("#reId").val(reId);
                $("#top" + reId).addClass(" btn-success")
            }
        }
    });

}

/**
 * 点击top 赋值  推荐
 */
function _voluation(obj) {
    if ($("#top_" + obj).hasClass("btn-success")) {
        $(".buttonClass" + obj).removeClass("btn-success");
        $("#re_Id").val("");
    } else {
        $("#top_1").removeClass("btn-success");
        $("#top_2").removeClass("btn-success");
        $("#top_3").removeClass("btn-success");
        $("#top_4").removeClass("btn-success");
        $("#top_5").removeClass("btn-success");
        $("#top_" + obj).addClass("btn-success")
        $("#re_Id").val(obj);
    }

}
/**
 * 点击top 赋值
 */
function voluation(obj) {
    if ($("#top" + obj).hasClass("btn-success")) {
        $(".buttonClass" + obj).removeClass("btn-success");
        $("#reId").val("");
    } else {
        $("#top1").removeClass("btn-success");
        $("#top2").removeClass("btn-success");
        $("#top3").removeClass("btn-success");
        $("#top4").removeClass("btn-success");
        $("#top5").removeClass("btn-success");
        $("#top" + obj).addClass("btn-success")
        $("#reId").val(obj);
    }

}
/**
 * 提交 推荐
 * @returns {boolean}
 */
function _submitTj() {
    var id = $("#p_id").val();
    var recommend = $("#re_Id").val();
    if (recommend == "" || recommend == 0) {
        layer.msg("请推荐！");
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/recomment/top",
        contentType: 'application/json',
        data: JSON.stringify({
            pid: id,
            sort: recommend,
            parentType:4
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("推荐成功");
                 $("#recommentSort").addClass("true-color");
                 $("#recommentSort").attr("data-val", "4");
                getAudioList(1, 10);
                layer.closeAll();
            } else {
                layer.msg("推荐失败");
            }

        }
    });
}
/**
 * 提交 置顶
 */
function submitTj() {
    var id = $("#pid").val();
    var recommend = $("#reId").val();
    if (recommend == "" || recommend == 0) {
        layer.msg("请置顶！");
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/recomment/top",
        contentType: 'application/json',
        data: JSON.stringify({
            pid: id,
            sort: recommend,
            parentType:5
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("置顶成功");
                $("#topSort").addClass("true-color");
                $("#topSort").attr("data-val", "5");
                getAudioList(1, 10);
                layer.closeAll();
            } else {
                layer.msg("置顶失败");
            }

        }
    });
}

function orderBy(obj) {
    if (obj == 4) {
        if ($("#recommentSort").hasClass("true-color")) {
            $("#recommentSort").removeClass("true-color")
            $("#recommentSort").attr("data-val", "");
        } else {
            $("#recommentSort").addClass("true-color")
            $("#recommentSort").attr("data-val", "4");
        }
    }else if(obj==5){
        if ($("#topSort").hasClass("true-color")) {
            $("#topSort").removeClass("true-color")
            $("#topSort").attr("data-val", "");
        } else {
            $("#topSort").addClass("true-color")
            $("#topSort").attr("data-val", "5");
        }
    }
    getAudioList(1, 10);
}



