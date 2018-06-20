package com.fire.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fire.service.ChartItemService;

public class ChartItem implements FireController {

	@Override
	public String controller(HttpServletRequest request) {
		ChartItemService chartItemService = new ChartItemService();
		String parasString = request.getParameter("paras");
		String[] paras = null;
		if (parasString != null)
			paras = parasString.split(",");
		JSONObject res = null;
		if (request.getParameter("isPie") == null)
			res = chartItemService.getChartItemJsonObject(request.getParameter("enum"), paras);
		else
			try {
				res = chartItemService.getPieChartItemJsonObject(request.getParameter("enum"), paras);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		String callback = request.getParameter("callback");
		if (callback == null) {
			return res.toString();
		} else {
			return callback + "(" + res.toString() + ")";
		}
	}
}
