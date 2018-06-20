package com.fire.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SendDataClient implements Runnable {
	public static String makeNodeMsg() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "add");
		jsonObject.put("type", "node");
		jsonObject.put("device", "6101011004a");
		jsonObject.put("location", "-2楼");
		jsonObject.put("updatetime", "2017-6-28 11:15:00");
		return jsonObject.toString();
	}

	public static String makeNodeUpdateMsg() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "modify");
		jsonObject.put("type", "node");
		jsonObject.put("device", "6101011001a");
		jsonObject.put("location", "-3楼");
		return jsonObject.toString() + "\r\n";
	}

	public static String makeNodeDeleteMsg() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "delete");
		jsonObject.put("type", "node");
		jsonObject.put("device", "6101011004a");
		return jsonObject.toString() + "\n";
	}

	public static String makeHostMsg() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "add");
		jsonObject.put("type", "host");
		jsonObject.put("hostID", "3101012");
		jsonObject.put("hostLocation", "-2楼");
		return jsonObject.toString() + "\n";
	}

	public static String makeHostUpdateMsg() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "modify");
		jsonObject.put("type", "host");
		jsonObject.put("hostID", "3101012");
		jsonObject.put("hostLocation", "-3楼");
		return jsonObject.toString() + "\n";
	}

	public static String makeHostDeleteMsg() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "delete");
		jsonObject.put("type", "host");
		jsonObject.put("hostID", "3101012");
		return jsonObject.toString() + "\n";
	}

	public static String makeDeviceMsg() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "add");
		jsonObject.put("type", "device");
		jsonObject.put("deviceID", "1102010101");
		jsonObject.put("buildingID", "1010");
		jsonObject.put("deviceCoding", "LS");
		jsonObject.put("deviceModel", "XF");
		jsonObject.put("deviceType", "电气柜");
		jsonObject.put("manufacturingUnit", "川仪");
		jsonObject.put("productionDate", "2017-02-27");
		jsonObject.put("devicePersion", "张三");
		jsonObject.put("deviceTel", "123456789");
		jsonObject.put("deviceinstallDate", "2017-02-27");
		jsonObject.put("durableYears", "10年");
		jsonObject.put("deviceLocation", "-2楼");
		return jsonObject.toString() + "\n";
	}

	public static String makeDeviceUpdateMsg() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "modify");
		jsonObject.put("type", "device");
		jsonObject.put("deviceID", "1102010101");
		jsonObject.put("buildingID", "1010");
		jsonObject.put("deviceCoding", "GS");
		jsonObject.put("deviceModel", "GF");
		return jsonObject.toString() + "\n";
	}

	public static String makeDeviceDeleteMsg() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "delete");
		jsonObject.put("type", "device");
		jsonObject.put("deviceID", "1102010101");
		return jsonObject.toString() + "\n";
	}

	public static String addNodeMsg() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject host = new JSONObject();
		host.put("unitID", "510101");
		host.put("can1", 1);
		host.put("can2", 2);
		host.put("can3", 1);
		host.put("can4", 0);
		host.put("can5", 1);
		host.put("can6", 3);
		host.put("updatetime", "2017-6-27 18:48:00");
		jsonArray.add(host);
		jsonObject.put("nodenum", jsonArray);
		jsonObject.put("len", "00000");
		jsonObject.put("command", "powerstate");
		int len = jsonObject.toString().length();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 5 - String.valueOf(len).length(); i++) {
			buffer.append("0");
		}
		buffer.append(len);
		jsonObject.put("len", buffer.toString());
		return jsonObject.toString();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			Thread thread = new Thread(new SendDataClient());
			thread.start();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// System.out.println(makeSensorString());
		Socket socket = null;
		int port = 9010;
		OutputStream socketOut = null;
		BufferedReader br = null;
		try {
			String res = "";
			socket = new Socket("172.20.33.237", port);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg = makeNodeUpdateMsg();
			System.out.println("msg" + msg);
			socketOut = socket.getOutputStream();
			socketOut.write(msg.getBytes());
			socketOut.flush();
			// 接收服务器的反馈
			while ((res = br.readLine()) != null) {
				System.out.println(res);
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (socketOut != null) {
				try {
					socketOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
