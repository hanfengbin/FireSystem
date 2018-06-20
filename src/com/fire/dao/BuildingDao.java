package com.fire.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import com.fire.util.DbConn;

public class BuildingDao {
	public JSONObject getCallJsonObject(String callStatement) throws SQLException {
		Connection conn = null;
		conn = DbConn.getConnection();
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		JSONObject jsonResult = new JSONObject(); 
		try {
			callableStatement = conn.prepareCall(callStatement);
			rs = callableStatement.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					jsonResult.put(metaData.getColumnName(i), rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum中Sql运行错误");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("TableItemEnum中Sql执行代码块其他错误");
		} finally {
			DbConn.close(conn);
			callableStatement.close();
			DbConn.close(rs);
		}
		return jsonResult;
	}
}
