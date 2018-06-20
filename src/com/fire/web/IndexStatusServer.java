package com.fire.web;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fire.service.TableItemService;

@ServerEndpoint("/index.do")
public class IndexStatusServer {
	private TableItemService tableItemService = new TableItemService();

	@OnOpen
	public void whenOpen(Session session) {
		// System.out.println("连接开始");
	}

	@OnMessage
	public void onMessage(String message, final Session session) throws IOException {
		JSONObject sendData = new JSONObject();
		if ("".equals(message)) {
			JSONArray jsonArray = tableItemService.getTableItemJsonObject("indexStatusData").getJSONObject("tableItem").getJSONObject("tableContent")
					.getJSONArray("1");
			JSONObject faultObject = tableItemService.getFaultTableItemJsonObject("faultDetailedInformation");
			sendData.put("status", jsonArray);
			sendData.put("fault", faultObject);
		} else if (message == null) {

		} else {
			JSONArray jsonArray = tableItemService.getTableItemJsonObject("indexStatusData" + message).getJSONObject("tableItem").getJSONObject("tableContent")
					.getJSONArray("1");
			JSONObject faultObject = tableItemService.getFaultTableItemJsonObject("faultDetailedInformation" + message);
			sendData.put("status", jsonArray);
			sendData.put("fault", faultObject);
		}
		session.getBasicRemote().sendText(sendData.toString());
	}

	@OnClose
	public void onClose(Session session) {
		// System.out.println("连接关闭");
	}

	@OnError
	public void onError(Throwable throwable) {
		System.err.println(throwable);
	}
}
