/**
 * Created by xiaohao on 2016/10/28.
 */

$(function () {
    getSchoolList(); //初始化加载学校列表


});

/**
 * 获取学校列表
 */
function getSchoolList(){
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/admin/reviewgroup/schoolList",
        contentType: 'application/json',
        data: "",
        dataType: 'json',
        success: function(data){
            $('#schoolList').empty();
            var tdHtml = "";
            $.each(data.obj, function(i, school){
                tdHtml +="<option value="+school.id+">"+school.name+"</option>";
            });
            $('#schoolList').html(tdHtml);
        }
    });
}

/**
 * 添加管理员
 */
function add()
{
    var realName=$("#realName").val();  //姓名
    var mobile=$("#mobile").val();      //手机
    var pwd=$("#pwd").val();            //密码
    var npwd=$("#npwd").val();          //确认密码
    var schoolId=$("#schoolList").val();
    var schoolName= $("#schoolList").find("option:selected").text();
    var studentNo=$("#studentno").val();       //学号
    var qq=$("#qq").val();                      //QQ号
    var userSex=$("#userSex").val();           //性别
    var UserInfo = {realName:realName,mobile:mobile,schoolId:schoolId,schoolName:schoolName,studentNo:studentNo,qq:qq,userSex:userSex};

    if(pwd!=npwd) {
        layer.msg("两次密码不一致！");
    }else {
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/admin/AdminUserInfo/doAdd",
            contentType: 'application/json',
            data: JSON.stringify({userInfo:UserInfo,password:pwd}),
            dataType: 'json',
            success: function(data){
                if(data.resultCode==1) {////如果data.resultcode为1成功，2失败
                    layer.msg("添加成功");

                }else {
                    layer.msg("添加失败");
                }
                window.location.href="/admin/AdminUserInfo/";
            }
        });
    }
}

/**
 * 返回上一个页面
 */
function back(){
    window.history.go(-1);
}
