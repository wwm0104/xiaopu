var pagination;
var page = 1;
var row = 10;
$(function () {
    pagination = $('#channelListPagination').bootpag({
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
        initData(num, row);
    });
    initData(1, 10);
});

function initData(page, row) {
    var name = $("#name").val();
    var sort = $("#sorts").attr("data-val");
    var type=$("#type").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/channelList",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            page: page,
            row: row,
            name: name,
            sort: sort,
            type:type
        }),
        success: function (data) {
            $("#channelist").empty();
            $.each(data.obj.list, function (i, o) {
                var _s = "<tr data-val=" + o.id + "><td>" + (o.name || "") + "</td>";
                _s += "<td>" + o.desc + "</td>";
                _s += "<td><img id='uploadImage" + o.id + "' class='dropzoneImgListUpload' src='" + o.path + o.images + "' style='width:50px;50px;' data-val='" + o.images + "'  data-toggle='tooltip' title='图片上传'/></td>";
                _s += "<td>" + o.num + "</td>";
                if (o.isOfficial == 0) {
                    _s += "<td>非官方</td>";
                } else if (o.isOfficial == 1) {
                    _s += "<td>官方</td>";
                } else {
                    _s += "<td>用户添加</td>";
                }
                if (o.status == 0) {
                    _s += "<td style='color: cornflowerblue'>待审核</td>";
                } else if (o.status == 1) {
                    _s += "<td style='color: green'>已启用</td>";
                } else {
                    _s += "<td style='color: red'>已失效</td>";
                }
                if (o.type == 1) {
                    _s += "<td><span class='fa fa-file-picture-o' style='color: green;'></span>图文</td>";
                } else if (o.type == 2) {
                    _s += "<td><span class='fa fa-file-movie-o red-color'></span>音频</td>";
                }
                _s += "<td><a  class='btn btn-primary' href='/admin/channel/chanForm?id=" + o.id + "'>编辑</a>    <input type='button' class='btn btn-default' onclick='update(" + o.id + ")' value='修改图片'>　　";
                if (o.status != -1) {
                    _s += "<input type='button'  class='btn delete-btn-bg' onclick='deleteChan(" + o.id + ")' value='删除'></td>";
                } else {
                    _s += "<input type='button'  class='btn delete-btn-bg' disabled='disabled' onclick='deleteChan(" + o.id + ")' value='删除'></td>";
                }
                if (o.status == 1 && o.sort == 5) {
                    _s += "<td><strong>NO.1</strong><button type='button' class='btn bg-color btn-xs' onclick='channelTj(" + o.id + "," + o.sort + ")'>推荐</button></td>";
                } else if (o.status == 1 && o.sort == 4) {
                    _s += "<td><strong>NO.2</strong><button type='button' class='btn bg-color btn-xs' onclick='channelTj(" + o.id + "," + o.sort + ")'>推荐</button></td>";
                } else if (o.status == 1 && o.sort == 3) {
                    _s += "<td><strong>NO.3</strong><button type='button' class='btn bg-color btn-xs' onclick='channelTj(" + o.id + "," + o.sort + ")'>推荐</button></td>";
                } else if (o.status == 1 && o.sort == 2) {
                    _s += "<td><strong>NO.4</strong><button type='button' class='btn bg-color btn-xs' onclick='channelTj(" + o.id + "," + o.sort + ")'>推荐</button></td>";
                } else if (o.status == 1 && o.sort == 1) {
                    _s += "<td><strong>NO.5</strong><button type='button' class='btn bg-color btn-xs' onclick='channelTj(" + o.id + "," + o.sort + ")'>推荐</button></td>";
                } else if (o.status == 1 && o.sort == 0) {
                    _s += "<td>　　<button type='button' class='btn bg-color btn-xs' onclick='channelTj(" + o.id + "," + o.sort + ")'>推荐</button></td>";
                } else {
                    _s += "<td></td>";
                }
                _s += "</tr>";
                $("#channelist").append(_s);
            });
            if (data.obj.pages == 0) {
                $("#channelListPagination").empty();
                $('#channelist').html("<tr><td colspan='14' style='text-align: center'>暂无数据</td></tr>");
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
            $(".dropzoneImgListUpload").dropzone({
                url: "/file/fileUpload",
                maxFiles: 10,
                maxFilesize: 50,
                acceptedFiles: ".png,.jpg",
                success: function (data) {
                    imgUpload(data, this);
                }
            });

        }
    });
}

/**
 * 修改图片
 * @param _this
 */
function update(id) {
    var images = $("#uploadImage" + id).attr("data-val");
    //询问框
    layer.confirm('确定要修改吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/admin/topics/saveChannel",
            contentType: 'application/json',
            data: JSON.stringify({
                id: id,
                images: images
            }),
            dataType: 'json',
            success: function (data) {
                if (data.resultCode == 1) {
                    loadData();
                    layer.msg("修改成功")
                } else {
                    layer.msg("修改失败")
                }

            }
        });
    });

}

/**
 * 推荐打开隐藏域
 */
function channelTj(id, reId) {
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
            parentType:1
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("推荐成功");
                $("#sorts").addClass("true-color");
                $("#sorts").attr("data-val", "1");
                initData(1, 10);
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
    initData(1, 10);
}
/**
 * 删除子频道
 * @param obj
 */
function deleteChan(obj) {
    layer.confirm('确定要删除吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/admin/topics/deleteChannel",
            contentType: 'application/json',
            data: JSON.stringify({
                id: obj
            }),
            dataType: 'json',
            success: function (data) {
                if (data.resultCode == 1) {
                    initData(1, 10);
                    layer.msg("删除成功")
                } else if (data.resultCode == 2) {
                    layer.msg('无法删除，请选择删除该频道下的主题', {icon: 5});
                } else {
                    layer.msg("删除失败")
                }

            }
        });
    });

}
