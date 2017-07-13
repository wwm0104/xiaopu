/**
 * 排序点击事件
 */
function orderBy(obj) {
    if(obj == 6){
        if ($("#sorts").hasClass("true-color")) {
            $("#sorts").removeClass("true-color")
            $("#sorts").attr("data-val", "");
        } else {
            $("#sorts").addClass("true-color")
            $("#sorts").attr("data-val", "1");
        }
    }else if (obj == 5) {
        if ($("#isRecomment").hasClass("true-color")) {
            $("#isRecomment").removeClass("true-color")
            $("#isRecomment").attr("data-val", "");
        } else {
            $("#isRecomment").addClass("true-color")
            $("#isRecomment").attr("data-val", "1");
        }
    } else if (obj == 4) {
        if ($("#isOfficial").hasClass("true-color")) {
            $("#isOfficial").removeClass("true-color")
            $("#isOfficial").attr("data-val", "");
        } else {
            $("#isOfficial").addClass("true-color")
            $("#isOfficial").attr("data-val", "1");
        }
    } else {
        if ($(".buttonClass" + obj).hasClass("true-color")) {
            $(".buttonClass" + obj).removeClass("true-color")
            $("#orderType").val("");
        } else {
            $(".buttonClass1").removeClass("true-color")
            $(".buttonClass2").removeClass("true-color")
            $(".buttonClass3").removeClass("true-color")
            $(".buttonClass" + obj).addClass("true-color")
            $("#orderType").attr("value", obj);
        }
    }
    initData(1, 10);
}


$(function () {
    $("#startTime").datetimepicker({
        format: 'yyyy/mm/dd hh:ii',
        language: 'zh-CN', //汉化
        autoclose: true //选择日期后自动关闭
    }).on('changeDate', function (ev) {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        if (endTime == "") {
            $("#endTime").focus();
            return false;
        } else {
            var sTime = Date.parse(startTime);
            var eTime = Date.parse(endTime);
            if (sTime > eTime) {
                layer.msg("结束时间不能大于开始时间");
                $("#endTime").val("");
                $("#endTime").focus();
                return;
            } else {
                initData(1, 10);
            }
        }
    });

    $("#endTime").datetimepicker({
        format: 'yyyy/mm/dd hh:ii',
        language: 'zh-CN', //汉化
        autoclose: true //选择日期后自动关闭
    }).on('changeDate', function (ev) {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        if (startTime == "") {
            $("#endTime").val("");
            $("#startTime").focus();
            return false;
        } else {
            var sTime = Date.parse(startTime);
            var eTime = Date.parse(endTime);
            if (sTime > eTime) {
                layer.msg("结束时间不能大于开始时间");
                $("#endTime").val("");
                $("#endTime").focus();
                return;
            } else {
                initData(1, 10);
            }
        }
    });

});

