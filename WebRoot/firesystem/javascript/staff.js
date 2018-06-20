$(document).ready(function(){
	$.ajax({//加载数据
        type: "post",
        url: "servlet/DispatchServlet",
        data:{'controller':'TableItem','enum':'staffList'},
        dataType: "json",
        success: function (simuData) {
        	var $simu=simuData.tableItem.tableContent;
        	for(var i in $simu){
        		var tmparray=$simu[i];
        		if(tmparray[4]==1){
        			tmparray[4]='管理员';
        		}else if(tmparray[4]==3){
        			tmparray[4]='维保人员';
        		}else{
        			tmparray[4]='普通用户';
        		}
        		tmparray.push('<button style="margin-top:-3px;margin-bottom:-3px;" id="modifyUserButton'+tmparray[0]+'">修改</button><button style="margin-top:-3px;margin-bottom:-3px;" id="deleteUserButton'+tmparray[0]+'">删除</button>');
        	}
            addJsonToTable(simuData, 'tableTbody');
        	$('button[id^="deleteUserButton"]').click(function(){//删除用户
        		var $userId=(this.id).substring(16);
        		if(confirm("是否确认删除此人员？")){
                    $.ajax({
                        type: "post",
                        url: "servlet/DispatchServlet",
                        data:{'controller':'DeleteStaff','userId':$userId},
                        success: function (simuData) {
                        	if(simuData=="success"){
                        		alert("删除成功");
                        	}else{
                        		alert("删除失败");
                        	}
                        	window.location.href="firesystem/staff.jsp"
                        },
                    });
        		}
        	});
        	$('button[id^="modifyUserButton"]').click(function(){//修改用户
        		$('#staffList').css('display','none');
        		$('#modifyStaffTable').css('display','block');
        		$('#addStaffTable').css('display','none');
        		var $userId=(this.id).substring(16);
                $.ajax({
                	type: "post",
                    url: "servlet/DispatchServlet",
                    data:{'controller':'QueryStaffById','staffId':$userId},
                    dataType: "json",
                    success: function (simuData) {
                    	$('#staffIdMod').html($userId);
                        $('#staffNameMod').val(simuData.StaffName);
                        $('#staffPhoneMod').val(simuData.StaffPhoneNumber);
                        $('#staffRspMod').val(simuData.StaffWorkArea);
                        $('#staffPwd1Mod').val(simuData.StaffPassword);
                        $('#staffPwd2Mod').val(simuData.StaffPassword);
                        if(simuData.StaffPermissions=="1"){
                            $('#staffThorMod').prop("checked",true);
                        }else{
                        	$('#staffThorMod').prop("checked",false);
                        }
                    	$('#cancelModifyStaff').click(function(){//修改用户取消
                    		window.location.href="firesystem/staff.jsp";
                    	});
                        $('#submitModifyStaff').click(function(){//修改用户提交
                      		
                      		var $admin;
                      		if($("#staffThor").is(':checked')){
                    			$admin=1;
                    		}else if($("#staffThor1").is(':checked')){
                    			$admin=3;
                    		}else{
                    			$admin=2;
                    		}
                      		var $username=$('#staffNameMod').val();
                      		var $phone=$('#staffPhoneMod').val();
                      		var $workArea=$('#staffRspMod').val();
                      		var $userPwd1=$('#staffPwd1Mod').val();
                      		var $userPwd2=$('#staffPwd2Mod').val();
                    		if($username==''){
                    			alert('姓名不能为空！');
                    		}else if($phone==''){
                    			alert('电话不能为空！');
                    		}else if($userPwd1==''||$userPwd2==''){
                    			alert('密码不能为空！');
                    		}else if($userPwd1!=$userPwd2){
                    			alert('两次密码不一致！');
                    		}                      		
                      		$.ajax({
                                  type: "post",
                                  url: "servlet/DispatchServlet",
                                  data:{'controller':'UpdateStaffById','name':$username,'phone':$phone,'workArea':$workArea,'userPwd1':$userPwd1,'admin':$admin,"userId":$userId},
                                  success: function (message) {
                                      if(message=="success"){
                                    	  alert("修改成功");
                                      }else{
                                    	  alert("修改失败");
                                      }
                                      window.location.href="firesystem/staff.jsp"
                                  },
                              });
                    	});
                    }
               });
        	});
        },
    });
	$('#addStaff').click(function(){//新增用户
		$('#staffList').css('display','none');
		$('#modifyStaffTable').css('display','none');
		$('#addStaffTable').css('display','block');
	});
	$('#cancelAddStaff').click(function(){//新增用户取消
		window.location.href="firesystem/staff.jsp";
	});
	$("#submitAddStaff").click(function(){//新增用户提交
		
		var $admin;
		if($("#staffThor").is(':checked')){
			$admin=1;
		}else if($("#staffThor1").is(':checked')){
			$admin=3;
		}else{
			$admin=2;
		}
		var $username=$('#staffName').val();
		var $phone=$('#staffPhone').val();
		var $workArea=$('#staffRsp').val();
		var $userPwd1=$('#staffPwd1').val();
		var $userPwd2=$('#staffPwd2').val();
		if($username==''){
			alert('姓名不能为空！');
		}else if($phone==''){
			alert('电话不能为空！');
		}else if($userPwd1==''||$userPwd2==''){
			alert('密码不能为空！');
		}else if($userPwd1!=$userPwd2){
			alert('两次密码不一致！');
		}else{
			$.ajax({
	            type: "post",
	            url: "servlet/DispatchServlet",
	            data:{'controller':'NewStaff','name':$username,'phone':$phone,'workArea':$workArea,'userPwd1':$userPwd1,'admin':$admin},
	            success: function (message) {
	                if(message=="failure"){
	                	alert("新增失败");
	                }else{
	                	alert("新增成功:您的帐号ID为"+message);
	                }
	                window.location.href="firesystem/staff.jsp"
	            },
	        });
		}
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