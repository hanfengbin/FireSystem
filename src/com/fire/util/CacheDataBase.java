package com.fire.util;

import com.fire.dao.InitDao;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class CacheDataBase {

	/**
	 * @param args
	 */
	public static Map<String, JSONObject> buildingData = new HashMap<String, JSONObject>();

	public static Map<String, ArrayList<String>> deviceVaulesPz = new HashMap<String, ArrayList<String>>();
	public static Map<String, ArrayList<String>> deviceParam = new HashMap<String, ArrayList<String>>();

	public static Map<String, JSONObject> warningParam = new ConcurrentHashMap<String, JSONObject>(); // 实时缓存预警信息，编号为异常编码+设备编号,是json嵌套json
	public static Map<String, JSONObject> troubleParam = new ConcurrentHashMap<String, JSONObject>();
	public static JSONObject basicConfig = new JSONObject(); // 服务器的Ip和端口号等信息
	public static Map<String, String> systemMap = new HashMap<String, String>();
	public static Map<String, List<String>> subSysteDeiceData = new HashMap<String, List<String>>();
	public static Map<String, JSONObject> subSystemNodeData = new ConcurrentHashMap<String, JSONObject>(); // 节点信息
	public static Map<String, JSONObject> subSystemHostData = new ConcurrentHashMap<String, JSONObject>(); // 主机信息
	public static Map<String, JSONObject> hostFaultData = new ConcurrentHashMap<String, JSONObject>(); // 主机的异常信息
	public static Map<String, ConcurrentHashMap<String, JSONObject>> currentMainValues = new ConcurrentHashMap<String, ConcurrentHashMap<String, JSONObject>>(); // 电气火灾节点的实时数据信息，key为楼栋编号，json的key为设备编号
	public static Map<String, ConcurrentHashMap<String, JSONObject>> powerMainValues = new ConcurrentHashMap<String, ConcurrentHashMap<String, JSONObject>>(); // 消防电源节点的实时数据信息，key为楼栋编号，json的key为设备编号
	public static Map<String, ConcurrentHashMap<String, JSONObject>> fireDoorMainValues = new ConcurrentHashMap<String, ConcurrentHashMap<String, JSONObject>>(); // 防火门节点的实时数据信息，key为楼栋编号，json的key为设备编号
	public static Map<String, ConcurrentHashMap<String, JSONObject>> gasMainValues = new ConcurrentHashMap<String, ConcurrentHashMap<String, JSONObject>>(); // 消防气体节点的实时数据信息，key为楼栋编号，json的key为设备编号

	public static ConcurrentMap<String, Integer> subSystemNodeState = new ConcurrentHashMap<String, Integer>();// 节点的状态，正常或者异常，每1min更新一次
	public static ConcurrentMap<String, Integer> subSystemHostState = new ConcurrentHashMap<String, Integer>();// 主机的状态，正常或者异常，每1min更新一次

	public static ExecutorService service = Executors.newFixedThreadPool(20);

	public static Log log = LogFactory.getLog(CacheDataBase.class);

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection con = DbConn.getConnection();
		//基础配置信息的导入
		baseDataLoad();
		//
		initializeFaultCurrent(con);
		loadSubSystemDevice();
		loadSubSystemHost(con);
		System.out.println(warningParam);
		System.out.println(hostFaultData);
		System.out.println(subSystemHostData);
		InitDao initDao = new InitDao();
		//把数据库中楼栋的信息放到内存中。
		buildingData = initDao.loadBuilding(con);
		DbConn.close(con);
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleWithFixedDelay(new UpdateNodeOrHostState(), 10, 60, TimeUnit.SECONDS);
	}

	public static void clearCache() {

	}
	/*
	*
	* */
	public static void baseDataLoad() {
		systemMap.put("water", "1");
		basicConfig.put("host", "172.20.33.126");
		basicConfig.put("port", 13344);
		basicConfig.put("alarmport", 15566);
		basicConfig.put("currentport", 13355);
		basicConfig.put("powerport", 13366);
		basicConfig.put("doorport", 13377);
		basicConfig.put("gasport", 13388);

	}

	public static void loadSubSystemHost(Connection con) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer buffer = new StringBuffer("select * from subsystem_host");
		try {
			preparedStatement = con.prepareStatement(buffer.toString());
			resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int column = resultSetMetaData.getColumnCount();
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				String hostId = resultSet.getString("hostID");
				for (int i = 1; i <= column; i++) {
					jsonObject.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
				}
				subSystemHostData.put(hostId, jsonObject);
				subSystemHostState.put(hostId, 0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.close(preparedStatement);
			DbConn.close(resultSet);
		}

	}

	public static void loadSubSystemDevice() {
		Connection con = DbConn.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer buffer = new StringBuffer("select * from subsystem_node");
		try {
			preparedStatement = con.prepareStatement(buffer.toString());
			resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int column = resultSetMetaData.getColumnCount();
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				String nodeId = resultSet.getString("nodeID");
				for (int i = 1; i <= column; i++) {
					jsonObject.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
				}
				subSystemNodeData.put(nodeId, jsonObject);
				subSystemNodeState.put(nodeId, 0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
			DbConn.close(resultSet);
		}
	}

	public static void initializeFaultCurrent(Connection con) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			StringBuffer stringBuffer = new StringBuffer(
					"SELECT subsystem_node.buildingID,fire_building.buildingName,fault_current.faultCode,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime FROM fault_current INNER JOIN subsystem_node ON fault_current.deviceID = subsystem_node.nodeID INNER JOIN fire_building ON subsystem_node.buildingID = fire_building.buildingID WHERE mvfaultDealState<>'已处置' UNION SELECT subsystem_host.buildingID,fire_building.buildingName,fault_current.faultCode,fault_current.deviceID,fault_current.faultCategory,fault_current.faultType,fault_current.faultTime,fault_current.faultLocation,fault_current.mnDeviceksczTime,fault_current.mvfaultDealState,fault_current.mvfaultDeviceDealPerson,fault_current.mvFaultCtime FROM fault_current INNER JOIN subsystem_host ON fault_current.deviceID = subsystem_host.hostID INNER JOIN fire_building ON subsystem_host.buildingID = fire_building.buildingID  WHERE mvfaultDealState<>'已处置' ");
			preparedStatement = con.prepareStatement(stringBuffer.toString());
			resultSet = preparedStatement.executeQuery();
			String buildId = "";
			JSONObject warningjsonObject = null;
			JSONObject troublejsonObject = null;
			JSONObject alarmjsonObject = null;
			JSONObject hostJsonObject = null;
			while (resultSet.next()) {
				if (!buildId.equals(resultSet.getString("buildingID"))) {
					buildId = resultSet.getString("buildingID");
					if (warningjsonObject != null) {
						warningParam.put(buildId, warningjsonObject);
					}
					if (troublejsonObject != null) {
						troubleParam.put(buildId, troublejsonObject);
					}
					if (hostJsonObject != null) {
						hostFaultData.put(buildId, hostJsonObject);
					}
					warningjsonObject = new JSONObject();
					troublejsonObject = new JSONObject();
					alarmjsonObject = new JSONObject();
					hostJsonObject = new JSONObject();
				}
				JSONObject current = new JSONObject();
				current.put("faultID", resultSet.getString("faultCode"));
				current.put("deviceID", resultSet.getString("deviceID"));
				current.put("faultCategory", resultSet.getString("faultCategory"));
				current.put("faultType", resultSet.getString("faultType"));
				current.put("faultTime", resultSet.getString("faultTime"));
				current.put("faultLocation", resultSet.getString("faultLocation"));
				if (resultSet.getString("faultCategory").equals("预警")) {
					warningjsonObject.put(resultSet.getString("faultCode"), current);
				} else if (resultSet.getString("faultCategory").equals("故障")) {
					troublejsonObject.put(resultSet.getString("faultCode"), current);
				} else if (resultSet.getString("faultCategory").equals("报警")) {
					alarmjsonObject.put(resultSet.getString("faultCode"), current);
				} else if (resultSet.getString("faultCategory").equals("主机异常")) {
					hostJsonObject.put(resultSet.getString("faultCode"), current);
				}
			}
			if (!buildId.equals("")) {
				warningParam.put(buildId, warningjsonObject);
				troubleParam.put(buildId, troublejsonObject);
				hostFaultData.put(buildId, hostJsonObject);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DbConn.close(preparedStatement);
			DbConn.close(resultSet);
		}
	}
}