var pagination;
$(function () {

    var page = 1;
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
        initData(num, row);
    });

    initData(1, 10);
});
function initData(page, row) {
    var isOfficial = $("#isOfficial").attr("data-val");
    var orderType = $("#orderType").val();
    var type = $("#type").val();//类型
    var tipOffCount = $("#tipOffCount").val();
    var isChallenge = $("#isChallenge").val();
    var startTime = $("#reservationtime").val();
    var channelId = $("#channelId").val();
    var isOver = $("#isOver").val();
    var userName = $("#userName").val();
    var remount = $("#isRecomment").attr("data-val");
    var sort = $("#sorts").attr("data-val");
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/list",
        contentType: 'application/json',
        data: JSON.stringify({
            page: page,
            row: row,
            isOfficial: isOfficial,
            orderType: orderType,
            type: type,
            tipOffCount: tipOffCount,
            isChallenge: isChallenge,
            takeTime: startTime,
            channelId: channelId,
            isOver: isOver,
            userName: userName,
            recommend: remount,
            sort:sort
        }),
        dataType: 'json',
        success: function (data) {
            $('#topicList').empty();
            $.each(data.obj.list, function (i, topic) {
                var _s = "<tr style='cursor:pointer;'><td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")' title='点击查看详情'>" + topic.id + "</td>";
                _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'>" + (topic.userName || "") + "</td>";
                _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'>" + (topic.realName || "") + "</td>";

                if (topic.type == 1) {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'><span class='fa fa-file-picture-o' style='color: green;'></span></td>";
                } else if (topic.type == 2) {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'><span class='fa fa-file-movie-o red-color'></span></td>";
                } else {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'><span class='fa fa-file-movie-o' style='color: yellow;'></span></td>";
                }

                _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'><span class='red-color'>" + topic.tipOffCount + "</span></td>";

                _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'>" + topic.createTime + "</td>";

                _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'>" + (topic.slogan || "") + "</td>";

                if (topic.isChallenge == 1) {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'><span class='red-color'>发起方</span></td>";
                } else if (topic.isChallenge == 0) {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'>挑战方</td>";
                } else if (topic.isChallenge == -1) {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'>无</td>";
                }

                _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'>" + topic.channelName || "" + "</td>";

                if (topic.over == 1) {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'><span class='red-color'>已结束</span></td>";
                } else if (topic.over == 0) {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'>进行中</td>";
                } else {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'>无</td>";
                }

                /*if (topic.isChallenge !=-1) {
                 if(topic.isSuccess ==""){
                 _s += "<td> <span style='color: red;'>失败</span></td>";
                 }else{
                 _s += "<td>成功</td>";
                 }
                 }else{
                 _s += "<td></td>";
                 }*/
                if (topic.isSuccess == 0 && topic.over == 1) {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'> <span class='red-color'>失败</span></td>";
                } else if (topic.isSuccess == 1 && topic.over == 1) {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'>成功</td>";
                } else {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'></td>";
                }


                if (topic.isOfficial == 0 && topic.isChallenge != -1) {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'><span class='nub-font'>" + (topic.visitSum || '' ) + "</span>" + "/" + topic.allVotes || '' + "</td>";
                } else {
                    _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'></td>";
                }

                /* _s += "<td>" + (topic.allCount || '' ) + "</td>";*/
                _s += "<td onclick='clickEvent(" + topic.id + "," + topic.isOfficial + ")'>" + topic.commentCnt + "</td>";
                if (topic.recommend == 5 && topic.isChallenge == 1) {
                    _s += "<td><strong>NO.1</strong><button type='button' class='btn bg-color btn-xs' onclick='tuijian(" + topic.id + "," + topic.recommend + ")'>置顶</button></td>";
                } else if (topic.recommend == 4 && topic.isChallenge == 1) {
                    _s += "<td><strong>NO.2</strong><button type='button' class='btn bg-color btn-xs' onclick='tuijian(" + topic.id + "," + topic.recommend + ")'>置顶</button></td>";
                } else if (topic.recommend == 3 && topic.isChallenge == 1) {
                    _s += "<td><strong>NO.3</strong><button type='button' class='btn bg-color btn-xs' onclick='tuijian(" + topic.id + "," + topic.recommend + ")'>置顶</button></td>";
                } else if (topic.recommend == 2 && topic.isChallenge == 1) {
                    _s += "<td><strong>NO.4</strong><button type='button' class='btn bg-color btn-xs' onclick='tuijian(" + topic.id + "," + topic.recommend + ")'>置顶</button></td>";
                } else if (topic.recommend == 1 && topic.isChallenge == 1) {
                    _s += "<td><strong>NO.5</strong><button type='button' class='btn bg-color btn-xs' onclick='tuijian(" + topic.id + "," + topic.recommend + ")'>置顶</button></td>";
                } else if (topic.isChallenge == 1) {
                    _s += "<td>　　<button type='button' class='btn bg-color btn-xs' onclick='tuijian(" + topic.id + "," + topic.recommend + ")'>置顶</button></td>";
                } else {
                    _s += "<td></td>";
                }

                if (topic.isChallenge != 0 && topic.over == 0 && topic.sort == 5) {
                    _s += "<td><strong>NO.1</strong><button type='button' class='btn bg-color btn-xs' onclick='indexTj(" + topic.id + "," + topic.sort + ")'>推荐</button></td>";
                } else if (topic.isChallenge != 0 && topic.over == 0 && topic.sort == 4) {
                    _s += "<td><strong>NO.2</strong><button type='button' class='btn bg-color btn-xs' onclick='indexTj(" + topic.id + "," + topic.sort + ")'>推荐</button></td>";
                } else if (topic.isChallenge != 0 && topic.over == 0 && topic.sort == 3) {
                    _s += "<td><strong>NO.3</strong><button type='button' class='btn bg-color btn-xs' onclick='indexTj(" + topic.id + "," + topic.sort + ")'>推荐</button></td>";
                } else if (topic.isChallenge != 0 && topic.over == 0 && topic.sort == 2) {
                    _s += "<td><strong>NO.4</strong><button type='button' class='btn bg-color btn-xs' onclick='indexTj(" + topic.id + "," + topic.sort + ")'>推荐</button></td>";
                } else if (topic.isChallenge != 0 && topic.over == 0 && topic.sort == 1) {
                    _s += "<td><strong>NO.5</strong><button type='button' class='btn bg-color btn-xs' onclick='indexTj(" + topic.id + "," + topic.sort + ")'>推荐</button></td>";
                } else if (topic.isChallenge != 0 && topic.over == 0) {
                    _s += "<td>　　<button type='button' class='btn bg-color btn-xs' onclick='indexTj(" + topic.id + "," + topic.sort + ")'>推荐</button></td>";
                } else {
                    _s += "<td></td>";
                }
                _s += '</tr>';
                $("#topicList").append(_s);
            })
            ;
            if (data.obj.pages == 0) {
                $("#Pagination").empty();
                $('#topicList').html("<tr><td colspan='14' style='text-align: center'>暂无数据</td></tr>");
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
 * 打开隐藏域
 */
/*function openChannelList() {
    layer.open({
        type: 1,
        skin: 'layui-layer-demo', //样式类名
        closeBtn: 1, //不显示关闭按钮
        anim: 2,
        area: ['820px', '440px'],
        shadeClose: true, //开启遮罩关闭
        title: '频道管理',
        content: $('#channe'),
        success: function (layero, index) {
            $("#insertButton").show();
            $("#insertInfo").hide();
            $("#chnaaleName").val("");
            $("#uploadImage_1").attr("data-val", "");
            $("#uploadImage_1").attr("value", "");
            $("#uploadImage_1").attr("src", "/img/cjhdtianjia.png");
            loadData();
        }
    });

}*/
/**
 * 添加按钮点击事件
 */
/*function insertButtonClick() {
    $("#insertButton").hide();
    $("#insertInfo").show();

}*/

/*
function loadData() {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/channelList",
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            $("#channelist").empty();
            $.each(data.obj, function (i, o) {
                var _s = "<tr data-val=" + o.id + "><td><input type='text' readonly='readonly' id='chName' class='form-control' name='chName'  value=" + o.name + "></td>";
                _s += "<td><img id='uploadImage" + o.id + "' class='dropzoneImgListUpload' src='" + o.path + o.images + "' style='width:50px;50px;' data-val='" + o.images + "'  data-toggle='tooltip' title='图片上传'/></td>";
                _s += "<td>" + o.num + "</td>";
                if(o.isOfficial==0){
                    _s += "<td>非官方</td>";
                }else if(o.isOfficial==1){
                    _s += "<td>官方</td>";
                }else{
                    _s += "<td>用户添加</td>";
                }
                _s += "<td>" + o.num + "</td>";
                _s += "<td><input type='button' class='btn btn-default' onclick='update(" + o.id + ")' value='修改'>　　<input type='button' disabled='disabled' class='btn delete-btn-bg' onclick='deleteChan(" + o.id + ")' value='删除'></td>";
                _s += "</tr>";
                $("#channelist").append(_s);
            });
            $(".dropzoneImgListUpload").dropzone({
                url: "/file/fileUpload",
                maxFiles: 10,
                maxFilesize: 50,
                acceptedFiles: ".png,.jpg",
                success: function (data) {
                    imgUpload(data,this);
                }
            });

        }
    });
}
*/

/**
 * 保存添加的频道
 */
/*function submitChannelForm() {
    var channelName = $("#chnaaleName").val();
    var images = $("#uploadImage_1").attr("data-val");
    var isOffice = $("#isOffice").val();
    if (channelName == '') {
        layer.msg("频道名称不能为空");
        return false;
    }
    if (images == '') {
        layer.msg("请上传频道图片");
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/saveChannel",
        contentType: 'application/json',
        data: JSON.stringify({
            name: channelName,
            images: images,
            isOfficial:isOffice
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("添加成功")
                loadData();
                $("#insertButton").show();
                $("#insertInfo").hide();
                $("#chnaaleName").val("");
                $("#uploadImage_1").attr("data-val", "");
                $("#uploadImage_1").attr("value", "");
                $("#uploadImage_1").attr("src", "/img/cjhdtianjia.png");
            } else {
                layer.msg("添加失败")
            }

        }
    });

}*/
/**
 * 修改图片
 * @param _this
 */
/*function update(id) {
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

}*/
/**
 * 删除频道
 * @param obj
 */
/*function deleteChan(obj) {
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
                    loadData();
                    layer.msg("删除成功")
                } else if (data.resultCode == 2) {
                    layer.msg('无法删除，请选择删除该频道下的主题', {icon: 5});
                } else {
                    layer.msg("删除失败")
                }

            }
        });
    });

}*/

/**
 * 推荐打开隐藏域
 */
function tuijian(id, reId) {
    $("#top1").removeClass("btn-success");
    $("#top2").removeClass("btn-success");
    $("#top3").removeClass("btn-success");
    $("#top4").removeClass("btn-success");
    $("#top5").removeClass("btn-success");
    $("#sid").val("");
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
            $("#sid").val(id);
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
    var id = $("#sid").val();
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
            parentType:6
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("修改成功");
                $("#isRecomment").addClass("true-color");
                $("#isRecomment").attr("data-val", "1");
                initData(1, 10);
                layer.closeAll();
            } else {
                layer.msg("修改失败");
            }

        }
    });
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/topics/updateRecoment",
        contentType: 'application/json',
        data: JSON.stringify({
            id: id,
            recommend: recommend
        }),
        dataType: 'json',
        success: function (data) {
        }
    });
}

