package com.fire.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fire.service.StatusService;

public class StatusController implements FireController {

	@Override
	public String controller(HttpServletRequest request) {
		StatusService statusService = new StatusService();
		JSONObject res = statusService.getStatusByBuilding(request.getParameter("buildId"), request.getParameter("system"));
		String callback = request.getParameter("callback");
		if (callback == null) {
			return res.toString();
		} else {
			return callback + "(" + res.toString() + ")";
		}
	}

}
