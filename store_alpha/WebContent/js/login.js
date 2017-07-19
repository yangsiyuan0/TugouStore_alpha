// 登陆页面的校验

// 2. AJAX：验证码异步校验
$(function() {
	$("#verifycode").blur(function() {
		var $verifycode = $(this).val();
		if($verifycode != ""){
			$.post("/store_alpha/UserServlet",{"method":"checkVerifyCode","verifycode":$verifycode},function(data){
				if(data.status == "no"){
					$("#verifymsg").html("&emsp;验证码错误");
					$("#regButton").attr("disabled",true);
				}else if(data.status== "yes"){
					$("#verifymsg").html("&emsp;验证码正确");
					$("#regButton").attr("disabled",false);
				}
			},"json");
		}
	})
})

// 1. 前端校验

$(function(){    
	// 此处如果用 $("#formSubmit"),会造成点击两次才能提交的问题
	 $("#formSubmit").bootstrapValidator({
		message: 'This value is not valid',
		feedbackIcons: {
				valid: 'glyphicon glyphicon-ok',
				validating: 'glyphicon glyphicon-refresh'
			},
        fields: {
            username: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    }
                }
            },
            verifycode:{
            	validators: {
                    notEmpty: {
                        message: '验证码不能为空'
                    }
            	}
            }
        }
    });
}); 