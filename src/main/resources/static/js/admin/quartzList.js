/**
 * 武宁
 * 定时器调度管理
 */
var pagination;
$(function () {
    var page = 1;
    var row = 10;
    pagination = $('#quartzListPagination').bootpag({
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
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/quartz/list",
        contentType: 'application/json',
        data: JSON.stringify({
            page: page,
            row: row
        }),
        dataType: 'json',
        success: function (data) {
            $('#quartzList').empty();
            if (data.resultCode == 1) {
                $.each(data.obj.list, function (i, quartz) {
                    var _s = "<tr><td>" + quartz.triggerName + "</td>";
                    _s += "<td>" + quartz.jobName + "</td>";
                    _s += "<td>" + quartz.nextFireTime + "</td>";
                    _s += "<td>" + quartz.privrity + "</td>";
                    if (quartz.triggerState == 'PAUSED') {
                        _s += "<td style='color: #0d6aad'>已暂停</td>";
                    } else if (quartz.triggerState == 'WAITING') {
                        _s += "<td style='color: green'>等待中</td>";
                    } else if (quartz.triggerState == 'ERROR') {
                        _s += "<td style='color: red'>已错误</td>";
                    } else {
                        _s += "<td>'+quartz.triggerState+'</td>";
                    }

                    /*  _s += "<td>" + quartz.triggerState + "</td>";*/


                    _s += "<td>" + quartz.triggerType + "</td>";
                    _s += "<td>" + quartz.startTime + "</td>";
                    _s += "<td>" + quartz.cronExpression + "</td>";
                    _s += "<td>";
                    if (quartz.triggerState == 'WAITING') {
                        _s += "<input type='button' class='btn tinyong-color'     value='暂停' onclick='pauseJob(\"" + quartz.jobName + "\",\"" + quartz.groupName + "\")'/>　　";
                    } else if (quartz.triggerState == 'ERROR' || quartz.triggerState == 'PAUSED') {
                        _s += "<input type='button' class='btn qiyong-color'  value='恢复'  onclick='resumeJob(\"" + quartz.jobName + "\",\"" + quartz.groupName + "\")'/>　　";
                    }
                    _s += "<input type='button' class='btn delete-btn-bg'   value='删除' onclick='removeJob(\"" + quartz.jobName + "\",\"" + quartz.groupName + "\",\"" + quartz.triggerGroupName + "\")'/>　　";
                    _s += "</td>";
                    $("#quartzList").append(_s);
                    /*<input type='button' class='btn true-color'  value='修改触发时间' onclick='modifyTime(\"" + quartz.jobName + "\",\"" + quartz.triggerGroupName + "\")'/>*/
                });
                if (data.obj.pages == 0) {
                    $("#quartzListPagination").empty();
                    $('#quartzList').html("<tr><td colspan='9' style='text-align: center'>暂无数据</td></tr>");
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

        }
    });
}
/**
 * 手动执行 全部定时器
 */
function run() {
    var parameter = '';
    /* var data = [];*/
    var obj = document.getElementsByName('parameter');
    for (var i = 0; i < obj.length; i++) {
        if (obj[i].checked) {
            parameter += obj[i].value + ',';  //如果选中，将value添加到变量param中
            /*  data.push(obj[i].value);*/
        }
    }
    if (parameter == "") {
        layer.msg("请选择要调度的定时器");
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/quartz/quartz",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            parameter: parameter
        }),
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("调度结束");
                location.reload();
            } else {
                layer.msg("调度失败");
            }
        }
    });
}
/**
 * 暂停 定时器
 */
function pauseJob(name, group) {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/quartz/pauseJob",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            jobName: name,
            groupName: group
        }),
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("已暂停");
                location.reload();
            } else {
                layer.msg("暂停失败");
            }
        }
    });
}
/**
 * 恢复定时器
 * @param name
 */
function resumeJob(name, group) {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/quartz/resumeJob",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            jobName: name,
            groupName: group
        }),
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("已恢复");
                location.reload();
            } else {
                layer.msg("恢复失败");
            }
        }
    });
}
/**
 * 删除定时器
 * @param name
 */
function removeJob(name, group, triggerGroupName) {
    layer.msg('你确定要删除吗？', {
        time: 0 //不自动关闭
        , btn: ['必须的', '取消']
        , yes: function (index) {
            layer.close(index);
            $.ajax({
                processData: false,
                type: 'POST',
                url: "/admin/quartz/removeJob",
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify({
                    jobName: name,
                    groupName: group,
                    triggerGroupName: triggerGroupName
                }),
                success: function (data) {
                    if (data.resultCode == 1) {
                        layer.msg("已删除");
                        location.reload();
                    } else {
                        layer.msg("删除失败");
                    }
                }
            });
        }
    });
}
/**
 * 修改触发时间
 */
function modifyTime(name, triggerGroupName) {
    /* */
    layer.prompt({title: '输入要修改的CRON，并确认', formType: 3}, function (pass, index) {
        layer.close(index);
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/admin/quartz/modifyTime",
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({
                jobName: name,
                triggerGroupName: triggerGroupName,
                cronExpression: pass
            }),
            success: function (data) {
                if (data.resultCode == 1) {
                    layer.msg("修改成功");
                    location.reload();
                } else {
                    layer.msg("修改失败");
                }
            }
        });

    });
}
/**
 * 停止所有调度
 */
function _shutDown() {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/quartz/shutdowns",
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("已停止");
                location.reload();
            } else {
                layer.msg("停止失败");
            }
        }
    });
}
/**
 * 启动所有调度
 */
function start() {
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/quartz/start",
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                layer.msg("已启动");
                location.reload();
            } else {
                layer.msg("启动失败");
            }
        }
    });
}