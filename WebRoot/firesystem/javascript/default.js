$(document).ready(function(){
	var $menuBar=$('.title .LeftMenuDiv');
	$menuBar.eq(0).click(function(){
		window.location.href="firesystem/index.jsp";
	});
	$menuBar.eq(1).click(function(){
		window.location.href="firesystem/map.jsp";
	});
	$menuBar.eq(2).click(function(){
		window.location.href="firesystem/exception.jsp";
	});
	$menuBar.eq(3).click(function(){
		window.location.href="firesystem/staff.jsp";
	});
	$menuBar.eq(4).click(function(){
		window.location.href="firesystem/subhome.jsp?sys=3";
	});
	$menuBar.eq(5).click(function(){
		window.location.href="firesystem/subhome.jsp?sys=4";
	});
	$menuBar.eq(6).click(function(){
		window.location.href="firesystem/subhome.jsp?sys=6";
	});
	$menuBar.eq(7).click(function(){
		window.location.href="firesystem/subhome.jsp?sys=5";
	});
	
	$('#logout').click(function(){
  		$.ajax({
            type: "post",
            url: "servlet/DispatchServlet",
            data:{'controller':'Logout'},
            success: function (simuData) {
            	if((simuData=="success")){
            		alert('注销成功');
            		window.location.href="firesystem/login.html";
            	}else{
            		alert('注销失败！');
            	}
            },
        });
	});
	$('#modifyPassword').click(function(){
		asyncbox.open({
            title:'修改密码',
            width:800,
            height:300,
            url:'firesystem/modifypwd.html'
            //url:www.w3school.com.cn/tiy/t.asp?f=html_table_test
        });
	});

});
function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
} 