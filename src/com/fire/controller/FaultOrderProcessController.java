package com.fire.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fire.service.FaultOrderProcessService;

public class FaultOrderProcessController implements FireController {

	@Override
	public String controller(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String, String[]> map = request.getParameterMap();
		FaultOrderProcessService service = new FaultOrderProcessService();
		String result = "";
		if ("receive".equals(map.get("proccess")[0])) {
			result = service.receiveOrderProcess(map);
		} else if ("complete".equals(map.get("proccess")[0])) {
			result = service.completeOrderProcess(map);
		}
		return result;
	}

}
