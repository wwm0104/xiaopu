$(document).ready(function () {
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

    });
});