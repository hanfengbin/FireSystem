package com.fire.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fire.util.SubSystemCode;

public class FaultTypeController implements FireController {

	@Override
	public String controller(HttpServletRequest request) {
		SubSystemCode faultCode = new SubSystemCode();
		JSONObject res = faultCode.getFaultTypeJsonObject(request.getParameter("system"), Integer.valueOf(request.getParameter("faultType")));
		String[] callback = request.getParameterValues("callback");
		if (callback == null) {
			return res.toString();
		} else {
			return callback[0] + "(" + res.toString() + ")";
		}

	}
}
