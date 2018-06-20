package com.fire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import net.sf.json.JSONObject;

import com.fire.util.DbConn;

@SuppressWarnings({ "unchecked" })
public class SaveFaultDao {

	/**
	 * @param args
	 * @author Administrator
	 */

	public void saveFaultCurrent(JSONObject jsonObject) {
		if (jsonObject == null) {
			return;
		}
		Iterator<String> iterators = jsonObject.keys();
		String faultId = iterators.next();
		JSONObject faultJsonObject = jsonObject.getJSONObject(faultId);
		// System.out.println("存实时异常" + faultJsonObject);
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer(
					"insert into fault_current(faultCode,deviceID,faultCategory,faultType,faultTime,faultLocation,faultParameter,deviceParamOrder) values(?,?,?,?,?,?,?,?)");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, faultJsonObject.getString("faultID"));
			preparedStatement.setString(2, faultJsonObject.getString("deviceID"));
			preparedStatement.setString(3, faultJsonObject.getString("faultCategory"));
			if (faultJsonObject.containsKey("faultType")) {
				preparedStatement.setString(4, faultJsonObject.getString("faultType"));
			} else {
				preparedStatement.setString(4, null);
			}
			preparedStatement.setString(5, faultJsonObject.getString("faultTime").replace('T', ' '));
			preparedStatement.setString(6, faultJsonObject.getString("faultLocation"));
			if (faultJsonObject.containsKey("faultParameter")) {
				preparedStatement.setString(7, faultJsonObject.getString("faultParameter"));
			} else {
				preparedStatement.setString(7, null);
			}
			if (faultJsonObject.containsKey("deviceParamOrder")) {
				preparedStatement.setInt(8, Integer.valueOf(faultJsonObject.getString("deviceParamOrder")));
			} else {
				preparedStatement.setInt(8, 0);
			}
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("存实时异常" + faultJsonObject);
			e.printStackTrace();
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}

	}

	public void saveWarningData(JSONObject jsonObject) {
		if (jsonObject == null) {
			return;
		}
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("insert into alarm_record(deviceID,alarmType,alarmTime,alarmLocation) values(?,?,?,?)");
			preparedStatement = con.prepareStatement(buffer.toString());
			// jsonObject.remove("alarmID");
			/*
			 * for (Iterator<String> iterator = jsonObject.keys();
			 * iterator.hasNext();) { String key = iterator.next(); String vaule
			 * = jsonObject.getString(key); if (key.equals("alarmTime")) {
			 * Timestamp timestamp = Timestamp.valueOf(vaule);
			 * preparedStatement.setString(count, key);
			 * preparedStatement.setTimestamp(count + 4, timestamp);
			 * 
			 * } else { preparedStatement.setString(count, key);
			 * preparedStatement.setString(count + 4, vaule); } count++; }
			 */
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}

	}

	public void saveHistoryFault(JSONObject jsonObject) {
		if (jsonObject == null) {
			return;
		}
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer(
					"insert into water_fault_record(deviceID,faultCategory,faultType,faultTime,faultLocation,faultParameter) values(?,?,?,?,?,?)");
			preparedStatement = con.prepareStatement(buffer.toString());
			// jsonObject.remove("faultID");
			System.out.println("存历史异常：" + jsonObject);
			/*
			 * for (Iterator<String> iterator = jsonObject.keys();
			 * iterator.hasNext();) { String key = iterator.next(); String vaule
			 * = jsonObject.getString(key); if (key.equals("faultTime")) {
			 * Timestamp timestamp = Timestamp.valueOf(vaule);
			 * preparedStatement.setString(count, key);
			 * preparedStatement.setTimestamp(count + 5, timestamp);
			 * 
			 * } else { preparedStatement.setString(count, key);
			 * preparedStatement.setString(count + 5, vaule); } count++; }
			 */
			preparedStatement.setInt(1, jsonObject.getInt("deviceID"));
			preparedStatement.setString(2, jsonObject.getString("faultCategory"));
			preparedStatement.setString(3, jsonObject.getString("faultType"));
			preparedStatement.setString(4, jsonObject.getString("faultTime"));
			preparedStatement.setString(5, jsonObject.getString("faultLocation"));
			if (jsonObject.containsKey("faultParameter")) {
				preparedStatement.setString(6, jsonObject.getString("faultParameter"));
			} else {
				preparedStatement.setString(6, null);
			}
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
