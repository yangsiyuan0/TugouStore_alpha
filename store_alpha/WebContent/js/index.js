// 首页的JS代码

// 1. 利用AJAX技术:展示导航栏(商品分类)
$(function(){
	$.post("/store_alpha/CategoryServlet",{"method":"findAllCategory"},function(data){
		// 对返回的CategoryList进行遍历:按格式插入商品分类到导航栏

		$(data).each(function(i,n) {
			$("#categoryMenu").append("<li><a href='/store_alpha/ProductServlet?cid=" + n.cid + "&method=findPageByCid&currentPage=1'>"+ n.cname +"</a></li>");
		});
	},"json");
})
