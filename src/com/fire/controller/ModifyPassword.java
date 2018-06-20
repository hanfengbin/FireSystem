package com.fire.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fire.service.StaffService;

public class ModifyPassword implements FireController {

	@Override
	public String controller(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String StaffId=(String) session.getAttribute("StaffId");
		StaffService staffService=new StaffService();
		if(staffService.updateStaff(request.getParameter("newPwd"), StaffId,request.getParameter("Pwd"))){
			return "success";
		}
		return "fauliure";
	}

}
