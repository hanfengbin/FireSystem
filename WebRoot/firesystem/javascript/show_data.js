$(document).ready(function(){

	var netkey = window.location.search;
	if (netkey == '') {
		// console.log(111);
		window.location.href = "index.html";
	}

	/* 滚动异常*/
	$('.profile li').click(function(){
		$(this).addClass('clicking').siblings('li').removeClass('clicking');
	});
	$('.profile li').eq(0).click();
	$("#warning").click(function(){
		//更改内容
		var olddiv=document.getElementById("abnormalDivDetail");
		for(var i=olddiv.childNodes.length-1;i>=0;i--)
		{ var del=olddiv.removeChild(olddiv.childNodes[i]);
			del=null;
		}
		//$('#abnormalDivDetail').html($alarmEvent);
	});
	$("#Prewarning").click(function(){
		//更改内容
		var olddiv=document.getElementById("abnormalDivDetail");
		for(var i=olddiv.childNodes.length-1;i>=0;i--)
		{ var del=olddiv.removeChild(olddiv.childNodes[i]);
			del=null;
		}
		//$('#abnormalDivDetail').html($warningEvent);
	});

	$("#abnormalEventUnhandled").click(function(){
		// 更改内容
		var olddiv=document.getElementById("abnormalDivDetail");
		for(var i=olddiv.childNodes.length-1;i>=0;i--)
		{ var del=olddiv.removeChild(olddiv.childNodes[i]);
			del=null;
		}
		//$('#abnormalDivDetail').html($unhandledEvent);
	});

	$("#abnormalEventHandling").click(function(){
		//更改内容
		var olddiv=document.getElementById("abnormalDivDetail");
		for(var i=olddiv.childNodes.length-1;i>=0;i--)
		{ var del=olddiv.removeChild(olddiv.childNodes[i]);
			del=null;
		}
		//$('#abnormalDivDetail').html($handledEvent);
	});


	var nm = netkey.split('?')[1].split('&')[0].split('=')[1];
	var pw = netkey.split('?')[1].split('&')[1].split('=')[1];
	var LeftMenuDiv = $(".LeftMenuDiv"), datanav = $(".data"), list = $(".list"),
		listOpen = $(".list-zhankai"), loginout = $(".loginout"),
		changepw = $(".changepw"), loginname = $(".loginname"),
		oldpw = $(".old-pw"), newpw = $(".new-pw"), newpw2 = $(".new-pw2"),
		changepwbtn = $(".btnchangepw");
	$(".loginren").html(nm);

	LeftMenuDiv.eq(0).addClass("LeftMenuDivChosen");

	loginout.on("click", function(){
		// console.log(222);
		window.location.href = "index.html";
	});

	datanav.hide();

	datanav.eq(0).show(10, function(){                                     //显示实时态势那一页
       loadpage(0);
	});

	LeftMenuDiv.on("click", function(){
		$(".LeftMenuDiv").removeClass("LeftMenuDivChosen");
		$(this).addClass("LeftMenuDivChosen");
		var indexNumber = LeftMenuDiv.index(this);
		//alert(indexNumber);
		console.log(indexNumber);
		datanav.hide();                                //当点击左边菜单栏的时候隐藏其他页面显示点击页面
		listOpen.hide();
		datanav.eq(indexNumber).show(10, function(){
            loadpage(indexNumber);						//传入indexNumber索引，如果等于1那么就生成百度地图
		});
	});
	//右上角的切换功能
	$('.zhuji').on("click", function(){
		datanav.hide();
		datanav.eq(8).show();
	});
	$('.node').on("click", function(){
		datanav.hide();
		datanav.eq(9).show();
	});
	$('.abnormalControl').on("click", function(){
		datanav.hide();
		datanav.eq(10).show();
	});

//**********修改密码*************//
	changepw.on("click", function(){
		datanav.hide();
		datanav.eq(11).show();
		oldpw.val('');
		newpw.val('');
		newpw2.val('');
		changepwbtn.attr("disabled", true);
		$(".loginming").html(nm);
	});
	oldpw.blur(function(){
		var oldPw = $(this).val();
		if (oldPw != pw) {
			$(".old-pw-info").html('旧密码输入错误！');
		}else{
			$(".old-pw-info").html('');
		}
	});
	newpw.blur(function(){
		var mima = newpw.val();
		if (mima == '') {
			$(".new-pw-info").html('请输入修改的密码！');
		}else if (mima.length < 6 || mima.length > 12) {
			$(".new-pw-info").html('请输入正确的密码格式！');
		}else{
			$(".new-pw-info").html('');
		}
	});
	newpw2.blur(function(){
		var mima2 = newpw2.val();
		if (newpw.val() != mima2) {
			changepwbtn.attr("disabled", true);
			$(".new-pw-info2").html('两次输入密码不相同！');
		}else if (newpw.val() == mima2 && $(".new-pw-info").html() == '') {
			$(".new-pw-info2").html('');
			changepwbtn.attr("disabled", false);
		}
	});
	changepwbtn.on("click", function(){
		alert(222);
	});
//**********修改密码end*************//

//**********节点设备的表切换（正常节点、异常节点、所有节点）*************//
	$(".nodeswitch").eq(0).addClass("btndivadd");
	$(".nodeswitch").on("click", function() {
		// alert(111);
		$(".nodeswitch").removeClass("btndivadd");
		$(this).addClass("btndivadd");
        var indexNumber =$(".nodeswitch").index(this);
        if(indexNumber==0){
            $.ajax({
                type: "post",
                url: "tableData.do",
                data:{'tablename':'nodedevice'},
                dataType: "json",
                success: function (simuData) {
                    //console.log(JSON.stringify(simuData));
                    console.log(simuData);
                    addJsonToTable(simuData,'nodetable');
                },
            });
		}else if(indexNumber==1){
            $.ajax({
                type: "post",
                url: "tableData.do",
                data:{'tablename':'nodedevicenomal'},
                dataType: "json",
                success: function (simuData) {
                    //console.log(JSON.stringify(simuData));
                    console.log(simuData);
                    addJsonToTable(simuData, 'nodetable');
                },
            });
		}else {
            $.ajax({
                type: "post",
                url: "tableData.do",
                data:{'tablename':'nodedeviceabnomal'},
                dataType: "json",
                success: function (simuData) {
                    //console.log(JSON.stringify(simuData));
                    console.log(simuData);
                    addJsonToTable(simuData, 'nodetable');
                },
            });
		}
	});
//**********节点设备end*************//
	//*******人员管理*****//
    $(".personswitch").eq(0).addClass("btndivadd");
    $(".personswitch").on("click", function() {
        // alert(111);
        $(".personswitch").removeClass("btndivadd");
        $(this).addClass("btndivadd");
    });
//*******人员管理end*****//
//**********主机表切换（）*************//
	$(".hostswitch").eq(0).addClass("btndivadd");
	$(".hostswitch").on("click", function() {
		// alert(111);
		$(".hostswitch").removeClass("btndivadd");
		$(this).addClass("btndivadd");
        var indexNumber =$(".hostswitch").index(this);
        if(indexNumber==0){
            $.ajax({
                type: "post",
                url: "tableData.do",
                data:{'tablename':'host'},
                dataType: "json",
                success: function (simuData) {
                    //console.log(JSON.stringify(simuData));
                    console.log(simuData);
                    addJsonToTable(simuData,'hosttable');
                },
            });
        }else if(indexNumber==1){
            $.ajax({
                type: "post",
                url: "tableData.do",
                data:{'tablename':'hostnomal'},
                dataType: "json",
                success: function (simuData) {
                    //console.log(JSON.stringify(simuData));
                    console.log(simuData);
                    addJsonToTable(simuData, 'hosttable');
                },
            });
        }else {
            $.ajax({
                type: "post",
                url: "tableData.do",
                data:{'tablename':'hostabnomal'},
                dataType: "json",
                success: function (simuData) {
                    //console.log(JSON.stringify(simuData));
                    console.log(simuData);
                    addJsonToTable(simuData, 'hosttable');
                },
            });
        }
	});
//**********主机表切换end*************//



})