/**
 * 当前行点击事件
 */
/*$(function () {
 $("tr").bind("click",function(){
 var topic_id = $(this).attr("data-val");
 alert(topic_id);
 });
 });*/
function clickEvent(topicId, isOffical) {
    window.location.href = "/admin/topics/selectTopicsDetail/" + topicId;

}

/**
 * 回车事件
 * @param e
 */
function touch(e) {
    var keynum;
    if (window.event) {
        keynum = e.keyCode
    } else if (e.which) {
        keynum = e.which
    }
    if (keynum == 13) {
        initData(1, 10)
    }


}

/**
 * 主页推荐主题
 */
function indexTj(topicId,sort) {
    $("#index1").removeClass("btn-success");
    $("#index2").removeClass("btn-success");
    $("#index3").removeClass("btn-success");
    $("#index4").removeClass("btn-success");
    $("#index5").removeClass("btn-success");
    $("#topicId").val("");
    layer.open({
        type: 1,
        skin: 'layui-layer-demo', //样式类名
        closeBtn: 1, //不显示关闭按钮
        anim: 2,
        area: ['420px', '240px'],
        title: '首页推荐',
        shadeClose: true, //开启遮罩关闭
        content: $('#indexBox'),
        success: function (layero, index) {
            $("#topicId").val(topicId);
            if (sort != 0) {
                $("#sort").val(sort);
                $("#index" + sort).addClass(" btn-success")
            }
        }
    });
}


/**
 * 点击首页 top 赋值
 */
function index(obj) {
    if ($("#index" + obj).hasClass("btn-success")) {
        $(".top" + obj).removeClass("btn-success");
        $("#sort").val("");
    } else {
        $("#index1").removeClass("btn-success");
        $("#index2").removeClass("btn-success");
        $("#index3").removeClass("btn-success");
        $("#index4").removeClass("btn-success");
        $("#index5").removeClass("btn-success");
        $("#index" + obj).addClass("btn-success")
        $("#sort").val(obj);
    }
}


/**
 * 提交首页推荐
 */
function submitIndexTj() {
    var id = $("#topicId").val();
    var sort = $("#sort").val();
    if (sort == "" || sort == 0) {
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
            sort: sort,
            parentType:2
        }),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("修改成功");
                $("#sorts").addClass("true-color");
                $("#sorts").attr("data-val", "1");
                initData(1, 10);
                layer.closeAll();
            } else {
                layer.msg("修改失败");
            }

        }
    });
}




