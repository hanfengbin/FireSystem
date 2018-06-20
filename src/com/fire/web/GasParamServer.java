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
import com.fire.util.GasReceiveData;

@ServerEndpoint("/gasParam.do")
public class GasParamServer {
	@OnOpen
	public void whenOpen(Session session) {
		System.out.println("气体实时数据连接开始");
		System.out.println("gas:" + CacheDataBase.gasMainValues);
	}

	@OnMessage
	public void onMessage(String message, final Session session) throws IOException {
		System.out.println(message);
		JSONObject jsonObject = JSONObject.fromObject(message);
		if (jsonObject.containsKey("buildId")) {
			String buildId = jsonObject.getString("buildId");
			String canNo = jsonObject.getString("canNo").split("-")[0];
			String type = jsonObject.getString("canNo").split("-")[1];
			JSONObject sendData = new JSONObject();
			if (CacheDataBase.gasMainValues.containsKey(buildId)) {
				if ("a".equals(type)) {
					sendData.put("tableHeader", GasReceiveData.gasTableHeadera);
				} else if ("b".equals(type)) {
					sendData.put("tableHeader", GasReceiveData.gasTableHeaderb);
				}
				sendData.put("tableContent", CacheDataBase.gasMainValues.get(buildId).get(canNo));
				System.out.println("sendGasData:" + sendData);
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
		throwable.printStackTrace();
		// System.err.println(throwable);
	}
}
