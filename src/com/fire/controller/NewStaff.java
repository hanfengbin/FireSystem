package com.fire.controller;

import javax.servlet.http.HttpServletRequest;

import com.fire.service.StaffService;

public class NewStaff implements FireController {

	@Override
	public String controller(HttpServletRequest request) {
		StaffService staffService=new StaffService();
		if(staffService.newStaff(
				request.getParameter("name"), 
				request.getParameter("workArea"), 
				request.getParameter("userPwd1"),  
				request.getParameter("admin"), 
				request.getParameter("phone"))){
			return "success";
		}		
		return "failure";
	}

}
