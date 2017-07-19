// 购物车页面的相关脚本

// 1. 点击删除购物车商品之后,进行弹窗确认
$(function() {
	$("#delete").click(function(){
		var pid = $(this).attr("pid");
		var flag = confirm("您忍心删除我吗?");
		if(flag==true){
			window.location.href="/store_alpha/CartServlet?method=removeCart&pid="+pid;
		}
	});

})
	