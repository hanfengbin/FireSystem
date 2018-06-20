package com.fire.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fire.service.StaffService;

public class QueryStaffById implements FireController {

	@Override
	public String controller(HttpServletRequest request) {
		StaffService staffService=new StaffService();
		JSONObject res=staffService.getStaffByStaffId(Integer.parseInt(request.getParameter("staffId")));
		if(res==null)
			return "No such Staff";
		return res.toString();
	}

}
