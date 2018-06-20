package com.fire.service;

import java.util.Map;

import net.sf.json.JSONObject;

import com.fire.dao.TableItemEnum;

public class TableItemService {
	public static TableItemEnum tableItemEnums[] = TableItemEnum.values();

	public JSONObject getTableItemJsonObject(String tableItemEnum) {
		TableItemEnum tie = null;
		try {// 通过名称获取TableItemEnum，失败则返回NULL
				// tie = TableItemEnum.valueOf(tableItemEnum);
			tie = tableItemEnums[Integer.parseInt(tableItemEnum)];
		} catch (NumberFormatException e) {
			tie = TableItemEnum.valueOf(tableItemEnum);
			JSONObject res = tie.getJsonObject();
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum名称不存在，tableItemEnum：" + tableItemEnum);
			return null;
		}
		JSONObject res = tie.getJsonObject();
		return res;
	}

	public JSONObject getFaultTableItemJsonObject(String tableItemEnum) {
		TableItemEnum tie = null;
		try {// 通过名称获取TableItemEnum，失败则返回NULL
				// tie = TableItemEnum.valueOf(tableItemEnum);
			tie = tableItemEnums[Integer.parseInt(tableItemEnum)];
		} catch (NumberFormatException e) {
			tie = TableItemEnum.valueOf(tableItemEnum);
			JSONObject res = tie.getFaultJsonObject();
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum名称不存在，tableItemEnum：" + tableItemEnum);
			return null;
		}
		JSONObject res = tie.getFaultJsonObject();
		return res;
	}

	public JSONObject getFaultTableItemJsonObject(String tableItemEnum, String[] param) {
		TableItemEnum tie = null;
		try {// 通过名称获取TableItemEnum，失败则返回NULL
				// tie = TableItemEnum.valueOf(tableItemEnum);
			tie = tableItemEnums[Integer.parseInt(tableItemEnum)];
		} catch (NumberFormatException e) {
			tie = TableItemEnum.valueOf(tableItemEnum);
			JSONObject res = tie.getFaultJsonObject(param);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum名称不存在，tableItemEnum：" + tableItemEnum);
			return null;
		}
		JSONObject res = tie.getFaultJsonObject(param);
		return res;
	}

	public JSONObject getScreenFaultJsonObject(Map<String, String[]> map) {
		TableItemEnum tie = null;
		String tableItemEnum = map.get("enum")[0];
		String sqlString = "";
		try {// 通过名称获取TableItemEnum，失败则返回NULL
				// tie = TableItemEnum.valueOf(tableItemEnum);
			tie = tableItemEnums[Integer.parseInt(tableItemEnum)];
			sqlString = tie.getSqlStatement();
			tie.setSqlStatement(getScreenFaultSql(map, sqlString));
		} catch (NumberFormatException e) {
			tie = TableItemEnum.valueOf(tableItemEnum);
			sqlString = tie.getSqlStatement();
			tie.setSqlStatement(getScreenFaultSql(map, sqlString));
			JSONObject res = tie.getJsonObject();
			tie.setSqlStatement(sqlString);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum名称不存在，tableItemEnum：" + tableItemEnum);
			return null;
		}
		JSONObject res = tie.getJsonObject();
		tie.setSqlStatement(sqlString);
		return res;
	}

	public String getScreenFaultSql(Map<String, String[]> map, String sqlString) {
		StringBuffer sqlBuffer = new StringBuffer(sqlString);
		if (map.containsKey("system")) {
			String system = map.get("system")[0];
			sqlBuffer.append(" and substring(fault_current.deviceID,1,1)='" + system + "'");
		}
		if (map.containsKey("faultCategory")) {
			String faultCategory = map.get("faultCategory")[0];
			sqlBuffer.append(" and faultCategory='" + faultCategory + "'");
		}
		if (map.containsKey("mvfaultDealState")) {
			String mvfaultDealState = map.get("mvfaultDealState")[0];
			sqlBuffer.append(" and mvfaultDealState='" + mvfaultDealState + "'");
		}
		if (map.containsKey("faultTimeStart") && map.containsKey("faultTimeEnd")) {
			String faultTimeStart = map.get("faultTimeStart")[0];
			String faultTimeEnd = map.get("faultTimeEnd")[0];
			sqlBuffer.append(" and faultTime >'" + faultTimeStart + " 00:00:00'" + " and faultTime <'" + faultTimeEnd + " 23:59:59" + "'");
		}
		sqlBuffer.append(" order by faultTime desc");
		// System.out.println("sql:" + sqlBuffer);
		return sqlBuffer.toString();
	}
}
