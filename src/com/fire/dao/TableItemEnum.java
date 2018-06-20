package com.fire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.fire.util.DbConn;
import com.fire.util.SubSystemCode;

public enum TableItemEnum {
	// 一个enmu对应一个Sql语句
	staffList("select StaffID,StaffName,StaffPhoneNumber,StaffWorkArea,StaffPermissions from staff_info order by StaffID Asc", "用户帐号,用户姓名,联系电话,责任区域,用户权限,操作"),
    //人员管理sql语句。
	hostList(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where substr(hostID,1,1)=3 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"),
    //
	hostListFault(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where substr(hostID,1,1)=3 and isNormal=1 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"),

	hostListNormal(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where substr(hostID,1,1)=3 and isNormal=0 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"),

	nodeList("select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where substr(nodeID,1,1)=3 order by nodeID Asc", "节点编号,总线号,主机编号,节点位置,节点状态"),

	nodeListFault("select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where isNormal=1 and substr(nodeID,1,1)=3 order by nodeID Asc",
			"节点编号,总线号,主机编号,节点位置,节点状态"),

	nodeListNormal("select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where isNormal=0 and substr(nodeID,1,1)=3 order by nodeID Asc",
			"节点编号,总线号,主机编号,节点位置,节点状态"),

	faultScreenByAppend(
			"SELECT fault_current.deviceID,fault_current.faultLocation,fault_current.faultCategory,fault_current.faultTime,fault_current.mvfaultDealState,fault_current.mnDeviceksczTime,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultDealFeedback,fault_current.faultType FROM fault_current INNER JOIN ((SELECT nodeID AS 'deviceID',buildingID FROM subsystem_node) UNION (SELECT hostID,buildingID FROM subsystem_host)) AS device ON device.deviceID=fault_current.deviceID where 1=1",
			"设备编号,位置,异常种类,异常时间,异常状态,处置时间,处置人员,处置完成时间,处置反馈,详情"),

	buildingList(
			"SELECT b.buildingID,b.unitID,b.buildingName,b.buildingLocation,b.buildingLat,b.buildingLon,b.buildingPerson,b.buildingTel,u.unitName,u.unitLocation FROM fire_building b INNER JOIN fire_unit u ON b.unitID = u.unitID",
			""),

	treeList(
			"SELECT DISTINCT b.buildingID,b.buildingName,ho.hostID,ho.hostLocation,ho.canNo FROM fire_building b LEFT JOIN((SELECT st.buildingID,st.hostID,st.hostLocation,st.canNo FROM ((SELECT h.hostID,h.buildingID,h.hostLocation,n.canNO FROM subsystem_host h LEFT JOIN subsystem_node n ON h.hostID=n.hostID) AS st)) as ho) ON ho.buildingID=b.buildingID WHERE (substr(hostId,1,1)=3 OR hostId IS NULL)",
			""),

	faultScreenByAppend3(
			"SELECT fault_current.deviceID,fault_current.faultLocation,fault_current.faultCategory,fault_current.faultTime,fault_current.mvfaultDealState,fault_current.mnDeviceksczTime,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultDealFeedback,fault_current.faultType FROM fault_current INNER JOIN ((SELECT nodeID as 'deviceID',buildingID FROM subsystem_node) UNION (SELECT hostID,buildingID FROM subsystem_host)) AS device ON device.deviceID=fault_current.deviceID where substr(fault_current.deviceID,1,1)=3 order by fault_current.faultTime desc",
			"设备编号,位置,异常种类,异常时间,异常状态,处置时间,处置人员,处置完成时间,处置反馈,故障类型"),

	treeList5(
			"SELECT DISTINCT b.buildingID,b.buildingName,ho.hostID,ho.hostLocation,ho.canNo FROM fire_building b LEFT JOIN((SELECT st.buildingID,st.hostID,st.hostLocation,st.canNo FROM ((SELECT h.hostID,h.buildingID,h.hostLocation,n.canNO FROM subsystem_host h LEFT JOIN subsystem_node n ON h.hostID=n.hostID) AS st)) as ho) ON ho.buildingID=b.buildingID WHERE (substr(hostId,1,1)=5 OR hostId IS NULL)",
			""),

	hostList5(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where substr(hostID,1,1)=5 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"),

	hostListFault5(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where substr(hostID,1,1)=5 and isNormal=1 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"),

	hostListNormal5(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where isNormal=0 and substr(hostID,1,1)=5 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"), nodeList5(
			"select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where substr(nodeID,1,1)=5 order by nodeID Asc", "节点编号,总线号,主机编号,节点位置,节点状态"),

	nodeListFault5("select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where isNormal=1 and substr(nodeID,1,1)=5 order by nodeID Asc",
			"节点编号,总线号,主机编号,节点位置,节点状态"),

	nodeListNormal5("select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where isNormal=0 and substr(nodeID,1,1)=5 order by nodeID Asc",
			"节点编号,总线号,主机编号,节点位置,节点状态"),

	faultScreenByAppend5(
			"SELECT fault_current.deviceID,fault_current.faultLocation,fault_current.faultCategory,fault_current.faultTime,fault_current.mvfaultDealState,fault_current.mnDeviceksczTime,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultDealFeedback,fault_current.faultType FROM fault_current INNER JOIN ((SELECT nodeID as 'deviceID',buildingID FROM subsystem_node) UNION (SELECT hostID,buildingID FROM subsystem_host)) AS device ON device.deviceID=fault_current.deviceID where substr(fault_current.deviceID,1,1)=5 order by fault_current.faultTime desc",
			"设备编号,位置,异常种类,异常时间,异常状态,处置时间,处置人员,处置完成时间,处置反馈,故障类型"),

	treeList4(
			"SELECT DISTINCT b.buildingID,b.buildingName,ho.hostID,ho.hostLocation,ho.canNo FROM fire_building b LEFT JOIN((SELECT st.buildingID,st.hostID,st.hostLocation,st.canNo FROM ((SELECT h.hostID,h.buildingID,h.hostLocation,n.canNO FROM subsystem_host h LEFT JOIN subsystem_node n ON h.hostID=n.hostID) AS st)) as ho) ON ho.buildingID=b.buildingID WHERE (substr(hostId,1,1)=4 OR hostId IS NULL)",
			""),

	hostList4(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where substr(hostID,1,1)=4 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"),

	hostListFault4(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where isNormal=1 and substr(hostID,1,1)=4 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"),

	hostListNormal4(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where isNormal=0 and substr(hostID,1,1)=4 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"),

	nodeList4("select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where substr(nodeID,1,1)=4 order by nodeID Asc", "节点编号,总线号,主机编号,节点位置,节点状态"),

	nodeListFault4("select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where isNormal=1 and substr(nodeID,1,1)=4 order by nodeID Asc",
			"节点编号,总线号,主机编号,节点位置,节点状态"),

	nodeListNormal4("select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where isNormal=0 and substr(nodeID,1,1)=4 order by nodeID Asc",
			"节点编号,总线号,主机编号,节点位置,节点状态"),

	faultScreenByAppend4(
			"SELECT fault_current.deviceID,fault_current.faultLocation,fault_current.faultCategory,fault_current.faultTime,fault_current.mvfaultDealState,fault_current.mnDeviceksczTime,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultDealFeedback,fault_current.faultType FROM fault_current INNER JOIN ((SELECT nodeID as 'deviceID',buildingID FROM subsystem_node) UNION (SELECT hostID,buildingID FROM subsystem_host)) AS device ON device.deviceID=fault_current.deviceID where substr(fault_current.deviceID,1,1)=4 order by fault_current.faultTime desc",
			"设备编号,位置,异常种类,异常时间,异常状态,处置时间,处置人员,处置完成时间,处置反馈,故障类型"), treeList6(
			"SELECT DISTINCT b.buildingID,b.buildingName,ho.hostID,ho.hostLocation,ho.canNo,ho.nodeType FROM fire_building b LEFT JOIN((SELECT st.buildingID,st.hostID,st.hostLocation,st.canNo,st.nodeType FROM ((SELECT h.hostID,h.buildingID,h.hostLocation,n.canNO,n.nodeType FROM subsystem_host h LEFT JOIN subsystem_node n ON h.hostID=n.hostID) AS st)) as ho) ON ho.buildingID=b.buildingID WHERE (substr(hostId,1,1)=6 OR hostId IS NULL)",
			""),

	hostList6(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where substr(hostID,1,1)=6 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"),

	hostListFault6(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where isNormal=1 and substr(hostID,1,1)=6 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"),

	hostListNormal6(
			"select hostID,buildingName,hostLocation,isNormal from subsystem_host INNER JOIN fire_building ON subsystem_host.buildingID=fire_building.buildingID where isNormal=0 and substr(hostID,1,1)=6 order by hostID Asc",
			"主机编号,所属楼栋,主机位置,主机状态"),

	nodeList6("select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where substr(nodeID,1,1)=6 order by nodeID Asc", "节点编号,总线号,主机编号,节点位置,节点状态"),

	nodeListFault6("select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where isNormal=1 and substr(nodeID,1,1)=6 order by nodeID Asc",
			"节点编号,总线号,主机编号,节点位置,节点状态"),

	nodeListNormal6("select nodeId,canNo,hostId,nodeLocation,isNormal from subSystem_node where isNormal=0 and substr(nodeID,1,1)=6 order by nodeID Asc",
			"节点编号,总线号,主机编号,节点位置,节点状态"),

	faultScreenByAppend6(
			"SELECT fault_current.deviceID,fault_current.faultLocation,fault_current.faultCategory,fault_current.faultTime,fault_current.mvfaultDealState,fault_current.mnDeviceksczTime,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultDealFeedback,fault_current.faultType FROM fault_current INNER JOIN ((SELECT nodeID as 'deviceID',buildingID FROM subsystem_node) UNION (SELECT hostID,buildingID FROM subsystem_host)) AS device ON device.deviceID=fault_current.deviceID where substr(fault_current.deviceID,1,1)=6 order by fault_current.faultTime desc",
			"设备编号,位置,异常种类,异常时间,异常状态,处置时间,处置人员,处置完成时间,处置反馈,故障类型"),

	indexStatusData(
			"SELECT (SELECT COUNT(*) FROM fire_building)AS 'buildSum',(SELECT COUNT(*) FROM subsystem_host) AS 'hostSum',(SELECT COUNT(*) FROM subsystem_node) AS 'nodeSum',(select count(*) FROM subsystem_node WHERE isNormal=1) as 'nodeFault',(select count(*) from (select deviceID from fault_current where faultCategory='预警' AND mvfaultDealState<>'已处置' group  by deviceID) as node_sum) as 'nodeFaultSum',(select count(*) from (select deviceID from fault_current where faultCategory='主机异常' AND mvfaultDealState<>'已处置' group  by deviceID) as node_sum) as 'hosteFault'",
			""), // 监测楼栋数,监测主机数,监测节点数,异常节点数,节点异常数,主机异常数
	indexStatusData3(
			"SELECT (SELECT COUNT(*) FROM (SELECT buildingID FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='3' GROUP BY buildingID) As buildCount) AS 'buildSum',(SELECT COUNT(*) FROM subsystem_host WHERE SUBSTRING(hostID,1,1)='3' ) AS 'hostSum',(SELECT COUNT(*) FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='3' ) AS 'nodeSum',(select count(*) FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='3' AND isNormal=1) as 'nodeFault',(select count(*) from (select deviceID from fault_current  WHERE SUBSTRING(deviceID,1,1)='3' AND faultCategory='预警' AND mvfaultDealState<>'已处置' group  by deviceID) as node_sum) as 'nodeFaultSum',(select count(*) from (select deviceID from fault_current  WHERE SUBSTRING(deviceID,1,1)='3' AND faultCategory='主机异常' AND mvfaultDealState<>'已处置' group  by deviceID) as node_sum) as 'hosteFault'",
			""), 
	indexStatusData4(
			"SELECT (SELECT COUNT(*) FROM (SELECT buildingID FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='4' GROUP BY buildingID) As buildCount) AS 'buildSum',(SELECT COUNT(*) FROM subsystem_host WHERE SUBSTRING(hostID,1,1)='4' ) AS 'hostSum',(SELECT COUNT(*) FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='4' ) AS 'nodeSum',(select count(*) FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='4' AND isNormal=1) as 'nodeFault',(select count(*) from (select deviceID from fault_current  WHERE SUBSTRING(deviceID,1,1)='4' AND faultCategory='预警' AND mvfaultDealState<>'已处置' group  by deviceID) as node_sum) as 'nodeFaultSum',(select count(*) from (select deviceID from fault_current  WHERE SUBSTRING(deviceID,1,1)='4' AND faultCategory='主机异常' AND mvfaultDealState<>'已处置' group  by deviceID) as node_sum) as 'hosteFault'",
			""), 
	indexStatusData5(
			"SELECT (SELECT COUNT(*) FROM (SELECT buildingID FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='5' GROUP BY buildingID) As buildCount) AS 'buildSum',(SELECT COUNT(*) FROM subsystem_host WHERE SUBSTRING(hostID,1,1)='5' ) AS 'hostSum',(SELECT COUNT(*) FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='5' ) AS 'nodeSum',(select count(*) FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='5' AND isNormal=1) as 'nodeFault',(select count(*) from (select deviceID from fault_current  WHERE SUBSTRING(deviceID,1,1)='5' AND faultCategory='预警' AND mvfaultDealState<>'已处置' group  by deviceID) as node_sum) as 'nodeFaultSum',(select count(*) from (select deviceID from fault_current  WHERE SUBSTRING(deviceID,1,1)='5' AND faultCategory='主机异常' AND mvfaultDealState<>'已处置' group  by deviceID) as node_sum) as 'hosteFault'",
			""), 
	indexStatusData6(
			"SELECT (SELECT COUNT(*) FROM (SELECT buildingID FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='6' GROUP BY buildingID) As buildCount) AS 'buildSum',(SELECT COUNT(*) FROM subsystem_host WHERE SUBSTRING(hostID,1,1)='6' ) AS 'hostSum',(SELECT COUNT(*) FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='6' ) AS 'nodeSum',(select count(*) FROM subsystem_node WHERE SUBSTRING(nodeID,1,1)='6' AND isNormal=1) as 'nodeFault',(select count(*) from (select deviceID from fault_current  WHERE SUBSTRING(deviceID,1,1)='6' AND faultCategory='预警' AND mvfaultDealState<>'已处置' group  by deviceID) as node_sum) as 'nodeFaultSum',(select count(*) from (select deviceID from fault_current  WHERE SUBSTRING(deviceID,1,1)='6' AND faultCategory='主机异常' AND mvfaultDealState<>'已处置' group  by deviceID) as node_sum) as 'hosteFault'",
			""), 
	faultDetailedInformation(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState<>'已处置' UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState<>'已处置'",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码"),
	faultDetailedInformationUndo(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState='未处置' UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState='未处置'",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码"),
	faultDetailedInformationUndo3(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState='未处置' AND SUBSTRING(fault_current.deviceID,1,1)='3' UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState='未处置' AND SUBSTRING(fault_current.deviceID,1,1)='3'",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码"), 
	faultDetailedInformationUndo4(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState='未处置' AND SUBSTRING(fault_current.deviceID,1,1)='4' UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState='未处置' AND SUBSTRING(fault_current.deviceID,1,1)='4'",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码"), 
	faultDetailedInformationUndo5(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState='未处置' AND SUBSTRING(fault_current.deviceID,1,1)='5' UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState='未处置' AND SUBSTRING(fault_current.deviceID,1,1)='5'",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码"), 
	faultDetailedInformationUndo6(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState='未处置' AND SUBSTRING(fault_current.deviceID,1,1)='6' UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState='未处置' AND SUBSTRING(fault_current.deviceID,1,1)='6'",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码"), 
	faultDetailedInformation3(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState<>'已处置' AND SUBSTRING(fault_current.deviceID,1,1)='3' UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState<>'已处置' AND SUBSTRING(fault_current.deviceID,1,1)='3'",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码"), 
	faultDetailedInformation4(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState<>'已处置' AND SUBSTRING(fault_current.deviceID,1,1)='3' UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState<>'已处置' AND SUBSTRING(fault_current.deviceID,1,1)='4'",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码"),
	faultDetailedInformation5(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState<>'已处置' AND SUBSTRING(fault_current.deviceID,1,1)='3' UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState<>'已处置' AND SUBSTRING(fault_current.deviceID,1,1)='5'",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码"), 
	faultDetailedInformation6(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState<>'已处置' AND SUBSTRING(fault_current.deviceID,1,1)='3' UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState<>'已处置' AND SUBSTRING(fault_current.deviceID,1,1)='6'",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码"), 
	faultDetailInfoDoingByPerson(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState='处置中' AND mvfaultDeviceDealPerson=? UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState='处置中' AND mvfaultDeviceDealPerson=?",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码"), 
	faultDetailInfoDoByPerson(
			"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState='已处置' AND mvfaultDeviceDealPerson=? UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime,fault_current.faultID,fault_current.faultCode FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState='已处置' AND mvfaultDeviceDealPerson=?",
			"楼栋编号,楼栋名称,设备编号,异常种类,异常类型,异常时间,异常位置,处置时间,异常状态,处置人员,处置完成时间,异常编号,异常编码")
	// 结束标记
	;
	private String sqlStatement;// Sql语句，生成tableContent
	private String titles; // 表格各列标题，用‘,’分割，生成tableHeader,如"标题1,标题2,标题3"

	private TableItemEnum(String sqlStateString, String titles) {
		this.sqlStatement = sqlStateString;
		this.titles = titles;
	}

	private TableItemEnum(String sqlStateString) {
		this.sqlStatement = sqlStateString;
	}

	public String getSqlStatement() {
		return sqlStatement;
	}

	public String getTitles() {
		return titles;
	}

	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public JSONObject getJsonObject() {

		JSONObject jsonTableItem = new JSONObject();
		Connection conn = null;
		conn = DbConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			ArrayList<Object> arrayObj = new ArrayList<Object>();
			pstmt = conn.prepareStatement(sqlStatement);//*******************************************************************
			rs = pstmt.executeQuery();                  //******************************************************************
			JSONObject jsonResult = new JSONObject();
			JSONObject jsonTmp = new JSONObject();

			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount(); // 获得列的数量

			if (titles == null) {// 未设置titles时候,应数据库中的标题
				for (int i = 1; i <= columnCount; i++) {
					arrayObj.add(metaData.getColumnLabel(i));
				}
				jsonResult.put("tableHeader", arrayObj);
				arrayObj.clear();
			} else { // 设置titles，用split切片成数组直接存入jsonObj
				List<String> lS = Arrays.asList(titles.split(","));
				jsonResult.put("tableHeader", lS);
			}

			int row = 1;
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					arrayObj.add(rs.getString(i));// 每一行数据，形成数组
				}
				jsonTmp.put(row + "", arrayObj);// put到Json中，key为对于的行数，既row
				arrayObj.clear();
				row++;
			}
			jsonResult.put("tableContent", jsonTmp);   //将每一行的数据
			jsonTableItem.put("tableItem", jsonResult);// 封装json

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum中Sql运行错误");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("TableItemEnum中Sql执行代码块其他错误");
		} finally {
			DbConn.close(conn);
			DbConn.close(pstmt);
			DbConn.close(rs);
		}
		return jsonTableItem;
	}

	public JSONObject getCallJsonObject() {
		JSONObject jsonTableItem = new JSONObject();
		Connection conn = null;
		conn = DbConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			ArrayList<Object> arrayObj = new ArrayList<Object>();
			pstmt = conn.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			JSONObject jsonResult = new JSONObject();
			JSONObject jsonTmp = new JSONObject();

			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount(); // 获得列的数量

			if (titles == null) {// 未设置titles时候,应数据库中的标题
				for (int i = 1; i <= columnCount; i++) {
					arrayObj.add(metaData.getColumnLabel(i));
				}
				jsonResult.put("tableHeader", arrayObj);
				arrayObj.clear();
			} else { // 设置titles，用split切片成数组直接存入jsonObj
				List<String> lS = Arrays.asList(titles.split(","));
				jsonResult.put("tableHeader", lS);
			}

			int row = 1;
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					arrayObj.add(rs.getString(i));// 每一行数据，形成数组
				}
				jsonTmp.put(row + "", arrayObj);// put到Json中，key为对于的行数，既row
				arrayObj.clear();
				row++;
			}
			jsonResult.put("tableContent", jsonTmp);
			jsonTableItem.put("tableItem", jsonResult);// 封装json

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum中Sql运行错误");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("TableItemEnum中Sql执行代码块其他错误");
		} finally {
			DbConn.close(conn);
			DbConn.close(pstmt);
			DbConn.close(rs);
		}
		return jsonTableItem;

	}

	public JSONObject getJsonObject(String sqlStatement) {

		JSONObject jsonTableItem = new JSONObject();
		Connection conn = null;
		conn = DbConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			ArrayList<Object> arrayObj = new ArrayList<Object>();
			pstmt = conn.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				JSONObject jsonResult = new JSONObject();
				for (int i = 1; i <= columnCount; i++) {
					jsonResult.put(metaData.getColumnName(i), rs.getObject(i));
				}
				arrayObj.add(jsonResult);
			}
			jsonTableItem.put("tableContent", arrayObj);// 封装json

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum中Sql运行错误");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("TableItemEnum中Sql执行代码块其他错误");
		} finally {
			DbConn.close(conn);
			DbConn.close(pstmt);
			DbConn.close(rs);
		}
		return jsonTableItem;
	}

	public HSSFWorkbook getExcel() {
		HSSFWorkbook wkb = new HSSFWorkbook();
		JSONObject jsonItem = this.getJsonObject();
		JSONObject jsonObject = jsonItem.getJSONObject("tableItem");
		System.out.println(jsonObject);

		HSSFSheet sheet = wkb.createSheet("sheet1");
		try {
			JSONArray tableHeader = jsonObject.getJSONArray("tableHeader");
			int tableHeaderLength = tableHeader.size();
			HSSFRow row1 = sheet.createRow(0);
			for (int i = 0; i < tableHeaderLength; i++) {
				row1.createCell(i).setCellValue(tableHeader.getString(i));
			}

			JSONObject tableContent = jsonObject.getJSONObject("tableContent");
			Iterator<?> it = tableContent.keys();
			int rowNum = 1;
			while (it.hasNext()) {
				String key = (String) it.next();
				JSONArray jsonArray = tableContent.getJSONArray(key);
				int length = jsonArray.size();
				HSSFRow row = sheet.createRow(rowNum);
				for (int i = 0; i < length; i++) {
					try {
						row.createCell(i).setCellValue(jsonArray.getString(i));
					} catch (Exception e) {
						row.createCell(i).setCellValue("null");
					}
				}
				rowNum++;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return wkb;
	}

	public JSONObject getFaultJsonObject() {
		JSONObject jsonResult = new JSONObject();
		SubSystemCode systemFaultCode = new SubSystemCode();
		Connection conn = null;
		conn = DbConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			ArrayList<Object> arrayObj = new ArrayList<Object>();
			pstmt = conn.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			JSONArray jsonArray = new JSONArray();

			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount(); // 获得列的数量
			if (titles == null) {// 未设置titles时候,应数据库中的标题
				for (int i = 1; i <= columnCount; i++) {
					arrayObj.add(metaData.getColumnLabel(i));
				}
				jsonResult.put("tableHeader", arrayObj);
				arrayObj.clear();
			} else { // 设置titles，用split切片成数组直接存入jsonObj
				List<String> lS = Arrays.asList(titles.split(","));
				jsonResult.put("tableHeader", lS);
			}
			int row = 1;
			while (rs.next()) {
				String deviceID = rs.getString("deviceID");
				String system = deviceID.substring(0, 1);
				String faultCategory = rs.getString("faultCategory");// 1是主电
				String faultCode = rs.getString("faultType");
				String faultType = "";
				if (faultCategory.equals("主机异常")) {
					if (faultCode.equals("1")) {
						faultType = "主电故障";
					} else if (faultCode.equals("2")) {
						faultType = "备电故障";
					} else if (faultCode.equals("3")) {
						faultType = "主电、备电故障";
					}
				} else {
					if (system.equals("3")) {
						faultType = systemFaultCode.getCurrentFaultTypeString("current", Integer.valueOf(faultCode));
					} else if (system.equals("4")) {
						faultType = "防火门异常";
					} else if (system.equals("5")) {
						faultType = systemFaultCode.getPowerFaultTypeString("power", Integer.valueOf(faultCode));
					} else if (system.equals("6")) {
						String type = deviceID.substring(deviceID.length() - 1, deviceID.length());
						String gas = "";
						if (type.equals("a")) {
							gas = "gas1";
						} else if (type.equals("b")) {
							gas = "gas2";
						} else if (type.equals("c")) {
							gas = "gas3";
						}
						faultType = systemFaultCode.getGasFaultTypeString(gas, Integer.valueOf(faultCode));
					}
				}
				for (int i = 1; i <= columnCount; i++) {
					if (i == 5) {
						arrayObj.add(faultType);
						continue;
					}
					arrayObj.add(rs.getString(i));// 每一行数据，形成数组
				}
				jsonArray.add(arrayObj);// put到Json中，key为对于的行数，既row
				arrayObj.clear();
				row++;
			}
			jsonResult.put("tableContent", jsonArray);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum中Sql运行错误");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("TableItemEnum中Sql执行代码块其他错误");
		} finally {
			DbConn.close(conn);
			DbConn.close(pstmt);
			DbConn.close(rs);
		}
		return jsonResult;

	}

	public JSONObject getFaultJsonObject(String[] param) {
		JSONObject jsonResult = new JSONObject();
		SubSystemCode systemFaultCode = new SubSystemCode();
		Connection conn = null;
		conn = DbConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			ArrayList<Object> arrayObj = new ArrayList<Object>();
			pstmt = conn.prepareStatement(sqlStatement);
			for (int i = 1; i <= param.length; i++) {
				pstmt.setString(i, param[i - 1]);
			}
			rs = pstmt.executeQuery();
			JSONArray jsonArray = new JSONArray();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount(); // 获得列的数量
			if (titles == null) {// 未设置titles时候,应数据库中的标题
				for (int i = 1; i <= columnCount; i++) {
					arrayObj.add(metaData.getColumnLabel(i));
				}
				jsonResult.put("tableHeader", arrayObj);
				arrayObj.clear();
			} else { // 设置titles，用split切片成数组直接存入jsonObj
				List<String> lS = Arrays.asList(titles.split(","));
				jsonResult.put("tableHeader", lS);
			}
			int row = 1;
			while (rs.next()) {
				String deviceID = rs.getString("deviceID");
				String system = deviceID.substring(0, 1);
				String faultCategory = rs.getString("faultCategory");// 1是主电
				String faultCode = rs.getString("faultType");
				String faultType = "";
				if (faultCategory.equals("主机异常")) {
					if (faultCode.equals("1")) {
						faultType = "主电故障";
					} else if (faultCode.equals("2")) {
						faultType = "备电故障";
					} else if (faultCode.equals("3")) {
						faultType = "主电、备电故障";
					}
				} else {
					if (system.equals("3")) {
						faultType = systemFaultCode.getCurrentFaultTypeString("current", Integer.valueOf(faultCode));
					} else if (system.equals("4")) {
						faultType = "防火门异常";
					} else if (system.equals("5")) {
						faultType = systemFaultCode.getPowerFaultTypeString("power", Integer.valueOf(faultCode));
					} else if (system.equals("6")) {
						String type = deviceID.substring(deviceID.length() - 1, deviceID.length());
						String gas = "";
						if (type.equals("a")) {
							gas = "gas1";
						} else if (type.equals("b")) {
							gas = "gas2";
						} else if (type.equals("c")) {
							gas = "gas3";
						}
						faultType = systemFaultCode.getGasFaultTypeString(gas, Integer.valueOf(faultCode));
					}
				}
				for (int i = 1; i <= columnCount; i++) {
					if (i == 5) {
						arrayObj.add(faultType);
						continue;
					}
					arrayObj.add(rs.getString(i));// 每一行数据，形成数组
				}
				jsonArray.add(arrayObj);// put到Json中，key为对于的行数，既row
				arrayObj.clear();
				row++;
			}
			jsonResult.put("tableContent", jsonArray);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum中Sql运行错误");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("TableItemEnum中Sql执行代码块其他错误");
		} finally {
			DbConn.close(conn);
			DbConn.close(pstmt);
			DbConn.close(rs);
		}
		return jsonResult;

	}
}
