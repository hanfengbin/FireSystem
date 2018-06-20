package com.fire.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fire.service.TableItemService;

public class TableItem implements FireController{

	@Override
	public String controller(HttpServletRequest request) {
		TableItemService tableItemService=new TableItemService();
		JSONObject res=tableItemService.getTableItemJsonObject(request.getParameter("enum"));
		String callback=request.getParameter("callback");
		if(callback==null){
			return res.toString();
		}else{
			return callback+"("+res.toString()+")";
		}
	}
}
