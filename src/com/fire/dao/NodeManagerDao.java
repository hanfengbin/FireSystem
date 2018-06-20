package com.fire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import com.fire.util.DbConn;

public class NodeManagerDao {
	public void addNode(JSONObject jsonObject) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("insert into subsystem_node(nodeID,hostID,deviceLocation) values(?,?,?)");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, jsonObject.getString("device"));
			preparedStatement.setString(2, jsonObject.getString("device").substring(0, 6));
			preparedStatement.setString(3, jsonObject.getString("location"));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	public void updateNode(JSONObject jsonObject) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("update subsystem_node set deviceLocation=? where nodeID=?");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, jsonObject.getString("location"));
			preparedStatement.setString(2, jsonObject.getString("device"));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	public void deleteNode(JSONObject jsonObject) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("delete from subsystem_node where nodeID=?");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, jsonObject.getString("device"));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}

	public long[] getHostCanNums(String hostID) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		long[] nums = new long[6];
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer(
					"SELECT subsystem_node.canNo,count(*) AS 'count' FROM subsystem_node WHERE subsystem_node.hostID=? GROUP BY subsystem_node.canNo ORDER BY subsystem_node.canNo");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, hostID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				nums[resultSet.getInt(1) - 1] = resultSet.getLong(2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
			DbConn.close(resultSet);
		}
		return nums;
	}

	public void insertNodeBatch(String nodeID, String buildingID, String hostID, int canNo, String createTime) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = DbConn.getConnection();
			StringBuffer buffer = new StringBuffer("insert into subsystem_node(nodeID,buildingID,hostID,canNo,createTime) values (?,?,?,?,?)");
			preparedStatement = con.prepareStatement(buffer.toString());
			preparedStatement.setString(1, nodeID);
			preparedStatement.setString(2, buildingID);
			preparedStatement.setString(3, hostID);
			preparedStatement.setInt(4, canNo);
			preparedStatement.setString(5, createTime);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.close(con);
			DbConn.close(preparedStatement);
		}
	}
}
