$(document).ready(function(){
    function GetQueryString(name)
    {
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)
       { 
        return  unescape(r[2]);
    }
        else
        {
            return null;
        } 
    }
     
     var $type=GetQueryString("type");
     console.log($type);
	$.ajax({
                        type: "post",
                        url: "../servlet/DispatchServlet",
                        data:{'controller':'FaultTypeController','system':'power','faultType':$type},
                        dataType: "json",
                        success: function (simuData) {
                        	console.log(simuData);
                            addJsonToTableTen(simuData, 'tableTbody');
                        },
                        error: function(data){
                        	alert(data);
                        },
                    });


        function addJsonToTableTen(jsonData,tableId){

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
        header+="</tr>";
        Tbody.append(header);

        var tableContentLength=0;//计算表格行数
        for(var numRow in tableContent){
            tableContentLength++;
        }

        var pageNum=Math.ceil(tableContentLength/8);//表格页数
        var pageContent=new Array();//数组可存入每页表格内容
        var array=0;
        for(var numRow in tableContent){//存入表格数据
            var row="<tr class='forPageChange'>";
            var rowContent=tableContent[numRow];
            for(var i=0;i<colLength;i++){
                row+='<td>'+rowContent[i]+'</td>';
            }
            row+="</tr>";
            pageContent[array]+=row;
            if(numRow%8==0){array++;}
        }

        var blankNum=8*pageNum-tableContentLength;//在行数不位20整数倍时添加空白行
        if(blankNum!=0){
            for(var j=0;j<blankNum;j++){
                var rowBlank="<tr class='forPageChange'>";
                for(var k=0;k<colLength;k++){
                    rowBlank+='<td></td>';
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
         });

         $('#pageNext').click(function(){//下一页
         if(currentPage==(pageNum-1)){return}
         currentPage++;
         $('.forPageChange').remove();
         $('#pageChange').before(pageContent[currentPage]);
         });
    }
});