// 全局数组，用来存放移除出当前频道id
var removeCid = new Array();
// 定义Map，存放移除排序
var orderMap = {};
var _flag=true;
$(document).ready(function () {

    tr_init();
    $('#inputForm').bootstrapValidator({
        message: '必填项',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        }
    }).on('success.form.bv', function (e) {
       /* e.preventDefault();
        var $form = $(e.target);
        var bv = $form.data('bootstrapValidator');*/
       var img =$("#uploadImage_1").attr("value");
        if(img==""){
            layer.msg("请上传图片");
            $("#submitId").removeAttr("disabled");
            return false;
        }else{
            $("#posterImg").val(img);
        }
        $.buidData();
       if(_flag == false){
            return false;
        }
    });
    $.buidData = function () {
        if ($("#chanlList tr").length == 0) {
            layer.msg("请选择频道");
            _flag=false;
        }
            var channelId = new Array();
            var channelSort = new Array();
            $("#table2 [name='CID']").each(function(){
                channelId.push($(this).val());
            });
            $("#table2 [name='CSORT']").each(function(){
                var _order = $(this).val();
                if(_order=="" || _order==null){
                    _order = "n";
                }
                channelSort.push(_order);
            });
            channelId = channelId.toString();
            $("#channelId").val(channelId);
            channelSort = channelSort.toString();
            $("#channelSort").val(channelSort);
            removeCid = removeCid.toString();
            $("#removeCid").val(removeCid);
        }

       /* var data = [];
        var id = $("#_id").val();
        $('#channnelList tr').each(function (i,index) {
            var tr = $(this);
            var item = {};
            item.pChannelId = id;
            var channelId = tr.find('select[data-clm]').val();
            if(channelId==""){
                layer.msg("请选择频道");
               /!* return false;*!/
                _flag=false;
            }else{
                item[tr.find('select[data-clm]').attr('data-clm')] = channelId;
            }
            var sort = tr.find('input[data-clm]').val();
            if(sort==""){
                layer.msg("请输入排序");
                _flag=false;
               /!* return false;*!/
            }else{
                item[tr.find('input[data-clm]').attr('data-clm')] = sort;
            }
            data.push(item);
        });
        $("#channelList").val($.toJSON(data))
        return _flag;*/
   /* }*/


    /**
     * 清除 不需要的频道
     */
   /* var more = $("#more").val();
    if(more==1){
        $('.pkChannelList option[data=2]').remove();
    }else if(more==2){
        $('.pkChannelList option[data=1]').remove();
    }*/
});

/**
 * 删除一行
 * @param _this
 */
function tr_delete(_this) {
    var tr = $(_this).parent('td').parent('tr');
    var pChannelId = tr.attr("data");
    var channelId = tr.attr("value");
    if (pChannelId == '' && channelId == '') {
        tr.remove();
        tr_init();
    } else {
        layer.confirm('确定删除吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                processData: false,
                type: 'POST',
                url: "/admin/channel/deleteChildChl",
                contentType: 'application/json',
                data: JSON.stringify({
                    pChannelId: pChannelId,
                    channelId: channelId
                }),
                dataType: 'json',
                success: function (data) {
                    if (data.resultCode == 1) {
                        layer.msg("删除成功")
                        tr.remove();
                        tr_init();
                    } else {
                        layer.msg("删除失败")
                    }

                }
            });
        });
    }
}
function tr_init() {
    if ($("#channnelList tr").length == 0) {
        //tr_add();
    }
}
/*function moreChange() {
    var more = $("#more").val();
    if(more==1){
        $('.pkChannelList option[data=2]').remove();
    }else if(more==2){
        $('.pkChannelList option[data=1]').remove();
    }
}*/



/**
 * 从右向左添加数据
 */
function down() {
// 获取上面table中选中，并遍历
    $("#table2 input:checked").each(function(){
        var tablerow = $(this).parent("td").parent("tr");
        var id = tablerow.find("input[name='CID']").val();
        removeCid.push(id);
        orderMap[id] = tablerow.find("[name='CSORT']").val();
        var name = tablerow.find("[name='CNAME']").html();
        var type = tablerow.find("[name='CTYPE']").html();
        var typeId = tablerow.find("[name='CTYPE']").attr("data");
        $("#table1").append("<tr><td><input type='checkbox' name='GID' value='"+id+"'/></td><td  name='GNAME'>"+name+"</td><td name='GTYPE' data='"+typeId+"'>"+type+"</td></tr>");
        tablerow.remove();
    }) ;

}
/**
 * 从左向右添加数据
 */
function up() {
    $("#table1 input:checked").each(function(){
        var tablerow = $(this).parent("td").parent("tr");
        var id = tablerow.find("input[name='GID']").val();
        $.each(removeCid,function(index,item){
            if(item==id){
                removeCid.splice(index,1);
            }
        });
        var name = tablerow.find("[name='GNAME']").html();
        var type = tablerow.find("[name='GTYPE']").html();
        var typeId = tablerow.find("[name='GTYPE']").attr("data");
        var order = orderMap[id];
        if(order){
            $("#table2").append("<tr><td><input type='checkbox' name='CID' value='"+id+"'/></td><td name='CNAME'>"+name+"</td><td name='CTYPE' data='"+typeId+"'>"+type+"</td><td><input name='CSORT' class='form-control' type='text' value='"+order+"'/></td></tr>");
        }else{
            $("#table2").append("<tr><td><input type='checkbox' name='CID' value='"+id+"'/></td><td name='CNAME'>"+name+"</td><td name='CTYPE' data='"+typeId+"'>"+type+"</td><td><input name='CSORT' class='form-control' type='text'/></td></tr>");
        }
        tablerow.remove();
    }) ;
}
/**
 * 从右向左添加所有数据
 */
function allDown() {
    $("#table2 input").prop("checked",true);
    down();
}
/**
 * 从左向右添加所有数据
 */
function allUp() {
    $("#table1 input").prop("checked",true);
    up();
}

