package com.fire.controller;

import javax.servlet.http.HttpServletRequest;

import com.fire.service.StaffService;

public class DeleteStaff implements FireController {

	@Override
	public String controller(HttpServletRequest request) {
		
		StaffService staffService=new StaffService();
		if(staffService.deleteStaffById(request.getParameter("userId"))){
			return "success";
		}		
		return "failure";
	}

}
