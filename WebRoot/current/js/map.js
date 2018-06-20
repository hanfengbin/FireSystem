$(document)
		.ready(
				function() {
					$.ajax({
						url: "servlet/DispatchServlet",
		                data:{'controller':'BuildingController','enum':'8'},
						type : "post",
						dataType : "json",
						success : function(jsondata) {
							console.log(jsondata);
							addPoi(jsondata);
						},
						error : function() {
							alert("加载poi异常");
						}
					});

					var buildingList = [];// 地图上的楼宇数组
					var poiMarker = [];// 地图上的poi数组
					try {
						var map = new BMap.Map("allmap");
					} catch (exception) {
						$("#allmap")
								.html(
										'<h1 style="text-align:center;padding-top:6em">网络链接超时，百度地图加载失败！</h1>');
						$("#allmap").css('background-color', 'white');
					}
					var point = new BMap.Point(106.476757, 29.57158);
					var marker = new BMap.Marker(point);
					// var infoWindow = new BMap.InfoWindow(sContent); //
					// 创建信息窗口对象
					map.centerAndZoom(point, 16);
					map.enableScrollWheelZoom();
					var pointArray = new Array();
					function addPOIEventHandler(marker, label) {
						marker.addEventListener("mouseover", function() {
							label.setStyle({ // 给label设置样式，任意的CSS都是可以的
								display : "block"
							});
						});
						marker.addEventListener("mouseout", function() {
							label.setStyle({ // 给label设置样式，任意的CSS都是可以的
								display : "none"
							});
						});
					}

					function addPoi(jsonData) {
						var points = []; // 添加海量点数据
						var data=jsonData.tableContent;
						var length = data.length;
						// alert(data.length);
						for ( var i = 0; i < length; i++) {
							var poi = new BMap.Point(data[i].buildingLon, data[i].buildingLat);
							points.push(poi);
							buildingList.push(data[i].buildingID);
							// 处理信息窗口开始
							var title = data[i].buildingName + '(' + data[i].unitName
									+ ')';
							var address = data[i].unitLocation;
							var telePhone = data[i].buildingTel;
							var marker = new BMap.Marker(poi);
							marker.id = data[i].buildingID;
							map.addOverlay(marker); // 在地图中添加marker
							var label = new BMap.Label(title, {
								"offset" : new BMap.Size(20, -20)
							});
							label.setStyle({
								display : "none", // 给label设置样式，任意的CSS都是可以的
								color : "red",
								fontSize : "12px",
								textAlign : "center",
								height : "20px",
								lineHeight : "12px",
								fontFamily : "微软雅黑",
								whiteSpace : "nowrap"
							});
							marker.setLabel(label);
							// addClickHandler(sContent,marker);
							// alert(title+address+telePhone);
							addClickHandler(title, address, telePhone, marker,
									label);
						}
						map.setViewport(points);
					}
					;
					function addClickHandler(title, address, telePhone, marker,
							label) {
						marker
								.addEventListener(
										"click",
										function(e) {
											$
													.ajax({
														url: "servlet/DispatchServlet",
										                data:{'controller':'StatusController','system':'3','buildId':marker.id},
														type : "post",
														dataType : "json",
														success : function(data) {
															var allStr = '';
															var mainSumStr = ' <div class=\"badge\"'
																	+ ' style=\"cursor:pointer;background-color:#FFC107;\" id=\"'
																	+ 'mainSum'
																	+ '\">'
																	+ data.hostAll
																	+ '</div>';
															var mainString = '<span style=\"line-height:18px letter-spacing:12px\">'
																	+ '监测主机:'
																	+ '</span>'
																	+ mainSumStr;
															var mainNormal = ' <div class=\"badge\"'
																	+ ' style=\"cursor:pointer;background-color:#00FF00;\" id=\"'
																	+ 'mainNormal'
																	+ '\">'
																	+ data.hostNormal
																	+ '</div>';
															var mainNormalString = '<span style=\"line-height:18px letter-spacing:12px\">'
																	+ '正常主机:'
																	+ '</span>'
																	+ mainNormal;
															var mainFault = ' <div class=\"badge\"'
																	+ ' style=\"cursor:pointer;background-color:#E62C0C;\" id=\"'
																	+ 'mainFault'
																	+ '\">'
																	+ data.hostFault
																	+ '</div>';
															var mainFaultString = '<span style=\"line-height:18px letter-spacing:12px\">'
																	+ '异常主机:'
																	+ '</span>'
																	+ mainFault;

															var nodeSumStr = ' <div class=\"badge\"'
																	+ ' style=\"cursor:pointer;background-color:#FFC107;\" id=\"'
																	+ 'mainSum'
																	+ '\">'
																	+ data.nodeAll
																	+ '</div>';
															var nodeString = '<span style=\"line-height:18px letter-spacing:12px\">'
																	+ '监测节点:'
																	+ '</span>'
																	+ nodeSumStr;
															var nodeNormal = ' <div class=\"badge\"'
																	+ ' style=\"cursor:pointer;background-color:#00FF00;\" id=\"'
																	+ 'mainNormal'
																	+ '\">'
																	+ data.nodeNormal
																	+ '</div>';
															var nodeNormalString = '<span style=\"line-height:18px letter-spacing:12px\">'
																	+ '正常节点:'
																	+ '</span>'
																	+ nodeNormal;
															var nodeFault = ' <div class=\"badge\"'
																	+ ' style=\"cursor:pointer;background-color:#E62C0C;\" id=\"'
																	+ 'mainFault'
																	+ '\">'
																	+ data.nodeFault
																	+ '</div>';
															var nodeFaultString = '<span style=\"line-height:18px letter-spacing:12px\">'
																	+ '异常节点:'
																	+ '</span>'
																	+ nodeFault;
															allStr = allStr
																	+ mainString
																	+ '&nbsp;'
																	+ nodeString
																	+ '</br>'
																	+ mainNormalString
																	+ '&nbsp;'
																	+ nodeNormalString
																	+ '</br>'
																	+ mainFaultString
																	+ '&nbsp;'
																	+ nodeFaultString
																	+ '</br>';
															var photoStr = 'current/images/headnew.png';
															var pidPhotoStr = 'current/images/rightnew.png';
															var photos = data.photos;
															var pidPhotos = data.pidPhotos;
															if (photos != null
																	&& photos.length > 0) {
																photoStr = '../'
																		+ photos[0];
															}
															if (pidPhotos != null
																	&& pidPhotos.length > 0) {
																pidPhotoStr = '../'
																		+ pidPhotos[0];
															}
															var content = '<div><img width="323" height="101" border="0" src="'
																	+ photoStr
																	+ '"></div>'
																	+ '<div style="margin:0;line-height:20px;padding:2px;">'
																	+ '<img src="'
																	+ pidPhotoStr
																	+ '" alt="" style="float:right;zoom:1;overflow:hidden;width:100px;height:120px;margin-left:3px;"/>'
																	/*
																	 * '<a
																	 * onclick="openPanorama('+marker.getPosition().lng+','+marker.getPosition().lat+');">>进入全景&gt;&gt;</a><br/>'
																	 */
																	+ '<span style=\"line-height:18px letter-spacing:12px\">'
																	+ '地址：'
																	+ address
																	+ '<br/>'
																	+ '电话：'
																	+ telePhone
																	+ '<br/>'
																	+ '</span>'
																	/* + '节点：' */
																	+ allStr
																	+ '</div>';
															// alert(content);
															openInfo(title,
																	content, e);
														},
														error : function() {
															alert("error");
														}
													});

										});
						marker.addEventListener("mouseover", function() {
							// var icon = new
							// BMap.Icon("../plugins/baidu/images/building_click.png",
							// new BMap.Size(30, 40));
							// marker.setIcon(icon);
							label.setStyle({ // 给label设置样式，任意的CSS都是可以的
								display : "block"
							});
						});
						marker.addEventListener("mouseout", function() {
							// var icon = new
							// BMap.Icon("../plugins/baidu/images/building.png",
							// new BMap.Size(30, 40));
							// marker.setIcon(icon);
							label.setStyle({ // 给label设置样式，任意的CSS都是可以的
								display : "none"
							});
						});
					}
					;

					function openInfo(title, content, e) {

						var point = new BMap.Point(e.target.getPosition().lng,
								e.target.getPosition().lat);
						var opts = {
							width : 328, // 信息窗口宽度
							height : 248, // 信息窗口高度
							title : '<b style=\"font-size:20px;\">' + title
									+ '</b>', // 信息窗口标题
							enableMessage : true
						// 设置允许信息窗发送短息
						};
						// 创建检索信息窗口对象
						var infoWindow = new BMap.InfoWindow(content, opts);
						// var infoWindow = new BMap.InfoWindow(content,opts);
						// // 创建信息窗口对象
						map.openInfoWindow(infoWindow, point); // 开启信息窗口
					}
					;

					var top_left_control = new BMap.ScaleControl({
						anchor : BMAP_ANCHOR_TOP_LEFT
					});// 左上角，添加比例尺
					var top_left_navigation = new BMap.NavigationControl(); // 左上角，添加默认缩放平移控件
					var mapType1 = new BMap.MapTypeControl({
						mapTypes : [ BMAP_NORMAL_MAP, BMAP_HYBRID_MAP ]
					});
					var mapType2 = new BMap.MapTypeControl({
						anchor : BMAP_ANCHOR_BOTTOM_LEFT
					});
					var overView = new BMap.OverviewMapControl();
					var overViewOpen = new BMap.OverviewMapControl({
						isOpen : true,
						anchor : BMAP_ANCHOR_BOTTOM_RIGHT
					});
					map.addControl(mapType1); // 2D图，卫星图
					map.addControl(mapType2); // 左上角，默认地图控件
					map.setCurrentCity("重庆"); // 由于有3D图，需要设置城市哦
					map.addControl(overView); // 添加默认缩略地图控件
					map.addControl(overViewOpen); // 右下角，打开
					map.addControl(top_left_control);
					map.addControl(top_left_navigation);
				});