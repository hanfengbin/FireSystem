package com.fire.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fire.service.TableItemService;

public class FaultDetailController implements FireController {

	@Override
	public String controller(HttpServletRequest request) {
		// TODO Auto-generated method stub
		TableItemService tableItemService = new TableItemService();
		JSONObject res = tableItemService.getFaultTableItemJsonObject(request.getParameter("enum"));
		String callback = request.getParameter("callback");
		if (callback == null) {
			return res.toString();
		} else {
			return callback + "(" + res.toString() + ")";
		}

	}

}
