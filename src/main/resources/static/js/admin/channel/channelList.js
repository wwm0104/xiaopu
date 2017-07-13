var pagination;
var page = 1;
var rows = 10;
$(function () {

    $(".table-1").sortable({
        update: function (event, ui) {
            var channelId = new Array();
            $("#channelList [name='cid']").each(function(){
                channelId.push($(this).val());
            });
            /**
             * 保存排序
             */
            $.ajax({
                processData: false,
                type: 'POST',
                url: "/admin/channel/updateSort",
                contentType: 'application/json',
                data: JSON.stringify({channId: channelId.toString()}),
                dataType: 'json',
                success: function (data) {
                    initData(1,10)
                }
            });

        }
    });
    $(".table-1").disableSelection();
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
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/channel/list",
        contentType: 'application/json',
        data: JSON.stringify({
            page: page,
            row: row,
            name: name
        }),
        dataType: 'json',
        success: function (data) {
            console.info(data);
            $('#channelList').empty();
            $.each(data.obj.list, function (i, channel) {
                var _s = "<tr>";
                _s += "<td><input type='hidden' name='cid' value='"+channel.id+"'>" + channel.id + "</td>";
                _s += "<td>" + channel.name + "</td>";
                _s += "<td>" + channel.desc + "</td>";
                _s += "<td><img width='40px' height='40px' src='" + data.imgsHostDomain + channel.posterImg + "'/></td>";
                _s += "<td name='sort'>" + channel.sort + "</td>";
                _s += "<td><a  class='btn btn-primary' href='/admin/channel/form?id=" + channel.id + "'>编辑</a>  <input type='button' class='btn delete-btn-bg' onclick='deleteChl(" + channel.id + ")' value='删除'>   <input type='button'  class='btn btn-default' onclick='openForm(" + channel.id + ",\"" + channel.name + "\")' value='添加子频道'></td>";
                _s += "<tr>";
                $("#channelList").append(_s);
            })
            ;
            if (data.obj.pages == 0) {
                $("#channelListPagination").empty();
                $('#channelList').html("<tr><td colspan='14' style='text-align: center'>暂无数据</td></tr>");
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
    ;
}
/**
 * 修改 排序
 * @param id
 */
function updateSort(id) {
    var sort = $("#sort_" + id).val();
    if (sort == null || sort == "") {
        layer.msg("排序不能为空")
        $("#sort_" + id).val($("#sort_" + id).attr("data-val"));
        $("#sort_" + id).focus();
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/channel/updateSort",
        contentType: 'application/json',
        data: JSON.stringify({
            id: id,
            sort: sort
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("修改成功")
                initData(1, 10);
            } else {
                layer.msg("修改失败")
            }

        }
    });

}

/**
 * 删除 频道主表信息
 * @param id
 */
function deleteChl(id) {
    layer.confirm('确定删除吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/admin/channel/deleteChl",
            contentType: 'application/json',
            data: JSON.stringify({
                id: id
            }),
            dataType: 'json',
            success: function (data) {
                if (data.resultCode == 1) {
                    layer.msg("删除成功")
                    initData(1, 10);
                } else {
                    layer.msg("删除失败")
                }

            }
        });
    });
}

/**
 * 打开隐藏域
 */
function openForm(id, name) {
    layer.open({
        type: 1,
        skin: 'layui-layer-demo', //样式类名
        closeBtn: 1, //不显示关闭按钮
        anim: 2,
        area: ['1000px', '700px'],
        title: '添加子频道',
        shadeClose: true, //开启遮罩关闭
        content: $('#insetChildChannel'),
        success: function (layero, index) {
            $("#channelId").val(id);
            $("#channelName").val(name);
        }
    });
}

function saveChannel() {
    var channelId = $("#channelId").val();
    var channelName = $("#channelName").val();
    var name = $("#names").val();
    var desc = $("#desc").val();
    var isOfficial = $("#isOfficial").val();
    var status = $("#status").val();
    var type = $("#type").val();
    var posterImg = $("#uploadImage_1").attr("value");
    var sort = $("#sort").val();
    if (posterImg == "") {
        layer.msg("请上传图片");
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/channel/saveChildChan",
        contentType: 'application/json',
        data: JSON.stringify({
            channelId: channelId,
            channelName: channelName,
            name: name,
            desc: desc,
            isOfficial: isOfficial,
            status: status,
            type: type,
            posterImg: posterImg,
            sort: sort
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("添加成功")
            } else {
                layer.msg("添加失败失败")
            }
            layer.closeAll();
        }
    });
}

