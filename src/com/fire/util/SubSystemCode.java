package com.fire.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

public class SubSystemCode {
	public static Map<String, String[]> subSystemCodeMap = new HashMap<String, String[]>();
	static {
		Properties properties = new Properties();
		InputStream inStream = DbConn.class.getResourceAsStream("faultType.properties");
		try {
			properties.load(inStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(properties);
		Enumeration<Object> enumeration = properties.keys();
		while (enumeration.hasMoreElements()) {
			String system = (String) enumeration.nextElement();
			String[] faults = properties.getProperty(system).toString().split(",");
			subSystemCodeMap.put(system, faults);
		}
	}

	public JSONObject getFaultTypeJsonObject(String system, int type) {
		if (system.equals("power")) {
			return getPowerFaultTypeJsonObject(system, type);
		} else if (system.equals("gas1") || system.equals("gas2")) {
			return getGasFaultTypeJsonObject(system, type);
		} else if (system.equals("current")) {
			return getCurrentFaultTypeJsonObject(system, type);
		}
		return new JSONObject();
	}

	public JSONObject getCurrentFaultTypeJsonObject(String system, int type) {
		JSONObject jsonObject = new JSONObject();
		String binaryString = Integer.toBinaryString(type);
		String[] faults = subSystemCodeMap.get(system);
		int[] data = new int[8];
		int pos = 8 - binaryString.length();
		for (int i = 0; i < binaryString.length(); i++, pos++) {
			data[pos] = binaryString.charAt(i) - '0';
		}
		jsonObject.put("tableHeader", faults);//得到表头
		JSONObject dataJsonObject = new JSONObject();
		dataJsonObject.put(1 + "", data);
		jsonObject.put("tableContent", dataJsonObject);//得到内容
		return jsonObject;
	}

	public JSONObject getPowerFaultTypeJsonObject(String system, int type) {
		String binaryString = Integer.toBinaryString(type);
		JSONObject jsonObject = new JSONObject();
		String[] faults = subSystemCodeMap.get(system);
		int[] data = new int[14];
		if (binaryString.length() > 14) {
			int len = binaryString.length() - 14;
			binaryString = binaryString.substring(len);
		}
		int pos = 14 - binaryString.length();
		for (int i = 0; i < binaryString.length(); i++, pos++) {
			data[pos] = binaryString.charAt(i) - '0';
		}
		jsonObject.put("tableHeader", faults);
		JSONObject jsonData = new JSONObject();
		jsonData.put(1 + "", data);
		jsonObject.put("tableContent", jsonData);
		return jsonObject;
	}

	public JSONObject getGasFaultTypeJsonObject(String system, int type) {
		JSONObject jsonObject = new JSONObject();
		String binaryString = Integer.toBinaryString(type >> 8);
		String[] faults = subSystemCodeMap.get(system);
		int[] data = new int[4];
		int pos = 4 - binaryString.length();
		for (int i = 0; i < binaryString.length(); i++, pos++) {
			data[pos] = binaryString.charAt(i) - '0';
		}
		jsonObject.put("tableHeader", faults);
		JSONObject dataJsonObject = new JSONObject();
		dataJsonObject.put(1 + "", data);
		jsonObject.put("tableContent", dataJsonObject);
		return jsonObject;
	}

	public String getCurrentFaultTypeString(String system, int type) {
		StringBuffer stringBuffer = new StringBuffer();
		String binaryString = Integer.toBinaryString(type);
		String[] faults = subSystemCodeMap.get(system);
		int[] data = new int[8];
		int pos = 8 - binaryString.length();
		for (int i = 0; i < binaryString.length(); i++, pos++) {
			data[pos] = binaryString.charAt(i) - '0';
		}
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 1) {
				stringBuffer.append(faults[i] + "、");
			}
		}
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		return stringBuffer.toString();
	}

	public String getPowerFaultTypeString(String system, int type) {
		String binaryString = Integer.toBinaryString(type);
		StringBuffer stringBuffer = new StringBuffer();
		String[] faults = subSystemCodeMap.get(system);
		int[] data = new int[14];
		if (binaryString.length() > 14) {
			int len = binaryString.length() - 14;
			binaryString = binaryString.substring(len);
		}
		int pos = 14 - binaryString.length();
		for (int i = 0; i < binaryString.length(); i++, pos++) {
			data[pos] = binaryString.charAt(i) - '0';
		}
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 1) {
				stringBuffer.append(faults[i] + "、");
			}
		}
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		return stringBuffer.toString();
	}

	public String getGasFaultTypeString(String system, int type) {
		StringBuffer stringBuffer = new StringBuffer();
		String binaryString = Integer.toBinaryString(type >> 8);
		String[] faults = subSystemCodeMap.get(system);
		int[] data = new int[4];
		int pos = 4 - binaryString.length();
		for (int i = 0; i < binaryString.length(); i++, pos++) {
			data[pos] = binaryString.charAt(i) - '0';
		}
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 1) {
				stringBuffer.append(faults[i] + "、");
			}
		}
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		return stringBuffer.toString();
	}

	public static void main(String[] arg0) {
		SubSystemCode faultCode = new SubSystemCode();
		/*
		 * System.out.println(faultCode.getFaultTypeJsonObject("current", 144));
		 * System.out.println(faultCode.getPowerFaultTypeJsonObject("power",
		 * 3072)); System.out.println(faultCode.getFaultTypeString("current",
		 * 144)); System.out.println(faultCode.getPowerFaultTypeString("power",
		 * 3072));
		 */
		System.out.println(faultCode.getGasFaultTypeJsonObject("gas1", 257));
		System.out.println(faultCode.getGasFaultTypeString("gas1", 257));
	}
}
