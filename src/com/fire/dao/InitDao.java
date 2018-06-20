package com.fire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import com.fire.util.DbConn;

public class InitDao {

	/**
	 * @param args
	 */
	public Map<String, JSONObject> loadBuilding(Connection con) {
		Map<String, JSONObject> map = new HashMap<String, JSONObject>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer buffer = new StringBuffer("select * from fire_building");
		try {
			preparedStatement = con.prepareStatement(buffer.toString());
			resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int column = resultSetMetaData.getColumnCount();
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				String buildingId = resultSet.getString("buildingID");
				for (int i = 1; i <= column; i++) {
					jsonObject.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
				}
				map.put(buildingId, jsonObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.close(preparedStatement);
			DbConn.close(resultSet);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public void test(Map<String, JSONObject> map) {
		Iterator<Entry<String, JSONObject>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, JSONObject> entry = iterator.next();
			System.out.println(entry.getValue());
			JSONObject jsonObject = entry.getValue();
			if (!jsonObject.containsKey("buildingLat")) {
				System.out.println("该楼栋还未填写经度信息");
			}
			for (Iterator<String> iterator2 = jsonObject.keys(); iterator2.hasNext();) {
				String tmp = iterator2.next();
				Object obj = jsonObject.get(tmp);
				System.out.println(tmp + ":" + obj);
			}
		}
	}
}
