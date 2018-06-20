package com.fire.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Logout implements FireController{

	@Override
	public String controller(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "success";
	}

}
