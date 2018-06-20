package com.fire.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fire.service.TableItemService;

public class FaultScreen implements FireController {
	@Override
	public String controller(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		TableItemService tableItemService = new TableItemService();
		JSONObject res = tableItemService.getScreenFaultJsonObject(map);
		String[] callback = map.get("callback");
		if (callback == null) {
			return res.toString();
		} else {
			return callback[0] + "(" + res.toString() + ")";
		}

	}

}
