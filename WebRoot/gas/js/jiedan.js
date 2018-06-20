$(document).ready(function(){
	startTime();
    var $excelName='';//导出报表的时候传送过去的参数
	var timeinter;
    function showPwd(){
        //alert('查看'+title+'第'+name+'节点设备');
        asyncbox.open({
            title:'修改密码',
            width:800,
            height:300,
            url:'modifypwd.html'
            //url:www.w3school.com.cn/tiy/t.asp?f=html_table_test
        });
    };
    	$(".functionButton").remove();
    	$("#__calendarPanel").css('visibility','hidden');
    	$("#closeButton").click();
    	$("#calender").remove();
		$("#allmap").css("display","none");
    	$('.widget-header button').css("display","none");
        $(".widget-header").css("display","block");
        $(".widget-content").css("display","block");
    	if(timeinter!=null){
        	clearInterval(timeinter);
    	}
        $('#treeNode').css('display','none');
        $('#spanTable').removeClass().addClass('span12');
        $('.widget-header i').removeClass().addClass("icon-list-alt");
        $('.widget-header h3').html('故障异常详情');
        $('.widget-header').append('<div id="pointShift1" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-56em;">所有异常</div>');
        $('.widget-header').append('<div id="pointShift2" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-56em;">未处置</div>');
        $('.widget-header').append('<div id="pointShift3" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-56em;">处置中</div>');
        $('.widget-header').append('<div id="pointShift4" class="functionButton" style="position:absolution;top:5px; width:5em;float:right;left:-56em;">已处置</div>');
        $excelName='CURRENTFAULT';
        $("#tableTbody tr").remove();
        
        $(".table").prepend('<tbody id="calender"><tr><td colspan="20" style="vertical-align:middle;">起始时间<input style="margin-bottom:0px;" type="text" id="startDate" size="22" maxlength="10" onclick="new Calendar().show(this);" /><span style="padding-left:5em;">截止时间</span><input style="margin-bottom:0px;" type="text" id="endDate" size="20" maxlength="10" onclick="new Calendar().show(this);" /><button style="width:10%;margin-left:5em;" id="getFault">查询</button></td></tr><tbody>')
        $("#tableTbody").append("<tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr><tr><td>　</td></tr>");
        
        $("#getFault").click(function(){
        	//alert($("#startDate").val());
        	$("#closeButton").click();
        	$.ajax({
                type: "post",
                url: "servlet/CurrentServlet",
                data:{'servletName':'CURRENTFAULT','startDate':$("#startDate").val(),'endDate':$("#endDate").val()},
                dataType: "json",
                success: function (simuData) {
                	//console.log(JSON.stringify(simuData));
                	//alert(JSON.stringify(simuData.tableContent));
                    addJsonToTableTen(simuData, 'tableTbody','all');
                    //$("#tableTbody").prepend('<tr><td colspan="20">起始时间<input type="text" id="startDate" size="22" maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" /><span style="padding-left:5em;">截止时间</span><input type="text" id="endDate" size="20" maxlength="10" onclick="new Calendar().show(this);" readonly="readonly" /><button style="width:10%;" id="getFault">查询</button></td></tr>')
                },
            });
        	
        });
        $("#pointShift3").click(function (){
            $.ajax({
                type: "post",
                url: "servlet/WeibaoServlet",
                data:{'servletName':'CURRENTCURRENTFAULTCZZ'},
                dataType: "json",
                success: function (simuData) {
                	//console.log(JSON.stringify(simuData));
                    addJsonToTableTen(simuData, 'tableTbody','czz');
                },
            });
        });
        $("#pointShift2").click(function (){
            $.ajax({
                type: "post",
                url: "http://localhost:8080/DemoC/servlet/WeibaoServlet",
                data:{'servletName':'CURRENTCURRENTFAULTWCZ'},
                dataType: "json",
                success: function (simuData) {
                	//console.log(JSON.stringify(simuData));
                    addJsonToTableTen(simuData, 'tableTbody','wcz');
                },
            });
        });
        $("#pointShift1").click(function (){
            $.ajax({
                type: "post",
                url: "http://localhost:8080/DemoC/servlet/WeibaoServlet",
                data:{'servletName':'CURRENTCURRENTFAULT'},
                dataType: "json",
                success: function (simuData) {
                	//console.log(JSON.stringify(simuData));
                    addJsonToTableTen(simuData, 'tableTbody','all');
                },
            });
        });
        $("#pointShift4").click(function (){
            $.ajax({
                type: "post",
                url: "http://localhost:8080/DemoC/servlet/WeibaoServlet",
                data:{'servletName':'CURRENTCURRENTFAULTYCZ'},
                dataType: "json",
                success: function (simuData) {
                	//console.log(JSON.stringify(simuData));
                    addJsonToTableTen(simuData, 'tableTbody','ycz');
                },
            });
        });
        /*        $.ajax({
            type: "post",
            url: "http://localhost:8080/DemoP/servlet/PowerServlet",
            data:{'servletName':'POWERFAULT'},
            dataType: "json",
            success: function (simuData) {
            	//console.log(JSON.stringify(simuData));
                addJsonToTableTen(simuData, 'tableTbody');
            },
        });*/
        $(".active").removeClass("active");
        $("#but4").parent().addClass("active");
        $("#excel").css("display","block");

    $("#excel").click(function(){
            window.open("http://localhost:8080/DemoC/servlet/ExcelServlet?excelName="+$excelName);
    });
    $('#logout').click(function(){
		$.ajax({
            type: "post",
            url: "http://localhost:8080/DemoC/servlet/UserServlet",
            data:{'Action':'logout'},
            success: function (simuData) {
            	if((simuData=="注销成功！")){
            		alert(simuData);
            		window.location.href="login.html";
            	}
            },
        });	
	});
    $("#modifyPwd").click(function(){
    	showPwd();
	});




	//树结构
    function activateTree(){
    	$( '.tree li' ).each( function() {
            if( $( this ).children( 'ul' ).length > 0 ) {
                $( this ).addClass( 'parent' );
            }
        });
        $( '.tree li.parent > a' ).click( function( ) {
            $( this ).parent().toggleClass( 'active' );
            $( this ).parent().children( 'ul' ).slideToggle( 'fast' );
        });
        $( '.tree li.parent > a' ).click();
    }
	$( '.tree li' ).each( function() {
        if( $( this ).children( 'ul' ).length > 0 ) {
            $( this ).addClass( 'parent' );
        }
    });
    $( '.tree li.parent > a' ).click( function( ) {
        $( this ).parent().toggleClass( 'active' );
        $( this ).parent().children( 'ul' ).slideToggle( 'fast' );
    });
    $( '.tree li.parent > a' ).click();







});

