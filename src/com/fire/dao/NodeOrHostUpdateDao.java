package com.fire.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.fire.util.DbConn;

public class NodeOrHostUpdateDao {
	public void updateNodeState(String[] updateSql) throws SQLException {
		Connection con = null;
		Statement statement = null;
		try {
			con = DbConn.getConnection();
			con.setAutoCommit(false);
			statement = con.createStatement();
			StringBuffer bufferFault = new StringBuffer("update subsystem_node set isNormal=1 where nodeID in ");
			StringBuffer bufferNormal = new StringBuffer("update subsystem_node set isNormal=0 where nodeID in ");
			statement.addBatch(bufferFault.append(updateSql[0]).toString());
			statement.addBatch(bufferNormal.append(updateSql[1]).toString());
			// System.out.println(bufferFault.toString()+"-"+bufferNormal.toString());
			statement.executeBatch();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			con.rollback();
			System.out.println("事务回滚");
			e.printStackTrace();
		} finally {
			con.setAutoCommit(true);
			DbConn.close(con);
			DbConn.close(statement);
		}
	}

	public void updateHostState(String[] updateCondition) throws SQLException {
		Connection con = null;
		Statement statement = null;
		try {
			con = DbConn.getConnection();
			con.setAutoCommit(false);
			statement = con.createStatement();
			StringBuffer bufferFault = new StringBuffer("update subsystem_host set isNormal=1 where hostID in ");
			StringBuffer bufferNormal = new StringBuffer("update subsystem_host set isNormal=0 where hostID in ");
			statement.addBatch(bufferFault.append(updateCondition[0]).toString());
			statement.addBatch(bufferNormal.append(updateCondition[1]).toString());
			statement.executeBatch();
			con.commit();
			// System.out.println(bufferFault.toString() + "-" +
			// bufferNormal.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
			System.out.println("事务回滚");
		} finally {
			con.setAutoCommit(true);
			DbConn.close(con);
			DbConn.close(statement);
		}
	}
}
