package com.fire.service;

import net.sf.json.JSONObject;

import com.fire.dao.ChartItemEnum;

public class ChartItemService {
	public static ChartItemEnum chartItemEnums[] = ChartItemEnum.values();

	public JSONObject getChartItemJsonObject(String chartItemEnum,String[] paras) {
		ChartItemEnum cie = null;
		try {// 通过名称获取TableItemEnum，失败则返回NULL
				// tie = TableItemEnum.valueOf(tableItemEnum);
			cie = chartItemEnums[Integer.parseInt(chartItemEnum)];
		} catch (NumberFormatException e) {
			cie = ChartItemEnum.valueOf(chartItemEnum);
			JSONObject res = cie.getChartJsonObject(paras);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum名称不存在，chartItemEnum：" + chartItemEnum);
			return null;
		}
		JSONObject res = cie.getChartJsonObject(paras);
		return res;
	}

	public JSONObject getPieChartItemJsonObject(String chartItemEnum,String[] paras) throws ClassNotFoundException {
		ChartItemEnum cie = null;
		try {// 通过名称获取TableItemEnum，失败则返回NULL
				// tie = TableItemEnum.valueOf(tableItemEnum);
			cie = chartItemEnums[Integer.parseInt(chartItemEnum)];
		} catch (NumberFormatException e) {
			cie = ChartItemEnum.valueOf(chartItemEnum);
			JSONObject res = cie.getPieChartJsonObject(paras);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("请求的TableItemEnum名称不存在，chartItemEnum：" + chartItemEnum);
			return null;
		}
		JSONObject res = cie.getPieChartJsonObject(paras);
		return res;
	}
}
