/**
 * Created by daer on 16/10/17.
 */
$(function () {

    var saveTime = parseInt($.cookie("saveTime")) || 0;
    var time = parseInt($.cookie("smsVcodeTime")) || 0;
    var startTime = (new Date()).getTime();
    if (saveTime != 0) {
        var endTime = (new Date()).getTime();
        var _time = saveTime - startTime;//结束时间 >当前时间 继续进行
        if (0 < _time) {
            var jgTime = parseInt(_time / 1000);
            if (jgTime > 0) {
                sendSMS(jgTime);
                return false;
            }
        } else {
            $.cookie("smsVcodeTime", 0);
        }
    }
});
var vFlag = false;
function sendLoginSms() {
    var mobile = $("#mobile").val();
    if (vFlag) return false;
    if (mobile == '') {
        message("请输入您的手机号！")
        return false;
    }
    //取出上次保存的结束时间  和现在时间比较
    var saveTime = parseInt($.cookie("saveTime")) || 0;
    if (saveTime == 0) {//为0时设置结束时间
        saveTime = (new Date()).getTime() + 90 * 1000;
        $.cookie("saveTime", saveTime);
    } else {//不为0时和当前时间比较
        var _time = saveTime - (new Date()).getTime();//结束时间 >当前时间 继续进行
        if (0 < _time) {
            //var time = parseInt($.cookie("smsVcodeTime")) || 0;
            var jgTime =parseInt(_time / 1000);
            if (jgTime > 0) {
                sendSMS(jgTime);
                return false;
            }
        }
    }
    $(".get-code").html("验证码发送中...");
    $(".get-code").attr({"disabled": "disabled"});
    vFlag = true;
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/user/loginSms",
        contentType: 'application/json',
        data: JSON.stringify({mobile: mobile}),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                $.cookie("saveTime", (new Date()).getTime() + 90 * 1000);
                $.cookie("smsVcodeTime", 90);
                var ctime = 90;
                sendSMS(ctime);
            } else {
                $(".get-code").html("发送验证码");
                $(".get-code").removeAttr("disabled");
                message(data.msg)
                return false;
            }
        }
    });

}

function sendSMS(ctime) {
    $(".get-code").attr({"disabled": "disabled"});
    var timerId = setInterval(function () {
        $.cookie("smsVcodeTime", ctime);
        if (ctime == 0) {
            clearInterval(timerId);
            vFlag = false;
            $(".get-code").removeAttr("disabled");
            $(".get-code").html("重新获取");
            return;
        }
        $(".get-code").html('<span>' + ctime-- + 's' + '</span>后重新获取');
    }, 1000);

}

/*登录js 控制*/
function message(obj) {
    layer.msg(obj, {time: 3000})
    /*$(".p_err").html(obj);*/
}
function login() {
    var mobile = $("#mobile").val();
    var password = $("#password").val();
    var smsCaptcha = $("#smsCaptcha").val();

    if (mobile == "") {
        message("请输入您的用户名");
        return false;
    }
    if (password == "") {
        message("请输入您的登录密码");
        return false;
    }
    if (smsCaptcha == "") {
        message("请输入您的手机验证码");
        return false;
    }
    if (mobile.length != 11) {
        message("请输入正确的用户名");
        return false;
    }
    if (smsCaptcha.length != 6) {
        message("请输入正确的手机验证码");
        return false;
    }
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/user/loginPost",
        contentType: 'application/json',
        data: JSON.stringify({mobile: mobile, password: password, smsCaptcha: smsCaptcha}),
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                window.location.href = data.obj;
            } else {
                $('#loginMsg').html(data.msg);
            }
        }
    });
}




