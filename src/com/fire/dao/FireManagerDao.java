package com.fire.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import net.sf.json.JSONObject;

import com.fire.util.CacheDataBase;
import com.fire.util.DbConn;

public class FireManagerDao {

	public boolean selectNodeById(String nodeID) throws Exception {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("select count(*) from subsystem_node where nodeID=?");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, nodeID);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			if (resultSet.getLong(1) > 0) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
			DbConn.close(resultSet);
		}
		return false;
	}

	public boolean selectHostById(String hostID) throws Exception {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("select count(*) from subsystem_host where hostID=?");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, hostID);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			if (resultSet.getLong(1) > 0) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
			DbConn.close(resultSet);
		}
		return false;
	}

	public boolean selectDeviceById(String deviceID) throws Exception {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("select count(*) from fire_device where deviceID=?");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, deviceID);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			if (resultSet.getLong(1) > 0) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
			DbConn.close(resultSet);
		}
		return false;
	}

	public void addNode(JSONObject jsonObject) throws SQLException, ClassNotFoundException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer(
					"insert into subsystem_node(nodeID,buildingID,nodeType,canNo,hostID,nodeLocation,createTime) values(?,?,?,?,?,?,now())");
			preparedStatement = con.prepareStatement(buffer.toString());
			String nodeId = jsonObject.getString("device");
			preparedStatement.setString(1, nodeId);
			preparedStatement.setString(2, jsonObject.getString("device").substring(1, 5));
			if (nodeId.length() >= 11) {
				preparedStatement.setString(3, nodeId.substring(nodeId.length() - 1));
			} else {
				preparedStatement.setString(3, "");
			}
			preparedStatement.setString(4, nodeId.substring(7, 8));
			preparedStatement.setString(5, nodeId.substring(0, 6));
			preparedStatement.setString(6, jsonObject.getString("location"));
			// preparedStatement.setString(7,
			// jsonObject.getString("updatetime"));
			preparedStatement.executeUpdate();
			CacheDataBase.subSystemNodeState.put(nodeId, 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	public void updateNode(JSONObject jsonObject) throws SQLException, ClassNotFoundException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("update subsystem_node set");
			if (jsonObject.containsKey("hostID")) {
				buffer.append(" hostID=\'" + jsonObject.getString("hostID") + "\',");
			}
			if (jsonObject.containsKey("location")) {
				buffer.append(" nodeLocation=\'" + jsonObject.getString("location") + "\',");
			}
			buffer.delete(buffer.length() - 1, buffer.length());
			buffer.append(" where nodeID=?");
			System.out.println(buffer.toString());
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, jsonObject.getString("device"));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	public void deleteNode(JSONObject jsonObject) throws SQLException, ClassNotFoundException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("delete from subsystem_node where nodeID=?");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, jsonObject.getString("device"));
			preparedStatement.executeUpdate();
			CacheDataBase.subSystemNodeState.remove(jsonObject.getString("device"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	public void addHost(JSONObject jsonObject) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("insert into subsystem_host(hostID,buildingID,hostLocation) values(?,?,?)");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, jsonObject.getString("hostID"));
			preparedStatement.setString(2, jsonObject.getString("hostID").substring(1, 5));
			preparedStatement.setString(3, jsonObject.getString("hostLocation"));
			preparedStatement.executeUpdate();
			CacheDataBase.subSystemHostState.put(jsonObject.getString("hostID"), 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	public void updateHost(JSONObject jsonObject) throws SQLException, ClassNotFoundException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("update subsystem_host set hostLocation=? where hostID=?");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, jsonObject.getString("hostLocation"));
			preparedStatement.setString(2, jsonObject.getString("hostID"));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	public void deleteHost(JSONObject jsonObject) throws SQLException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("delete from subsystem_host where hostID=?");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, jsonObject.getString("hostID"));
			preparedStatement.executeUpdate();
			CacheDataBase.subSystemHostState.remove(jsonObject.getString("hostID"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	public void addDevice(JSONObject jsonObject) throws SQLException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer(
					"insert into fire_device(deviceID,buildingID,deviceCoding,deviceType,deviceModel,manufacturingUnit,productionDate,devicePersion,deviceTel,deviceinstallDate,durableYears,deviceLocation) values(?,?,?,?,?,?,?,?,?,?,?,?)");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, jsonObject.getString("deviceID"));
			preparedStatement.setString(2, jsonObject.getString("buildingID"));
			preparedStatement.setString(3, jsonObject.getString("deviceCoding"));
			preparedStatement.setString(4, jsonObject.getString("deviceType"));
			preparedStatement.setString(5, jsonObject.getString("deviceModel"));
			preparedStatement.setString(6, jsonObject.getString("manufacturingUnit"));
			if (jsonObject.containsKey("productionDate")) {
				preparedStatement.setDate(7, Date.valueOf(jsonObject.getString("productionDate")));
			} else {
				preparedStatement.setTimestamp(7, null);
			}
			preparedStatement.setString(8, jsonObject.getString("devicePersion"));
			preparedStatement.setString(9, jsonObject.getString("deviceTel"));
			if (jsonObject.containsKey("deviceinstallDate")) {
				preparedStatement.setDate(10, Date.valueOf(jsonObject.getString("deviceinstallDate")));
			} else {
				preparedStatement.setDate(10, null);
			}
			preparedStatement.setString(11, jsonObject.getString("durableYears"));
			preparedStatement.setString(12, jsonObject.getString("deviceLocation"));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	@SuppressWarnings("unchecked")
	public void updateDevice(JSONObject jsonObject) throws SQLException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("update fire_device set");
			String deviceID = jsonObject.getString("deviceID");
			jsonObject.remove("command");
			jsonObject.remove("type");
			jsonObject.remove("deviceID");
			Iterator<String> iterator = jsonObject.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = jsonObject.getString(key);
				buffer.append(" " + key + "=\'" + value + "\',");
			}
			buffer.delete(buffer.length() - 1, buffer.length());
			buffer.append(" where deviceID=?");
			System.out.println(buffer.toString());
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, deviceID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	public void deleteDevice(JSONObject jsonObject) throws SQLException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("delete from fire_device where deviceID=?");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, jsonObject.getString("deviceID"));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}
}
