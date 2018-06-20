package com.fire.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fire.service.TableItemService;

public class QueryFaultDetailByPerson implements FireController {

	@Override
	public String controller(HttpServletRequest request) {
		// TODO Auto-generated method stub
		TableItemService tableItemService = new TableItemService();
		String[] dealPerson = new String[2];
		dealPerson[0] = dealPerson[1] = request.getParameter("dealPerson");
		JSONObject res = tableItemService.getFaultTableItemJsonObject(request.getParameter("enum"),dealPerson);
		String callback = request.getParameter("callback");
		if (callback == null) {
			return res.toString();
		} else {
			return callback + "(" + res.toString() + ")";
		}
	}

}
