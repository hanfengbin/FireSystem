package com.fire.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.fire.util.DbConn;

public class StaffService {
	public Map<String, String> staffLogin(String staffId, String staffPassword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbConn.getConnection();
			pstmt = conn.prepareStatement("select * from staff_info where ( StaffID=" + staffId + " or StaffPhoneNumber=" + staffId + " ) and StaffPassword=\'"
					+ staffPassword + "\'");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("StaffName", rs.getString("StaffName"));
				map.put("StaffId", staffId);
				map.put("StaffWorkArea", rs.getString("StaffWorkArea"));
				map.put("StaffPermissions", rs.getString("StaffPermissions"));

				return map;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.close(conn);
			DbConn.close(rs);
			DbConn.close(pstmt);
		}

		return null;
	}

	public boolean newStaff(String staffName, String staffWorkArea, String staffPassword, String StaffPermissions, String staffPhoneNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int staffId = 0;
		try {
			conn = DbConn.getConnection();
			pstmt = conn.prepareStatement("select max(StaffID) as staffID from staff_info");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				staffId = Integer.parseInt(rs.getString("staffID")) + 1;
			}
			String sqlStatement = "INSERT INTO staff_info (staffId,staffname,staffphonenumber,staffworkArea,staffpassword,staffpermissions) VALUES ('"
					+ staffId + "','" + staffName + "','" + staffPhoneNumber + "','" + staffWorkArea + "','" + staffPassword + "','" + StaffPermissions + "')";
			pstmt = conn.prepareStatement(sqlStatement);
			pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			DbConn.close(conn);
			DbConn.close(rs);
			DbConn.close(pstmt);
		}
		return true;
	}

	public JSONObject getStaffByStaffId(int staffId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONObject res = new JSONObject();
		String sqlStatement = "select * from staff_info where staffID=" + staffId;
		try {
			conn = DbConn.getConnection();
			pstmt = conn.prepareStatement(sqlStatement);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				res.put("StaffId", rs.getString("StaffID"));
				res.put("StaffName", rs.getString("StaffName"));
				res.put("StaffPhoneNumber", rs.getString("StaffPhoneNumber"));
				res.put("StaffWorkArea", rs.getString("StaffWorkArea"));
				res.put("StaffPassword", rs.getString("StaffPassword"));
				res.put("StaffPermissions", rs.getString("StaffPermissions"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DbConn.close(conn);
			DbConn.close(rs);
			DbConn.close(pstmt);
		}
		return res;
	}

	public boolean updateStaff(String staffName, String staffWorkArea, String staffPassword, String StaffPermissions, String staffPhoneNumber, String staffId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbConn.getConnection();
			String sqlStatement = "UPDATE staff_info SET " + "StaffName='" + staffName + "'," + "staffphonenumber='" + staffPhoneNumber + "',"
					+ "staffworkArea='" + staffWorkArea + "'," + "staffpassword='" + staffPassword + "'," + "staffpermissions='" + StaffPermissions + "'"
					+ "WHERE StaffID='" + staffId + "'";
			pstmt = conn.prepareStatement(sqlStatement);
			pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			DbConn.close(conn);
			DbConn.close(rs);
			DbConn.close(pstmt);
		}
		return true;
	}

	public boolean deleteStaffById(String staffId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbConn.getConnection();
			String sqlStatement = "Delete from staff_info where StaffID=" + staffId;
			pstmt = conn.prepareStatement(sqlStatement);
			pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			DbConn.close(conn);
			DbConn.close(rs);
			DbConn.close(pstmt);
		}
		return true;
	}

	public boolean updateStaff(String staffPassword, String staffId, String oldPassword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbConn.getConnection();
			String sqlStatement = "UPDATE staff_info SET " + "staffpassword='" + staffPassword + "' " + "WHERE StaffID='" + staffId + "' AND StaffPassword='"
					+ oldPassword + "'";
			pstmt = conn.prepareStatement(sqlStatement);
			pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			DbConn.close(conn);
			DbConn.close(rs);
			DbConn.close(pstmt);
		}
		return true;
	}

}