function loadpage(dataPage){
	switch (dataPage){
		case 0:
		{
		console.log(0);
	}
		    break;//首页
		case 1: {

		console.log("1 selected");
		var data1 = $("#data1");
		var map = new BMap.Map("data1");//创建地图实例在div id=data1的块里
		var point = new BMap.Point(106.476757,29.571593);//地图中心点坐标
		var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});
		var top_left_navigation = new BMap.NavigationControl();
		map.centerAndZoom(point, 18);//确定地图中心点和缩放大小
		map.addControl(new BMap.MapTypeControl());
		map.addControl(top_left_control);        
		map.addControl(top_left_navigation);
		map.enableScrollWheelZoom(true);
		var marker = new BMap.Marker(point); 
		map.addOverlay(marker);
		console.log("1 selected again");
      		var sContent =
                '<div style="width: 300px; height: 20px; font-size: 13px; color: #777">'
                + '<span style="font-size: 14px;color: rgb(77, 77, 77);font-weight: bold;">重庆大学主教学楼</span>'
                + '<span class="errorTable" style="font-size: 10px;color: rgb(61, 109, 204);margin-left: 5px;">更多»</span>'
                + '</div>'
                + '<div style="width: 100%; height: 100%; position: relative;margin-top: 5px">'
						+ '<img id="zhujiao" style="width: 100%; height: 100%;" src="images/2_1.jpg" />'
						+ '<div class="back" style="position: absolute; top: 127px;    background-color: rgba(0, 0, 0, 0.6);width: 100%;height: 22px">'
								+ '<span style="color: white;font-size: 10px;margin-left: 5px">进入全景»</span>'
						+ '</div>'
				+ '</div>'
				+ '<div style="width: 100%; height: 40px; margin-top: 5px;color: rgb(77, 77, 77);">'
					+ '<div style="width: 33%; height: 30px; text-align: center; margin-top: 5px; float: left;  font-size: 14px;line-height: 30px">'
						+ '<i class="icon-desktop" ></i>'
						+ '<span style="margin-left: 10px">主机： </span>'
						+ '<span style="font-size: 14px;margin: 5px 0px ">3</span>'
					+ '</div>'
					+ '<div style="width: 33%; height: 30px; text-align: center; margin-top: 5px; float: left; font-size: 14px;line-height: 30px">'
						+ '<i class="icon-sitemap" ></i>'
						+ '<span style="margin-left: 10px">节点： </span>'
						+ '<span style="font-size: 14px;margin: 5px 0px">24</span>'
					+ '</div>'
					+ '<div style="width: 33%; height: 30px; text-align: center; margin-top: 5px; float: left; font-size: 14px;line-height: 30px">'
						+ '<span class="glyphicon glyphicon-exclamation-sign" ></span>'
						+ '<span style="margin-left: 10px">异常： </span>'
						+ '<span style="font-size: 14px;margin: 5px 0px">35</span>'
					+ '</div>'
				+ '</div>'
				+ '<div style="width: 100%; height: 42px; font-size: 13px; color: #777; padding-left: 10px;">'
					+ '<span class="glyphicon glyphicon-map-marker" ></span>'
					+ '<span style="font-size: 10px;margin-left: 10px">重庆市沙坪坝区沙正街174号重庆大学</span>'
					+ '<p>'
						+ '<a style="float: right;font-size: 10px">导出</a>'
						+ '<span style="float: right;">&nbsp;|&nbsp;</span>'
						+ '<a style="float: right;font-size: 10px">上传图片</a>'
					+ '</p>'
				+ '</div>';
		console.log(sContent);
       	 var infoWindow = new BMap.InfoWindow(sContent);
      	 	 marker.addEventListener("click", function(){
            this.openInfoWindow(infoWindow);
            //图片加载完毕重绘infowindow
            document.getElementById('zhujiao').onload = function (){
                infoWindow.redraw();
            }

			$('.errorTable').on("click", function(){

				var errorTableDiv = document.createElement('div');
				errorTableDiv.style.width = '500px';
				errorTableDiv.style.height = '300px';
				errorTableDiv.style.position = 'fixed';
				errorTableDiv.style.left = '35%';
				errorTableDiv.style.top = '30%';
				errorTableDiv.style.zIndex = '6';
				errorTableDiv.style.backgroundColor = '#ddd';
				errorTableDiv.id = 'errorTableDiv';
				$('.pagepage').append(errorTableDiv);
				var table1 = document.createElement('table');
				table1 = 
					'<table class="table table-striped">'
						+ '<caption"><span style="margin-left: 230px; font-size: 16px;">故障表</span>'
						+ '<span id="exit" style="float: right; margin-right: 20px; font-size: 16px;">X</span>'
						+ '</caption>'
						+ '<thead class="error-his">'
							+ '<tr>'
								+ '<th>故障编号</th>'
								+ '<th>节点号</th>'
								+ '<th>总线号</th>'
								+ '<th>主机号</th>'
								+ '<th>故障类型</th>'
								+ '<th>故障时间</th>'
							+ '</tr>'
						+ '</thead>'
						+ '<tbody>'
							+ '<tr>'
								+ '<td>1</td>'
								+ '<td>2</td>'
								+ '<td>2</td>'
								+ '<td>2</td>'
								+ '<td>2</td>'
								+ '<td>2016-12-12 12:12:12</td>'
							+ '</tr>'
							+ '<tr>'
								+ '<td>1</td>'
								+ '<td>2</td>'
								+ '<td>2</td>'
								+ '<td>2</td>'
								+ '<td>2</td>'
								+ '<td>2016-12-12 12:12:12</td>'
							+ '</tr>'
							+ '<tr>'
								+ '<td>1</td>'
								+ '<td>2</td>'
								+ '<td>2</td>'
								+ '<td>2</td>'
								+ '<td>2</td>'
								+ '<td>2016-12-12 12:12:12</td>'
							+ '</tr>'
						+ '</tbody>'
					+ '</table>'
				document.getElementById('errorTableDiv').innerHTML = table1;
				$("#exit").on('click', function () {
					$("#body1").remove();
					$("#errorTableDiv").remove();
				});
			});
		 });
	}
	break;// 地理信息
		case 2: {

		console.log(2);
	}break;
		case 3:{

        $.ajax({
            type: "post",
            url: "tableData.do",
            data:{'tablename':'host'},
            dataType: "json",
            success: function (simuData) {
                //console.log(JSON.stringify(simuData));
                console.log(simuData);
                addJsonToTable(simuData, 'hosttable');
            },
        });
		console.log(3);
	}
			break;
		case 4: {
			$('.warning').eq(1).click();
		//电气火灾
		// / 基于准备好的dom，初始化echarts实例饼图
		var myChart = echarts.init(document.getElementById('dqhzbingtu'));
		var labelTop = {
			normal : {
				label : {
					show : true,
					position : 'center',
					formatter : "\n{b}",
					textStyle: {
						baseline : 'top',
						fontSize:8,
					},
				},
				labelLine : {
					show : false
				}
			}
		};
		var labelFromatter = {
			normal : {
				color:"#50a856",
				label : {
					formatter : function (params){
						return 100 - params.value + '%'
					},
					textStyle: {
						baseline : 'top',
					}
				}
			},
		}
		var labelBottom = {
			normal : {
				color: '#ccc',
				label : {
					show : true,
					position : 'center',
					formatter : function (params){
						return 100-params.value + '%'
					},
					textStyle: {
						baseline : 'bottom',
						color:"#577fb0",
					}
				},
				labelLine : {
					show : false
				}
			},
			emphasis: {
				color: 'rgba(0,0,0,0)'
			}
		};
		var radius = [40, 55];
		option = {
			tooltip: {
				trigger: 'item',
				formatter: "{a} <br/>{b}: {c} ({d}%)"
			},
			legend: {
				x : 'left',
				y : '20px',
				orient:"vertical",
				data:[
					'正常率',"异常率"
				],
				textStyle:{
					fontSize:8,
				},
			},
			title : {
				text: '节点正常率',
				subtext: 'Point normal rate',
				textStyle:{
					fontSize:10,
					//fontWeight:"normal",
				},
				x: 'center'
			},
			toolbox: {
				show : true,
				feature : {
					// dataView : {show: true, readOnly: false},//文本显示
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			series : [
				{
					name:'饼图',
					type : 'pie',
					// center : ['10%', '30%'],
					radius : radius,
					x: '0%', // for funnel
					itemStyle : labelFromatter,
					data : [
						{name:'异常率', value:46, itemStyle : labelBottom},
						{name:'正常率', value:54,itemStyle : labelTop}

					]
				}
			]
		};
		myChart.setOption(option);
		//柱图
		var myChart = echarts.init(document.getElementById('dqhztiaotu'));
		option = {
			title : {
				text: '异常分类统计',
				subtext: 'Abnormal classification statistics',
				textStyle:{
					fontSize:10,
				},
			},
			grid: {
				left: '0%',
				containLabel: true,
				bottom:"40",
			},
			tooltip : {
				trigger: 'axis',
				axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend: {
				show:true,
				data:['异常数'],
				x:"center",
				y:"190",
			},
			toolbox: {
				show : true,
				feature : {
					// dataView : {show: true, readOnly: false},
					magicType : {show: true, type: ['line', 'bar']},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			xAxis : [
				{
					type : 'value'
				}
			],
			yAxis : [
				{

					type : 'category',
					data : ['电流异常','温度异常','主机故障','节点故障']
				}
			],
			series : [
				{
					name:'异常数',
					type:'bar',
					itemStyle:{
						normal:{
							color:"#61a0a8",
						},
					},
					data:[2.0, 4.9, 7.0, 23.2],
				},

			]
		};
		myChart.setOption(option);
		//zz折线图
		myChart.setOption(option);
		var myChart = echarts.init(document.getElementById('dqhzxiantu'));
		option={
			title: {
				text: '异常统计',
				subtext: 'Exception statistics|For year',
				textStyle:{
					fontSize:14,
				},
				x:"30"
			},
			grid: {
				left: '20',
				containLabel: true,
				bottom:"10",
				right:"20"
			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				show:true,
				data:['异常总数']
			},
			toolbox: {
				show: true,
				feature: {

					//  dataView: {readOnly: false},
					magicType: {type: ['line', 'bar']},
					restore: {},
					saveAsImage: {}
				}
			},
			xAxis:  {
				type: 'category',
				boundaryGap: false,
				data: ['Jan. ','Feb.',' Mar.',' Apr.',' May.','Jun.',' Jul.',' Aug.',' Sept.',' Oct.',' Nov.',' Dec.']
			},
			yAxis: {
				type: 'value',
				axisLabel: {
					formatter: '{value}'
				}
			},
			series: [
				{
					name:'异常总数',
					type:'line',
					data:[5, 11, 15, 13, 12, 13, 10,4,3,6,11,7],
					markPoint: {
						data: [
							{type: 'max', name: '最大值'},

						]
					},
				},

			]

		}
		myChart.setOption(option);
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
        $.ajax({
            type: "post",
            url: "tableData.do",
            data:{'tablename':'nodedevice'},
            dataType: "json",
            success: function (simuData) {
                //console.log(JSON.stringify(simuData));
                console.log(simuData);
                addJsonToTable(simuData, 'nodetable');
            }
        });
		console.log(4);

		}break;
		case 5:{
			$('.warning').eq(2).click();
		//防火门
		// / 基于准备好的dom，初始化echarts实例饼图
		var myChart = echarts.init(document.getElementById('fhmbingtu'));
		var labelTop = {
			normal : {
				label : {
					show : true,
					position : 'center',
					formatter : "\n{b}",
					textStyle: {
						baseline : 'top',
						fontSize:8,
					},
				},
				labelLine : {
					show : false
				}
			}
		};
		var labelFromatter = {
			normal : {
				color:"#50a856",
				label : {
					formatter : function (params){
						return 100 - params.value + '%'
					},
					textStyle: {
						baseline : 'top',
					}
				}
			},
		}
		var labelBottom = {
			normal : {
				color: '#ccc',
				label : {
					show : true,
					position : 'center',
					formatter : function (params){
						return 100-params.value + '%'
					},
					textStyle: {
						baseline : 'bottom',
						color:"#577fb0",
					}
				},
				labelLine : {
					show : false
				}
			},
			emphasis: {
				color: 'rgba(0,0,0,0)'
			}
		};
		var radius = [40, 55];
		option = {
			tooltip: {
				trigger: 'item',
				formatter: "{a} <br/>{b}: {c} ({d}%)"
			},
			legend: {
				x : 'left',
				y : '20px',
				orient:"vertical",
				data:[
					'正常率',"异常率"
				],
				textStyle:{
					fontSize:8,
				},
			},
			title : {
				text: '节点正常率',
				subtext: 'Point normal rate',
				textStyle:{
					fontSize:10,
					//fontWeight:"normal",
				},
				x: 'center'
			},
			toolbox: {
				show : true,
				feature : {
					// dataView : {show: true, readOnly: false},//文本显示
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			series : [
				{
					name:'饼图',
					type : 'pie',
					// center : ['10%', '30%'],
					radius : radius,
					x: '0%', // for funnel
					itemStyle : labelFromatter,
					data : [
						{name:'异常率', value:46, itemStyle : labelBottom},
						{name:'正常率', value:54,itemStyle : labelTop}

					]
				}
			]
		};
		myChart.setOption(option);
		//柱图
		var myChart = echarts.init(document.getElementById('fhmtiaotu'));
		option = {
			title : {
				text: '异常分类统计',
				subtext: 'Abnormal classification statistics',
				textStyle:{
					fontSize:10,
				},
			},
			grid: {
				left: '0%',
				containLabel: true,
				bottom:"40",
			},
			tooltip : {
				trigger: 'axis',
				axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend: {
				show:true,
				data:['异常数'],
				x:"center",
				y:"190",
			},
			toolbox: {
				show : true,
				feature : {
					// dataView : {show: true, readOnly: false},
					magicType : {show: true, type: ['line', 'bar']},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			xAxis : [
				{
					type : 'value'
				}
			],
			yAxis : [
				{

					type : 'category',
					data : ['电流异常','温度异常','主机故障','节点故障']
				}
			],
			series : [
				{
					name:'异常数',
					type:'bar',
					itemStyle:{
						normal:{
							color:"#61a0a8",
						},
					},
					data:[2.0, 4.9, 7.0, 23.2],
				},

			]
		};
		myChart.setOption(option);
		//zz折线图
		myChart.setOption(option);
		var myChart = echarts.init(document.getElementById('fhmxiantu'));
		option={
			title: {
				text: '异常统计',
				subtext: 'Exception statistics|For year',
				textStyle:{
					fontSize:14,
				},
				x:"30"
			},
			grid: {
				left: '20',
				containLabel: true,
				bottom:"10",
				right:"20"
			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				show:true,
				data:['异常总数']
			},
			toolbox: {
				show: true,
				feature: {

					//  dataView: {readOnly: false},
					magicType: {type: ['line', 'bar']},
					restore: {},
					saveAsImage: {}
				}
			},
			xAxis:  {
				type: 'category',
				boundaryGap: false,
				data: ['Jan. ','Feb.',' Mar.',' Apr.',' May.','Jun.',' Jul.',' Aug.',' Sept.',' Oct.',' Nov.',' Dec.']
			},
			yAxis: {
				type: 'value',
				axisLabel: {
					formatter: '{value}'
				}
			},
			series: [
				{
					name:'异常总数',
					type:'line',
					data:[5, 11, 15, 13, 12, 13, 10,4,3,6,11,7],
					markPoint: {
						data: [
							{type: 'max', name: '最大值'},

						]
					},
				},

			]

		}
		myChart.setOption(option);
        $.ajax({
            type: "post",
            url: "tableData.do",
            data:{'tablename':'user'},
            dataType: "json",
            success: function (simuData) {
                //console.log(JSON.stringify(simuData));
                console.log(simuData);
                var $simu=simuData.tableItem.tableContent;
                for(var i in $simu){
                    var tmparray=$simu[i];

                    tmparray.push('<button  id="deleteUserButton"><i class="icon-minus"></i> 删 除</button>');
                }
                addJsonToTable(simuData, 'usertable');
            }
        });

		console.log(5);
	}break;
		case 6:{
			$('.warning').eq(3).click();
		//气体监控
		// / 基于准备好的dom，初始化echarts实例饼图
		var myChart = echarts.init(document.getElementById('qtjkbingtu'));
		var labelTop = {
			normal : {
				label : {
					show : true,
					position : 'center',
					formatter : "\n{b}",
					textStyle: {
						baseline : 'top',
						fontSize:8,
					},
				},
				labelLine : {
					show : false
				}
			}
		};
		var labelFromatter = {
			normal : {
				color:"#50a856",
				label : {
					formatter : function (params){
						return 100 - params.value + '%'
					},
					textStyle: {
						baseline : 'top',
					}
				}
			},
		}
		var labelBottom = {
			normal : {
				color: '#ccc',
				label : {
					show : true,
					position : 'center',
					formatter : function (params){
						return 100-params.value + '%'
					},
					textStyle: {
						baseline : 'bottom',
						color:"#577fb0",
					}
				},
				labelLine : {
					show : false
				}
			},
			emphasis: {
				color: 'rgba(0,0,0,0)'
			}
		};
		var radius = [40, 55];
		option = {
			tooltip: {
				trigger: 'item',
				formatter: "{a} <br/>{b}: {c} ({d}%)"
			},
			legend: {
				x : 'left',
				y : '20px',
				orient:"vertical",
				data:[
					'正常率',"异常率"
				],
				textStyle:{
					fontSize:8,
				},
			},
			title : {
				text: '节点正常率',
				subtext: 'Point normal rate',
				textStyle:{
					fontSize:10,
					//fontWeight:"normal",
				},
				x: 'center'
			},
			toolbox: {
				show : true,
				feature : {
					// dataView : {show: true, readOnly: false},//文本显示
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			series : [
				{
					name:'饼图',
					type : 'pie',
					// center : ['10%', '30%'],
					radius : radius,
					x: '0%', // for funnel
					itemStyle : labelFromatter,
					data : [
						{name:'异常率', value:46, itemStyle : labelBottom},
						{name:'正常率', value:54,itemStyle : labelTop}

					]
				}
			]
		};
		myChart.setOption(option);
		//柱图
		var myChart = echarts.init(document.getElementById('qtjktiaotu'));
		option = {
			title : {
				text: '异常分类统计',
				subtext: 'Abnormal classification statistics',
				textStyle:{
					fontSize:10,
				},
			},
			grid: {
				left: '0%',
				containLabel: true,
				bottom:"40",
			},
			tooltip : {
				trigger: 'axis',
				axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend: {
				show:true,
				data:['异常数'],
				x:"center",
				y:"190",
			},
			toolbox: {
				show : true,
				feature : {
					// dataView : {show: true, readOnly: false},
					magicType : {show: true, type: ['line', 'bar']},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			xAxis : [
				{
					type : 'value'
				}
			],
			yAxis : [
				{

					type : 'category',
					data : ['电流异常','温度异常','主机故障','节点故障']
				}
			],
			series : [
				{
					name:'异常数',
					type:'bar',
					itemStyle:{
						normal:{
							color:"#61a0a8",
						},
					},
					data:[2.0, 4.9, 7.0, 23.2],
				},

			]
		};
		myChart.setOption(option);
		//zz折线图
		myChart.setOption(option);
		var myChart = echarts.init(document.getElementById('qtjkxiantu'));
		option={
			title: {
				text: '异常统计',
				subtext: 'Exception statistics|For year',
				textStyle:{
					fontSize:14,
				},
				x:"30"
			},
			grid: {
				left: '20',
				containLabel: true,
				bottom:"10",
				right:"20"
			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				show:true,
				data:['异常总数']
			},
			toolbox: {
				show: true,
				feature: {

					//  dataView: {readOnly: false},
					magicType: {type: ['line', 'bar']},
					restore: {},
					saveAsImage: {}
				}
			},
			xAxis:  {
				type: 'category',
				boundaryGap: false,
				data: ['Jan. ','Feb.',' Mar.',' Apr.',' May.','Jun.',' Jul.',' Aug.',' Sept.',' Oct.',' Nov.',' Dec.']
			},
			yAxis: {
				type: 'value',
				axisLabel: {
					formatter: '{value}'
				}
			},
			series: [
				{
					name:'异常总数',
					type:'line',
					data:[5, 11, 15, 13, 12, 13, 10,4,3,6,11,7],
					markPoint: {
						data: [
							{type: 'max', name: '最大值'},

						]
					},
				},

			]

		}
		myChart.setOption(option);
		console.log(6);
	}break;
		case 7:{
			$('.warning').eq(4).click();
		//消防电源
		// / 基于准备好的dom，初始化echarts实例饼图
		var myChart = echarts.init(document.getElementById('xfdybingtu'));
		var labelTop = {
			normal : {
				label : {
					show : true,
					position : 'center',
					formatter : "\n{b}",
					textStyle: {
						baseline : 'top',
						fontSize:8,
					},
				},
				labelLine : {
					show : false
				}
			}
		};
		var labelFromatter = {
			normal : {
				color:"#50a856",
				label : {
					formatter : function (params){
						return 100 - params.value + '%'
					},
					textStyle: {
						baseline : 'top',
					}
				}
			},
		}
		var labelBottom = {
			normal : {
				color: '#ccc',
				label : {
					show : true,
					position : 'center',
					formatter : function (params){
						return 100-params.value + '%'
					},
					textStyle: {
						baseline : 'bottom',
						color:"#577fb0",
					}
				},
				labelLine : {
					show : false
				}
			},
			emphasis: {
				color: 'rgba(0,0,0,0)'
			}
		};
		var radius = [40, 55];
		option = {
			tooltip: {
				trigger: 'item',
				formatter: "{a} <br/>{b}: {c} ({d}%)"
			},
			legend: {
				x : 'left',
				y : '20px',
				orient:"vertical",
				data:[
					'正常率',"异常率"
				],
				textStyle:{
					fontSize:8,
				},
			},
			title : {
				text: '节点正常率',
				subtext: 'Point normal rate',
				textStyle:{
					fontSize:10,
					//fontWeight:"normal",
				},
				x: 'center'
			},
			toolbox: {
				show : true,
				feature : {
					// dataView : {show: true, readOnly: false},//文本显示
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			series : [
				{
					name:'饼图',
					type : 'pie',
					// center : ['10%', '30%'],
					radius : radius,
					x: '0%', // for funnel
					itemStyle : labelFromatter,
					data : [
						{name:'异常率', value:46, itemStyle : labelBottom},
						{name:'正常率', value:54,itemStyle : labelTop}

					]
				}
			]
		};
		myChart.setOption(option);
		//柱图
		var myChart = echarts.init(document.getElementById('xfdytiaotu'));
		option = {
			title : {
				text: '异常分类统计',
				subtext: 'Abnormal classification statistics',
				textStyle:{
					fontSize:10,
				},
			},
			grid: {
				left: '0%',
				containLabel: true,
				bottom:"40",
			},
			tooltip : {
				trigger: 'axis',
				axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend: {
				show:true,
				data:['异常数'],
				x:"center",
				y:"190",
			},
			toolbox: {
				show : true,
				feature : {
					// dataView : {show: true, readOnly: false},
					magicType : {show: true, type: ['line', 'bar']},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			xAxis : [
				{
					type : 'value'
				}
			],
			yAxis : [
				{

					type : 'category',
					data : ['电流异常','温度异常','主机故障','节点故障']
				}
			],
			series : [
				{
					name:'异常数',
					type:'bar',
					itemStyle:{
						normal:{
							color:"#61a0a8",
						},
					},
					data:[2.0, 4.9, 7.0, 23.2],
				},

			]
		};
		myChart.setOption(option);
		//zz折线图
		myChart.setOption(option);
		var myChart = echarts.init(document.getElementById('xfdyxiantu'));
		option={
			title: {
				text: '异常统计',
				subtext: 'Exception statistics|For year',
				textStyle:{
					fontSize:14,
				},
				x:"30"
			},
			grid: {
				left: '20',
				containLabel: true,
				bottom:"10",
				right:"20"
			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				show:true,
				data:['异常总数']
			},
			toolbox: {
				show: true,
				feature: {

					//  dataView: {readOnly: false},
					magicType: {type: ['line', 'bar']},
					restore: {},
					saveAsImage: {}
				}
			},
			xAxis:  {
				type: 'category',
				boundaryGap: false,
				data: ['Jan. ','Feb.',' Mar.',' Apr.',' May.','Jun.',' Jul.',' Aug.',' Sept.',' Oct.',' Nov.',' Dec.']
			},
			yAxis: {
				type: 'value',
				axisLabel: {
					formatter: '{value}'
				}
			},
			series: [
				{
					name:'异常总数',
					type:'line',
					data:[5, 11, 15, 13, 12, 13, 10,4,3,6,11,7],
					markPoint: {
						data: [
							{type: 'max', name: '最大值'},

						]
					},
				},

			]

		}
		myChart.setOption(option);
		console.log(6);

	//	console.log(88888);
	}break;

}
}
function addJsonToTable(jsonData,tableId){

	/*        var tableHeader=jsonData["tableItem"].tableHeader;
	 var tableContent=jsonData["tableItem"].tableContent;*/
    var tableHeader=jsonData.tableItem.tableHeader;
    var tableContent=jsonData.tableItem.tableContent;
    var colLength=tableHeader.length;
    var rowlength=tableContent[1].length;
	/*console.log(tableHeader);
	console.log(tableHeader.length);*/
    $("#"+tableId+" tr").remove();  //移除所有的tr，清空表格
    var Tbody=$("#"+tableId+" tbody");
	var Thead=$("#"+tableId+" thead");
    var header="<tr>";//添加表格标题
    for(var trTxt in tableHeader){
        header+='<th>'+tableHeader[trTxt]+'</th>';//有多少个标题就加多少个，JS遍历属性的方法
    }
    header+="</tr>";
    Thead.append(header);

    var tableContentLength=0;//计算表格行数
    for(var numRow in tableContent){
        tableContentLength++;
    }

    var pageNum=Math.ceil(tableContentLength/11);//表格页数

    if(pageNum==0){
        pageNum=1;
    }
    var pageContent=new Array();//数组可存入每页表格内容
    var array=0;
    for(var numRow in tableContent){                  //存入表格数据
        var row="<tr class='forPageChange'>";
        var rowContent=tableContent[numRow];//这是一行数据
        for(var i=0;i<rowlength;i++){       //分别把一行数据写入对应的单元格里
            row+='<td>'+rowContent[i]+'</td>';
        }
        row+="</tr>";
        pageContent[array]+=row;
        if(numRow%11==0){array++;}
    }

    var blankNum=11*pageNum-tableContentLength;//在行数不位11整数倍时添加空白行
    if(blankNum!=0){
        for(var j=0;j<blankNum;j++){
            var rowBlank="<tr class='forPageChange'>";
            for(var k=0;k<rowlength;k++){
                rowBlank+='<td>　</td>';
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

    $('#pagePreview').click(function(){           //上一页
        if(currentPage==0){return}
        currentPage--;
        $('.forPageChange').remove();
        $('#pageChange').before(pageContent[currentPage]);
        $('#pageCurrent').html(currentPage+1);
    });

    $('#pageNext').click(function(){             //下一页
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
        $('#pageChange').before("<tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr><tr><td colspan='20'>　</td></tr>");
    }
}
