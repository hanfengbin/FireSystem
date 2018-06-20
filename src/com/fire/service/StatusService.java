package com.fire.service;

import java.sql.SQLException;

import net.sf.json.JSONObject;

import com.fire.dao.BuildingDao;
import com.fire.dao.TableItemEnum;

public class StatusService {
	public static TableItemEnum tableItemEnums[] = TableItemEnum.values();

	public JSONObject getStatusByBuilding(String buildingId, String system) {
		BuildingDao buildingDao = new BuildingDao();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = buildingDao.getCallJsonObject("call map_building(" + buildingId + "," + system + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	public JSONObject getBuildJsonObject(String tableItemEnum) {

		TableItemEnum tie = null;
		try {// 通过名称获取TableItemEnum，失败则返回NULL
				// tie = TableItemEnum.valueOf(tableItemEnum);
			tie = tableItemEnums[Integer.parseInt(tableItemEnum)];
		} catch (NumberFormatException e) {
			tie = TableItemEnum.valueOf(tableItemEnum);
			JSONObject res = tie.getJsonObject(tie.getSqlStatement());
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum名称不存在，tableItemEnum：" + tableItemEnum);
			return null;
		}
		JSONObject res = tie.getJsonObject(tie.getSqlStatement());
		return res;

	}
}
