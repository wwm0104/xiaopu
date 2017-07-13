/**
 * Created by Wang on 16/11/07.
 */
var pagination;
$(function () {
    var page = 1;
    var rows = 10;
    $("#prizeType").select2({
        minimumResultsForSearch: Infinity
    });
    $("#isPublic").select2({
        minimumResultsForSearch: Infinity
    });
    $("#prizeStatus").select2({
        minimumResultsForSearch: Infinity
    });
    initPrizeData(page, rows);

    $("#prizeType").on("change", function () {
        initPrizeData(page, rows);
    });
    $("#isPublic").on("change", function () {
        initPrizeData(page, rows);
    });
    $("#prizeStatus").on("change", function () {
        initPrizeData(page, rows);
    });

    $("#prizeCreate").on("click", function () {
        window.location.href = "/admin/fight/prizeCreate";
    });

    pagination = $('#prizeListPagination').bootpag({
        total: 0,          // total pages
        page: 1,            // default page
        maxVisible: 10,     // visible pagination
        firstLastUse: true,
        prev: '上一页',
        next: '下一页',
        first: '首页',
        last: '末页',
        leaps: true         // next/prev leaps through maxVisible
    }).on("page", function (prize, num) {
        initPrizeData(num, rows);
    });
});

function initPrizeData(page, rows) {
    var sort = $("#sorts").attr("data-val");
    var type = $("#prizeType").val();
    var status = $("#prizeStatus").val();
    var isPublic = $("#isPublic").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/fight/prize/list",
        contentType: 'application/json',
        data: JSON.stringify({page: page, rows: rows, type: type, status: status, isPublic: isPublic, sort: sort}),
        dataType: 'json',
        success: function (data) {
            $('#prizeList').empty();
            var tdHtml = "";
            $.each(data.obj.list, function (i, prize) {
                tdHtml += "<tr>";
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'>" + prize.id + "</a></td>";
                if (prize.type == 1) {
                    tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'> 现金 </a></td>";
                } else if (prize.type == 2) {
                    tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'> 实物 </a></td>";
                } else if (prize.type == 3) {
                    tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'> 虚拟 </a></td>";
                }
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'>" + prize.name + "</a></td>";
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'><img style='width: 50px;height: 30px' src='" + prize.imgsHostDomain + prize.prizeMap.imgs + "'/></img></a></td>";
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'>" + prize.availableTime + "<br>" + prize.expireTime + "</a></td>";
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'>" + prize.prizeMap.rewardRule.challengeCnt + "</a></td>";
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'>" + prize.stockTotal + "</a></td>";
                tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'>" + (prize.stockTotal - prize.stockOut) + "</a></td>";
                if (prize.isPublic == 0) {
                    tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'> 校谱提供 </a></td>";
                } else if (prize.isPublic == 1) {
                    tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'> 发起提供 </a></td>";
                } else if (prize.isPublic == 2) {
                    tdHtml += "<td ><a style='color: black;' href='/admin/fight/prizeDetail/" + prize.id + "'> 校谱专用 </a></td>";
                }
                if (prize.available == 0) {
                    tdHtml += "<td ><a style='color: red;' href='/admin/fight/prizeDetail/" + prize.id + "'> 已停用 </a></td>";
                } else {
                    tdHtml += "<td ><a style='color: limegreen;' href='/admin/fight/prizeDetail/" + prize.id + "'> 已启用 </a></td>";
                }
                if (prize.available == 1 &&　prize.sort == 5 && prize.isPublic == 0) {
                    tdHtml += "<td><strong>NO.1</strong><button type='button' class='btn bg-color btn-xs' onclick='prizeTj(" + prize.id + "," + prize.sort + ")'>推荐</button></td>";
                } else if (prize.available == 1 && prize.sort == 4 && prize.isPublic == 0) {
                    tdHtml += "<td><strong>NO.2</strong><button type='button' class='btn bg-color btn-xs' onclick='prizeTj(" + prize.id + "," + prize.sort + ")'>推荐</button></td>";
                } else if (prize.available == 1 && prize.sort == 3 && prize.isPublic == 0) {
                    tdHtml += "<td><strong>NO.3</strong><button type='button' class='btn bg-color btn-xs' onclick='prizeTj(" + prize.id + "," + prize.sort + ")'>推荐</button></td>";
                } else if (prize.available == 1 && prize.sort == 2 && prize.isPublic == 0) {
                    tdHtml += "<td><strong>NO.4</strong><button type='button' class='btn bg-color btn-xs' onclick='prizeTj(" + prize.id + "," + prize.sort + ")'>推荐</button></td>";
                } else if (prize.available == 1 && prize.sort == 1 && prize.isPublic == 0) {
                    tdHtml += "<td><strong>NO.5</strong><button type='button' class='btn bg-color btn-xs' onclick='prizeTj(" + prize.id + "," + prize.sort + ")'>推荐</button></td>";
                } else if(prize.available == 1 && prize.sort == 0 && prize.isPublic == 0){
                    tdHtml += "<td>　　<button type='button' class='btn bg-color btn-xs' onclick='prizeTj(" + prize.id + "," + prize.sort + ")'>推荐</button></td>";
                }else{
                    tdHtml += "<td></td>";
                }

                tdHtml += "</tr>";
            });
            $('#prizeList').html(tdHtml);
            if (data.obj.pages == 0) {
                $("#prizeListPagination").hide();
                $('#prizeList').html("<tr><td colspan='10' style='text-align: center'>暂无数据</td></tr>");
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
                $("#prizeListPagination").show();
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
}

/**
 * 推荐打开隐藏域
 */
function prizeTj(id, reId) {
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
        title: '推荐到',
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
 * 提交推荐
 */
function submitTj() {
    var id = $("#pid").val();
    var recommend = $("#reId").val();
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
            parentType:3
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("推荐成功");
                $("#sorts").addClass("true-color");
                $("#sorts").attr("data-val", "1");
                initPrizeData(1, 10);
                layer.closeAll();
            } else {
                layer.msg("推荐失败");
            }

        }
    });
}

function orderBy(obj) {
    if (obj == 1) {
        if ($("#sorts").hasClass("true-color")) {
            $("#sorts").removeClass("true-color")
            $("#sorts").attr("data-val", "");
        } else {
            $("#sorts").addClass("true-color")
            $("#sorts").attr("data-val", "1");
        }
    }
    initPrizeData(1, 10);
}
