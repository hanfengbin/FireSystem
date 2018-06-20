$(document).ready(function(){
	$.ajax({//加载数据
        type: "post",
        url: "servlet/DispatchServlet",
        data:{'controller':'TableItem','enum':'faultScreenByAppend'},
        dataType: "json",
        success: function (simuData) {
        	
            addJsonToTable(simuData, 'tableTbody');
        },
    });	
/*	$.ajax({//楼栋select
        type: "post",
        url: "servlet/DispatchServlet",
        data:{'controller':'BuildingController','enum':'8'},
        dataType: "json",
        success: function (simuData) {
        	var message=simuData.tableContent;
        	var $buildSelect=$('#buildSelect');
        	for(id in message){
        		$buildSelect.append('<option value='+message[id].buildingID+'>'+message[id].buildingName+'</option>');
        	}       	
        },
    });	*/
	$('#exceptionQueryButton').click(function(){//查询按钮点击事件
		var $exceptionType=$('#exceptionSelect option:selected').text();
		var $stateText=$('#stateSelect option:selected').text();
		var $startingTime=$('#startingTime').val();
		var $endingTime=$('#endingTime').val();
		var $exceptionQuery={};
		$exceptionQuery.controller="FaultScreen";
		$exceptionQuery.enum="faultScreenByAppend";
		$exceptionQuery.faultTimeStart="1971-01-01";
		var myDate = new Date();
		var dateStr = myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate();
		$exceptionQuery.faultTimeEnd=dateStr;
		if($exceptionType!=''&&$exceptionType!=null)
			$exceptionQuery.faultCategory=$exceptionType;
		if($stateText!=''&&$stateText!=null)
			$exceptionQuery.mvfaultDealState=$stateText;
		if($stateText!=''&&$stateText!=null)
			$exceptionQuery.mvfaultDealState=$stateText;
		if($startingTime!=''&&$startingTime!=null)
			$exceptionQuery.faultTimeStart=$startingTime;
		if($endingTime!=''&&$endingTime!=null)
			$exceptionQuery.faultTimeEnd=$endingTime;
		$.ajax({//加载数据
	        type: "post",
	        url: "servlet/DispatchServlet",
	        data:$exceptionQuery,
	        dataType: "json",
	        success: function (simuData) {
	            addJsonToTable(simuData, 'tableTbody');
	        },
	    });
	});
});



function addJsonToTable(jsonData,tableId){

	    var tableHeader=jsonData["tableItem"].tableHeader;
	    var tableContent=jsonData["tableItem"].tableContent;

	    var colLength=tableHeader.length;

	    $("#"+tableId+" tr").remove();
	    var Tbody=$("#"+tableId);
	    
	    var header="<tr>";//添加表格标题
	    for(var trTxt in tableHeader){
	        header+='<th>'+tableHeader[trTxt]+'</th>';
	    }
	    header+="</tr>";
	    Tbody.prev().html(header);

	    var tableContentLength=0;//计算表格行数
	    for(var numRow in tableContent){
	        tableContentLength++;
	    }

	    var pageNum=Math.ceil(tableContentLength/10);//表格页数

	    if(pageNum==0){
	    	pageNum=1;
	    }
	    var pageContent=new Array();//数组可存入每页表格内容
	    var array=0;
	    for(var numRow in tableContent){//存入表格数据
	        var row="<tr class='forPageChange'>";
	        var rowContent=tableContent[numRow];
	        for(var i=0;i<colLength;i++){
	        	if(rowContent[i]==null){
	        		rowContent[i]='--';
	        	}
	            row+='<td>'+rowContent[i]+'</td>';
	        }
	        row+="</tr>";
	        pageContent[array]+=row;
	        if(numRow%10==0){array++;}
	    }

	    var blankNum=10*pageNum-tableContentLength;//在行数不位20整数倍时添加空白行
	    if(blankNum!=0){
	        for(var j=0;j<blankNum;j++){
	            var rowBlank="<tr class='forPageChange'>";
	            for(var k=0;k<colLength;k++){
	                rowBlank+='<td>　</td>';
	            }
	            rowBlank+="</tr>";
	            pageContent[array]+=rowBlank;
	        }
	    }
	    var currentPage=0;
	    Tbody.append(pageContent[currentPage]);
	    Tbody.append('<tr id="pageChange"><td colspan="20">第 <span id="pageCurrent">'+(currentPage+1)+'</span> 页　共 <span id="pageTotal">'+pageNum+'</span> 页　<span id="pagePreview">上一页</span>　<span id="pageNext">下一页</span></td></tr>');

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
	     $('#pagePreview').css("cursor","pointer");
	     $('#pageNext').css("cursor","pointer");
	     //alert(111+JSON.stringify(tableContent));
	     if(JSON.stringify(tableContent)=='{}'){
	    	 //alert(222);
	    	 $('.forPageChange').remove();
	    	 $('#pageChange').before("<tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr>");
	     }
}