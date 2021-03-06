package com.fire.web;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import com.fire.util.CacheDataBase;
import com.fire.util.FireDoorReceiveData;

@ServerEndpoint("/fireDoorParam.do")
public class FireDoorParamServer {
	@OnOpen
	public void whenOpen(Session session) {
		System.out.println("连接开始");
	}

	@OnMessage
	public void onMessage(String message, final Session session) throws IOException {
		System.out.println(message);
		JSONObject jsonObject = JSONObject.fromObject(message);
		if (jsonObject.containsKey("buildId")) {
			String buildId = jsonObject.getString("buildId");
			String canNo = jsonObject.getString("canNo");
			JSONObject sendData = new JSONObject();
			if (CacheDataBase.fireDoorMainValues.containsKey(buildId)) {
				sendData.put("tableHeader", FireDoorReceiveData.fireDoorTableHeader);
				sendData.put("tableContent", CacheDataBase.fireDoorMainValues.get(buildId).get(canNo));
				System.out.println("sendCurrentData" + sendData);
				session.getBasicRemote().sendText(sendData.toString());

			} else {
				System.out.println("该楼栋暂无数据");
			}
		}
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("连接关闭");
	}

	@OnError
	public void onError(Throwable throwable) {
		System.err.println(throwable);
	}
}
