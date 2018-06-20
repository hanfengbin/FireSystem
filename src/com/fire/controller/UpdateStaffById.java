package com.fire.controller;

import javax.servlet.http.HttpServletRequest;

import com.fire.service.StaffService;

public class UpdateStaffById implements FireController {

	@Override
	public String controller(HttpServletRequest request) {
		StaffService staffService = new StaffService();
		if (staffService.updateStaff(request.getParameter("name"), request.getParameter("workArea"), request.getParameter("userPwd1"),
				request.getParameter("admin"), request.getParameter("phone"), request.getParameter("userId"))) {
			return "success";
		}
		return "failure";
	}

}
