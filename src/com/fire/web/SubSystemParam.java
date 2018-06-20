package com.fire.web;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

@ServerEndpoint("/SubSystemdevicePara")
public class SubSystemParam {
	@OnOpen
	public void whenOpen(Session session) {
		System.out.println("连接开始");
	}

	@OnMessage
	public void onMessage(String message) {
		JSONObject jsonObject = JSONObject.fromObject(message);

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
