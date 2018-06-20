package com.fire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.fire.util.DbConn;

public enum ChartItemEnum {
	// 一个enmu对应一个Sql语句

	// 所有异常统计，按子系统分类，30-------0
	faultStateDays(
			"select SUBSTR(deviceID,1,1) as 'category',count(*) as 'total',DATE_FORMAT(faultTime,'%Y-%m-%d') as 'date' from fault_current where faultTime>date_sub(now(),interval 29 day) group by DATE_FORMAT(faultTime,'%Y-%m-%d'),category",
			30),

	faultStateDaysSub(
			"select SUBSTR(deviceID,1,1) as 'category',count(*) as 'total',DATE_FORMAT(faultTime,'%Y-%m-%d') as 'date' from fault_current where SUBSTR(deviceID,1,1)=? AND faultTime>date_sub(now(),interval 29 day) group by DATE_FORMAT(faultTime,'%Y-%m-%d'),category",
			30),

	faultStateDaysSubParas(
			"select faultCategory as 'category',count(*) as 'total',DATE_FORMAT(faultTime,'%Y-%m-%d') as 'date' from fault_current where SUBSTR(deviceID,1,1)=? AND faultTime>date_sub(now(),interval 29 day) group by DATE_FORMAT(faultTime,'%Y-%m-%d'),category",
			30),

	faultStateDaysSubAndBuildingParas(
			"select faultCategory as 'category',count(*) as 'total',DATE_FORMAT(faultTime,'%Y-%m-%d') as 'date' from fault_current where SUBSTR(deviceID,1,1)=? AND SUBSTR(deviceID,2,4)=? AND faultTime>date_sub(now(),interval 29 day) group by DATE_FORMAT(faultTime,'%Y-%m-%d'),category",
			30),

	// 所有异常饼状图统计，按校区+故障火警预警分类,7-------12
	faultState(
			"select CONCAT(SUBSTR(deviceID,2,2),faultcategory) as 'category',count(*) as 'total' from fault_current group by category order by category Desc"), faultStatisticsByThirtyDayOrderSys(
			"select SUBSTR(deviceID,1,1) as 'category',count(*) as 'total',DATE_FORMAT(faultTime,'%Y-%m-%d') as 'date' from fault_current where faultTime>date_sub(now(),interval 29 day) group by DATE_FORMAT(faultTime,'%Y-%m-%d'),category",
			30), faultStatisticsByThirtyDay(
			"select 1 as 'category',count(*) as 'total',DATE_FORMAT(faultTime,'%Y-%m-%d') as 'date' from fault_current where faultTime>date_sub(now(),interval 29 day) group by DATE_FORMAT(faultTime,'%Y-%m-%d')",
			30), faultStatisticsByThirtyDay3(
			"select 1 as 'category',count(*) as 'total',DATE_FORMAT(faultTime,'%Y-%m-%d') as 'date' from fault_current where SUBSTRING(deviceID,1,1)='3' AND faultTime>date_sub(now(),interval 29 day) group by DATE_FORMAT(faultTime,'%Y-%m-%d')",
			30), faultStatisticsByThirtyDay4(
			"select 1 as 'category',count(*) as 'total',DATE_FORMAT(faultTime,'%Y-%m-%d') as 'date' from fault_current where SUBSTRING(deviceID,1,1)='4' AND faultTime>date_sub(now(),interval 29 day) group by DATE_FORMAT(faultTime,'%Y-%m-%d')",
			30), faultStatisticsByThirtyDay5(
			"select 1 as 'category',count(*) as 'total',DATE_FORMAT(faultTime,'%Y-%m-%d') as 'date' from fault_current where SUBSTRING(deviceID,1,1)='5' AND faultTime>date_sub(now(),interval 29 day) group by DATE_FORMAT(faultTime,'%Y-%m-%d')",
			30), faultStatisticsByThirtyDay6(
			"select 1 as 'category',count(*) as 'total',DATE_FORMAT(faultTime,'%Y-%m-%d') as 'date' from fault_current where SUBSTRING(deviceID,1,1)='6' AND faultTime>date_sub(now(),interval 29 day) group by DATE_FORMAT(faultTime,'%Y-%m-%d')",
			30)
	// 结束标记
	;
	private String sqlStatement;// Sql语句，生成tableContent
	private int resultLength; // 表格各列标题，用‘,’分割，生成tableHeader,如"标题1,标题2,标题3"

	private ChartItemEnum(String sqlStateString, int resultLength) {
		this.sqlStatement = sqlStateString;
		this.resultLength = resultLength;
	}

	private ChartItemEnum(String sqlStateString) {
		this.sqlStatement = sqlStateString;
	}

	public String getSqlStatement() {
		return sqlStatement;
	}

	public int getResultLength() {
		return resultLength;
	}

	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}

	public void setResultLength(int resultLength) {
		this.resultLength = resultLength;
	}

	public JSONObject getChartJsonObject(String[] paras) {

		JSONObject jsonChartItem = new JSONObject();
		Connection conn = null;
		conn = DbConn.getConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> xDate = new ArrayList<String>();
		long oneDay = 1000 * 60 * 60 * 24;
		for (int i = resultLength - 1, j = 0; i >= 0; i--, j++) {
			String tmp = sdf.format(System.currentTimeMillis() - oneDay * j);
			xDate.add(0, tmp.substring(5, 10));
		}
		Map<String, int[]> map = new HashMap<String, int[]>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sqlStatement);
			if (paras != null) {
				for (int i = 1; i <= paras.length; i++)
					pstmt.setString(i, paras[i - 1]);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int index = xDate.indexOf((rs.getString("date")).substring(5));

				int arr[] = null;
				String cateString = rs.getString("category");
				if (map.containsKey(cateString)) {
					arr = map.get(cateString);
				} else {
					arr = new int[resultLength];
					map.put(cateString, arr);
				}
				arr[index] = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("请求的ChartItemEnum中Sql运行错误");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ChartItemEnum中Sql执行代码块其他错误");
			return null;
		} finally {
			DbConn.close(conn);
			DbConn.close(pstmt);
			DbConn.close(rs);
		}
		jsonChartItem.put("date", xDate);

		JSONObject data = new JSONObject();
		data.putAll(map);
		jsonChartItem.put("data", data);
		JSONObject res = new JSONObject();
		res.put("chartItem", jsonChartItem);
		return res;
	}

	public JSONObject getPieChartJsonObject(String[] paras) throws ClassNotFoundException {

		JSONObject jsonChartItem = new JSONObject();
		Connection conn = null;
		conn = DbConn.getConnection();
		List<String> categoryList = new ArrayList<String>();
		List<Integer> valueList = new ArrayList<Integer>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sqlStatement);
			if (paras != null) {
				for (int i = 1; i <= paras.length; i++)
					pstmt.setString(i, paras[i - 1]);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				categoryList.add(rs.getString("category"));
				valueList.add(rs.getInt("total"));
			}
			jsonChartItem.put("categoryList", categoryList);
			jsonChartItem.put("valueList", valueList);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("请求的ChartItemEnum中Sql运行错误");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ChartItemEnum中Sql执行代码块其他错误");
			return null;
		} finally {
			DbConn.close(conn);
			DbConn.close(pstmt);
			DbConn.close(rs);
		}
		JSONObject res = new JSONObject();
		res.put("pieChartItem", jsonChartItem);
		return res;
	}

	public static void main(String[] args) {
		ChartItemEnum c = ChartItemEnum.valueOf("faultStateDays");
		System.out.println(c.getChartJsonObject(null));

		// System.out.println(c.getChartJsonObject(c.getResultLength()));
	}
}
