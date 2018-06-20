package com.fire.controller;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fire.service.StaffService;

public class Login implements FireController {

	@Override
	public String controller(HttpServletRequest request) {

		StaffService staffService = new StaffService();
		Map<String, String> map = null;
		try {
			map = staffService.staffLogin(request.getParameter("id"), request.getParameter("password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (map == null) {
			return "failure";
		}
		HttpSession session = request.getSession();

		Set<String> keys = map.keySet();
		for (String key : keys) {
			session.setAttribute(key, map.get(key));
		}
		if ("2".equals(map.get("StaffPermissions"))) {
			return "user";
		} else if ("3".equals(map.get("StaffPermissions"))) {
			return "maintenance";
		}
		return "success";
	}
}
