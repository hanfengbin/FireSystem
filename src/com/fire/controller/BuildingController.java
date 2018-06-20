package com.fire.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fire.service.StatusService;

public class BuildingController implements FireController {
	@Override
	public String controller(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StatusService mapService = new StatusService();
		JSONObject res = mapService.getBuildJsonObject(request.getParameter("enum"));
		String callback = request.getParameter("callback");
		if (callback == null) {
			return res.toString();
		} else {
			return callback + "(" + res.toString() + ")";
		}

	}

}
