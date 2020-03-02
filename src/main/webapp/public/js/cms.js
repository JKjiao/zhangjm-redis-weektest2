//菜单数组
var menuArr = $(".col-2 .nav-link");
//当前的url
var curUrl = $(menuArr[0]).attr("url");

//点击事件
$(".col-2 .nav-link").on("click", function() {
	var url = $(this).attr("url");
	selectMenu(url);
})
//选择菜单并加载相应的页面
function selectMenu(url) {
	//修改菜单状态
	menuArr.each(function(index, value) {
		$(value).removeClass("active");
		if ($(value).attr("url") == url) {
			$(this).addClass("active");
			curUrl = url;
		}
	})
	//加载页面
	$.get(url, {}, function(res) {
		$("#main").html(res);
	}, "html");
}

//加载默认页面（第一个）
selectMenu(curUrl);
//主页面添加、修改、删除、查询时，调用该方法
function reload(params) {
	$.get(curUrl, params, function(res) {
		$("#main").html(res);
	}, "html");
}
//在主页面打开其它页面时调用
function openPage(url) {
	$.get(url, {}, function(res) {
		$("#main").html(res);
	}, "html");
}