/*向tableId的TBODY表格添加jsonData数据，每页8行内容，包含分页代码*/
function addJsonToTableTen(jsonData,tableId,state){

/*        var tableHeader=jsonData["tableItem"].tableHeader;
    var tableContent=jsonData["tableItem"].tableContent;*/
	var tableHeader=jsonData.tableHeader;
    var tableContent=jsonData.tableContent;
    var colLength=tableHeader.length;


    $("#"+tableId+" tr").remove();
    var Tbody=$("#"+tableId);

    var header="<tr>";//添加表格标题
    for(var trTxt in tableHeader){
        header+='<th>'+tableHeader[trTxt]+'</th>';
    }
   
    if(state=='czz'||state=='wcz'){
    	header+='<th>'+'操作'+'</th>';
    }
    header+="</tr>";
    Tbody.append(header);

    var tableContentLength=0;//计算表格行数
    for(var numRow in tableContent){
        tableContentLength++;
    }

    var pageNum=Math.ceil(tableContentLength/15);//表格页数

    if(pageNum==0){
    	pageNum=1;
    }
    var pageContent=new Array();//数组可存入每页表格内容
    var array=0;
    for(var numRow in tableContent){//存入表格数据
        var row="<tr class='forPageChange'>";
        var rowContent=tableContent[numRow];
        for(var i=0;i<colLength;i++){
            row+='<td>'+rowContent[i]+'</td>';
        }
        if(state=='wcz'){
        	row+='<td>'+' <div class=\"badge\" onclick=\"updatefaultwczsate(\''+ rowContent[0] +'\')\"'+'style=\"cursor:pointer;background-color:#FFC107;\"id=\"'+rowContent[0]+'\">'+ '接单' +'</div>'+'</td>';
        }
        else if(state=='czz'){
        	row+='<td>'+' <div class=\"badge\" onclick=\"updatefaultczzsate(\''+ rowContent[0] +'\')\"'+'style=\"cursor:pointer;background-color:#FFC107;\"id=\"'+rowContent[0]+'\">'+'确认完成' +'</div>'+'</td>';
        }
        row+="</tr>";
        pageContent[array]+=row;
        if(numRow%15==0){array++;}
    }

    var blankNum=15*pageNum-tableContentLength;//在行数不位20整数倍时添加空白行
    if(blankNum!=0){
        for(var j=0;j<blankNum;j++){
            var rowBlank="<tr class='forPageChange'>";
            for(var k=0;k<colLength;k++){
                rowBlank+='<td>　</td>';
            }
            if(state=='wcz'){
            	rowBlank+='<td> </td>';
            }
            else if(state=='czz'){
            	rowBlank+='<td> </td>';
            }
            rowBlank+="</tr>";
            pageContent[array]+=rowBlank;
        }
    }
    var currentPage=0;
    Tbody.append(pageContent[currentPage]);
    Tbody.append('<tr id="pageChange"><td colspan="20">第 <span id="pageCurrent">'+(currentPage+1)+'</span> 页　共 <span id="pageTotal">'+pageNum+'</span> 页　<span id="pagePreview">上一页</span>　<span id="pageNext">下一页</span></td></tr>');
    $('#pagePreview').css("cursor","pointer");
    $('#pageNext').css("cursor","pointer");

    $('#pagePreview').click(function(){//上一页
     if(currentPage==0){return}
     currentPage--;
     $('.forPageChange').remove();
     $('#pageChange').before(pageContent[currentPage]);
     $('#pageCurrent').html(currentPage+1);
     });

     $('#pageNext').click(function(){//下一页
     if(currentPage==(pageNum-1)){return}
     currentPage++;
     $('.forPageChange').remove();
     $('#pageChange').before(pageContent[currentPage]);   
     $('#pageCurrent').html(currentPage+1);
     });
     
     //alert(111+JSON.stringify(tableContent));
     if(JSON.stringify(tableContent)=='{}'){
    	 //alert(222);
    	 $('.forPageChange').remove();
    	 $('#pageChange').before("<tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr>");
     }
}
/*------------------------------------*/

/*加载右上角时间*/
function startTime()
	{
		var today=new Date();
		var h=today.getHours();
		var m=today.getMinutes();
		var s=today.getSeconds();
		var w=today.getDay();
		var y=today.getFullYear();
		var mt=today.getMonth()+1;
		var d=today.getDate();
		//var mms=today.getMilliseconds();
		var weekChn = ["日","一","二","三","四","五","六"];
		
		// add a zero in front of numbers<10
		h=checkTime(h);
		m=checkTime(m);
		s=checkTime(s);

		$("#nowTime").text(h+":"+m+":"+s);
		$("#nowDate").text(y+"年"+mt+"月"+d+"日");
		$("#nowWeek").text("星期"+weekChn[w]);
		t=setTimeout('startTime()',1000);
	}
function checkTime(i)
	{
		if (i<10) {i="0" + i}
  		return i
	}
/*-------------*/

//写cookies

function setCookie(name,value)
{
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

//读取cookies
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg))
 
        return unescape(arr[2]);
    else
        return null;
}

//删除cookies
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
} 
