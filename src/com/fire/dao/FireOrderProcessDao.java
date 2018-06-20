package com.fire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fire.util.DbConn;

public class FireOrderProcessDao {
	public void receiveOrderProcessMethod(String faultId, String dealPerson) throws SQLException {
		Connection conn = DbConn.getConnection();
		PreparedStatement preparedStatement = null;
		StringBuffer buffer = new StringBuffer("UPDATE fault_current SET mvfaultDealState='处置中',mvfaultDeviceDealPerson=?, "
				+ "mnDeviceksczTime=now() WHERE faultID=? ");
		preparedStatement = conn.prepareStatement(buffer.toString());
		preparedStatement.setString(1, dealPerson);
		preparedStatement.setInt(2, Integer.valueOf(faultId));
		preparedStatement.executeUpdate();
		preparedStatement.close();
		conn.close();

	}

	public void completeOrderProcessMethod(String faultId) throws SQLException {
		Connection conn = DbConn.getConnection();
		PreparedStatement preparedStatement = null;
		StringBuffer buffer = new StringBuffer("UPDATE fault_current SET mvfaultDealState='已处置', " + "mvFaultCtime=now() WHERE faultID=? ");
		preparedStatement = conn.prepareStatement(buffer.toString());
		preparedStatement.setInt(1, Integer.valueOf(faultId));
		preparedStatement.executeUpdate();
		preparedStatement.close();
		conn.close();
	}

}
