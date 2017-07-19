// 用于实现注册中的前端校验 + AJAX异步校验


// 4. BootStrap提供的日期解决方案
$(function() {
	$(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });
})


// 3. AJAX：验证码异步校验
$(function() {
	$("#verifycode").blur(function() {
		var $verifycode = $(this).val();
		if($verifycode != ""){
			$.post("/store_alpha/UserServlet",{"method":"checkVerifyCode","verifycode":$verifycode},function(data){
				if(data.status == "no"){
					$("#verifymsg").html("&emsp;(验证码错误)");
					$("#regButton").attr("disabled",true);
				}else if(data.status== "yes"){
					$("#verifymsg").html("&emsp;验证码正确");
					$("#regButton").attr("disabled",false);
				}
			},"json");
		}
	})
})



// 2. AJAX:用户名异步校验
$(function() {
	$("#username").blur(function() {
		var $username = $(this).val();
		if($username != ""){
			$.post("/store_alpha/UserServlet",{"method":"checkUsername","username":$username},function(data){
				if(data.status=="yes"){ // 如果用户名可用
					$("#usermsg").html("<samll color='green'>&emsp;(用户名可以使用)</small>");
					$("#regButton").attr("disabled",true);
				}else if(data.status== "no"){ // 如果用户名不可用
					$("#usermsg").html("<small color='red'>&emsp;(用户名已被占用)</small");
					$("#regButton").attr("disabled",false);
				}
			},"json");
		}
	})
})

// 1. 前端校验
$(function(){
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
                    },
					stringLength: {
						min: 6, 
						max: 18, 
						message: '用户名长度必须在6到18位之间' 
					}, 
					regexp: { 
						regexp: /^[a-zA-Z0-9_]+$/, 
						message: '用户名只能包含大写、小写、数字和下划线' 
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
            repassword: {
                validators: {
                    notEmpty: {
                        message: '确认密码不能为空'
                    },
                    identical: {
                        field: 'password',
                        message: '与密码不符'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '邮箱地址不能为空'
                    },
                    emailAddress: {
                    	message: '邮箱地址格式有误' 
                   	}
                }
            },
            name: {
            	validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    }
            	}
            },
            telephone: {
            	validators: {
                    notEmpty: {
                        message: '号码不能为空'
                    },
                    phone: {
                        message: '号码格式不对',
                    	country: 'CN'
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
