package com.fire.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SaveFileData {

	/**
	 * @param args
	 * @author zdh1911
	 * @throws IOException
	 */
	public void saveHostFaultRecord(JSONObject jsonObject) {
		if (jsonObject == null) {
			return;
		}
		String root = "";
		String deviceId = jsonObject.getString("deviceID");
		String system = deviceId.substring(0, 1);
		if (system.equals("3")) {
			root = "E:\\fireData\\FaultRecord\\CurrentFault\\hostFault";
		} else if (system.equals("4")) {
			root = "E:\\fireData\\FaultRecord\\FireDoorFault\\hostFault";
		} else if (system.equals("5")) {
			root = "E:\\fireData\\FaultRecord\\PowerFault\\hostFault";
		} else if (system.equals("6")) {
			root = "E:\\fireData\\FaultRecord\\GasFault\\hostFault";
		}
		String faultTime = jsonObject.getString("faultTime").replace('T', ' ');
		String faultCategory = jsonObject.getString("faultCategory");
		String faultType = jsonObject.getString("faultID");
		String buildId = deviceId.substring(1, 5);
		String hostId = deviceId;
		StringBuffer faultTypePath = new StringBuffer(root);
		faultTypePath.append("\\");
		faultTypePath.append(buildId);
		faultTypePath.append("\\");
		faultTypePath.append(hostId);
		if (faultCategory.equals("主机异常")) {
			faultCategory = "hostFault";
		}
		faultTypePath.append("\\");
		faultTypePath.append(faultCategory);
		faultTypePath.append("\\");
		faultTypePath.append(faultType);
		File faultTypeFile = new File(faultTypePath.toString());
		if (!faultTypeFile.exists()) {
			faultTypeFile.mkdirs();
		}
		String datePath = faultTypePath + "\\" + faultTime.split(" ")[0] + ".txt";
		File dateFile = new File(datePath);
		try {
			if (!dateFile.exists()) {
				dateFile.createNewFile();
			}
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(dateFile, true), "UTF-8");
			out.write(jsonObject.toString() + ",");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveNodeFaultRecord(JSONObject jsonObject) {
		if (jsonObject == null) {
			return;
		}
		String root = "";
		String deviceId = jsonObject.getString("deviceID");
		String system = deviceId.substring(0, 1);
		if (system.equals("3")) {
			root = "E:\\fireData\\FaultRecord\\CurrentFault\\nodeFault";
		} else if (system.equals("4")) {
			root = "E:\\fireData\\FaultRecord\\FireDoorFault\\nodeFault";
		} else if (system.equals("5")) {
			root = "E:\\fireData\\FaultRecord\\PowerFault\\nodeFault";
		} else if (system.equals("6")) {
			root = "E:\\fireData\\FaultRecord\\GasFault\\nodeFault";
		}
		String faultTime = jsonObject.getString("faultTime").replace('T', ' ');
		String faultCategory = jsonObject.getString("faultCategory");
		if (faultCategory.equals("预警")) {
			faultCategory = "warning";
		} else if (faultCategory.equals("主机异常")) {
			faultCategory = "hostFault";
		}
		String faultType = jsonObject.getString("faultID");
		String buildId = deviceId.substring(1, 5);
		StringBuffer faultTypePath = new StringBuffer(root);
		faultTypePath.append("\\");
		faultTypePath.append(buildId);
		faultTypePath.append("\\");
		faultTypePath.append(deviceId);
		faultTypePath.append("\\");
		faultTypePath.append(faultCategory);
		faultTypePath.append("\\");
		faultTypePath.append(faultType);
		File faultTypeFile = new File(faultTypePath.toString());
		if (!faultTypeFile.exists()) {
			faultTypeFile.mkdirs();
		}
		String datePath = faultTypePath + "\\" + faultTime.split(" ")[0] + ".txt";
		File dateFile = new File(datePath);
		try {
			if (!dateFile.exists()) {
				dateFile.createNewFile();
			}
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(dateFile, true), "UTF-8");
			out.write(jsonObject.toString() + ",");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveFaultRecord(JSONObject jsonObject) {
		if (jsonObject == null) {
			return;
		}
		String root = "E:\\fireData\\FaultRecord\\waterFault";
		String deviceId = jsonObject.getString("deviceID");
		String faultTime = jsonObject.getString("faultTime");
		String faultCategory = jsonObject.getString("faultCategory");
		String faultCode = jsonObject.getString("faultID");
		String buildId = deviceId.substring(1, 5);
		String buildPath = root + "\\" + buildId;
		File buildFile = new File(buildPath);
		if (!buildFile.exists()) {
			buildFile.mkdirs();
		}
		String devicePath = buildPath + "\\" + deviceId;
		File deviceFile = new File(devicePath);
		if (!deviceFile.exists()) {
			deviceFile.mkdir();
		}
		if (faultCategory.equals("预警")) {
			faultCategory = "warning";
		} else if (faultCategory.equals("故障")) {
			faultCategory = "trouble";
		} else if (faultCategory.equals("报警")) {
			faultCategory = "alarm";
		}
		String categoryPath = devicePath + "\\" + faultCategory;
		File categoryFile = new File(categoryPath);
		if (!categoryFile.exists()) {
			categoryFile.mkdir();
		}
		String faultCodePath = categoryPath + "\\" + faultCode;
		File faultCodeFile = new File(faultCodePath);
		if (!faultCodeFile.exists()) {
			faultCodeFile.mkdir();
		}
		String datePath = faultCodePath + "\\" + faultTime.split(" ")[0] + ".txt";
		File dateFile = new File(datePath);
		try {
			if (!dateFile.exists()) {
				dateFile.createNewFile();
			}
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(dateFile, true), "UTF-8");
			out.write(jsonObject.toString() + ",");
			out.close();
			/*
			 * FileWriter fileWriter = new FileWriter(dateFile, true);
			 * fileWriter.write(jsonObject.toString() + ",");
			 * fileWriter.close();
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveAlarmRecord(JSONObject jsonObject) {
		if (jsonObject == null) {
			return;
		}
		String root = "E:\\fireData\\FaultRecord\\alarmFault";
		String deviceId = jsonObject.getString("deviceID");
		String faultTime = jsonObject.getString("faultTime");
		String faultCategory = jsonObject.getString("faultCategory");
		String buildId = deviceId.substring(1, 5);
		String buildPath = root + "\\" + buildId;
		File buildFile = new File(buildPath);
		if (!buildFile.exists()) {
			buildFile.mkdirs();
		}
		String devicePath = buildPath + "\\" + deviceId;
		File deviceFile = new File(devicePath);
		if (!deviceFile.exists()) {
			deviceFile.mkdir();
		}
		if (faultCategory.equals("故障")) {
			faultCategory = "trouble";
		} else if (faultCategory.equals("火警")) {
			faultCategory = "alarm";
		}
		String categoryPath = devicePath + "\\" + faultCategory;
		File categoryFile = new File(categoryPath);
		if (!categoryFile.exists()) {
			categoryFile.mkdir();
		}
		String datePath = categoryPath + "\\" + faultTime.split(" ")[0] + ".txt";
		File dateFile = new File(datePath);
		try {
			if (!dateFile.exists()) {
				dateFile.createNewFile();
			}
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(dateFile, true), "UTF-8");
			out.write(jsonObject.toString() + ",");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void saveFile(JSONArray jsonArray) throws IOException {
		String root = "E:\\fireData";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String deviceId = jsonObject.getString("device");
			String system = deviceId.substring(0, 1);
			switch (system) {
			case "3":
				system = "FireCurrent";
				break;
			case "4":
				system = "FireDoor";
				break;
			case "5":
				system = "FirePower";
				break;
			case "6":
				system = "FireGas";
				break;
			default:
				break;
			}
			String buildId = deviceId.substring(1, 5);
			String datetime = "";
			if (jsonObject.containsKey("updatetime")) {
				datetime = jsonObject.getString("updatetime").replace('T', ' ');
			} else if (jsonObject.containsKey("Updatetime")) {
				datetime = jsonObject.getString("Updatetime").replace('T', ' ');
			}
			String date = datetime.split(" ")[0];
			String time = datetime.split(" ")[1];
			StringBuffer buildPath = new StringBuffer(root + "\\" + system + "\\" + buildId);
			File file = new File(buildPath.toString());
			if (!file.exists()) {
				file.mkdir();
			}
			StringBuffer hostPath = new StringBuffer(buildPath + "\\" + deviceId.substring(5, 6));
			File hostFile = new File(hostPath.toString());
			if (!hostFile.exists()) {
				hostFile.mkdir();
			}
			jsonObject.remove("device");
			jsonObject.remove("updatetime");
			if (system.equals("FirePower")) {
				String Channelnum = jsonObject.getString("Channelnum");
				for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext();) {
					String key = iterator.next();
					String arr = jsonObject.getString(key);
					JSONObject content = new JSONObject();
					content.put(time, arr);
					StringBuffer buffer = new StringBuffer();
					buffer.append(hostPath);
					buffer.append("\\");
					buffer.append(deviceId);
					writePowerFile(buffer.toString(), Channelnum, key, date, content.toString());
				}
			} else {
				for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext();) {
					String key = iterator.next();
					String arr = jsonObject.getString(key);
					JSONObject content = new JSONObject();
					content.put(time, arr);
					StringBuffer buffer = new StringBuffer();
					buffer.append(hostPath);
					buffer.append("\\");
					buffer.append(deviceId);
					writeFile(buffer.toString(), key, date, content.toString());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void saveFile(JSONObject jsonObject) throws IOException {
		String root = "E:\\fireData";
		String system = jsonObject.getString("system");
		JSONObject data = jsonObject.getJSONObject("data");
		String datetime = jsonObject.getString("time");
		String buildId = jsonObject.getString("buildId");
		String date = datetime.split(" ")[0];
		String time = datetime.split(" ")[1];
		File file = new File(root + "\\" + system + "\\" + buildId);
		if (!file.exists()) {
			file.mkdir();
		}
		for (Iterator<String> iterator = data.keys(); iterator.hasNext();) {
			String deviceId = iterator.next();
			String arr = data.getString(deviceId);
			JSONArray jsonArray = JSONArray.fromObject(arr);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject content = new JSONObject();
				content.put(time, jsonArray.get(i));
				StringBuffer buffer = new StringBuffer();
				buffer.append(root);
				buffer.append("\\");
				buffer.append(system);
				buffer.append("\\");
				buffer.append(buildId);
				buffer.append("\\");
				buffer.append(deviceId);
				writeFile(buffer.toString(), String.valueOf(i), date, content.toString());
			}
		}

		JSONObject contenJsonObject = new JSONObject();
		contenJsonObject.put(time, "");
	}

	public void saveAlarmFile(JSONObject jsonObject) throws IOException {
		String root = "E:\\fireData";
		String system = jsonObject.getString("System");
		String datetime = jsonObject.getString("Time");
		String buildId = jsonObject.getString("BuildId");
		String hostId = jsonObject.getString("fireEngine");
		String end = jsonObject.getString("End");
		String start = jsonObject.getString("Start");
		String txtname = start + "-" + end;
		String date = datetime.split(" ")[0];
		String time = datetime.split(" ")[1];
		File file = new File(root + "\\" + system + "\\" + buildId + "\\" + hostId + "\\" + txtname);
		if (!file.exists()) {
			file.mkdirs();
		}
		JSONObject content = new JSONObject();
		content.put(time, jsonObject.getJSONArray("Data"));
		StringBuffer buffer = new StringBuffer();
		buffer.append(root);
		buffer.append("\\");
		buffer.append(system);
		buffer.append("\\");
		buffer.append(buildId);
		buffer.append("\\");
		buffer.append(txtname);
		writeFile(buffer.toString(), date, content.toString());
		JSONObject contenJsonObject = new JSONObject();
		contenJsonObject.put(time, "");

	}

	public void writeFile(String deviceIdPath, String datepath, String content) throws IOException {

		File deviceFile = new File(deviceIdPath);
		if (!deviceFile.exists()) {
			deviceFile.mkdirs();
		}
		File file = new File(deviceIdPath + "\\" + datepath + ".txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8");
		out.write(content + ",");
		out.close();

	}

	public void writePowerFile(String deviceIdPath, String Channelnum, String param, String datepath, String content) throws IOException {
		File channelnumFile = new File(deviceIdPath + "\\" + Channelnum);
		if (!channelnumFile.exists()) {
			channelnumFile.mkdirs();
		}
		File paraFile = new File(deviceIdPath + "\\" + Channelnum + "\\" + param);
		if (!paraFile.exists()) {
			paraFile.mkdirs();
		}
		File file = new File(deviceIdPath + "\\" + Channelnum + "\\" + param + "\\" + datepath + ".txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8");
		out.write(content + ",");
		out.close();
	}

	public void writeFile(String deviceIdPath, String param, String datepath, String content) throws IOException {
		File deviceFile = new File(deviceIdPath);
		if (!deviceFile.exists()) {
			deviceFile.mkdirs();
		}
		File paraFile = new File(deviceIdPath + "\\" + param);
		if (!paraFile.exists()) {
			paraFile.mkdirs();
		}
		File file = new File(deviceIdPath + "\\" + param + "\\" + datepath + ".txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8");
		out.write(content + ",");
		out.close();
		/*
		 * FileWriter fileWriter = new FileWriter(file, true);
		 * fileWriter.write(content + ","); fileWriter.close();
		 */
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SaveFileData saveFileData = new SaveFileData();
		String str = "{'len':597,'buildId':'1010','system':'water','data':{'101':[0],'211':[0,0,0],'102':[0],'201':[0,0,0],'212':[0,0,0],'202':[0,0,0],'301':[0,0],'302':[0,0],'303':[0,0],'304':[0,0],'305':[0,0],'306':[0,0],'307':[0,0],'308':[0,0],'309':[0,0]},'time':'2016-10-21 15:45:38.0'}";
		JSONObject jsonObject = JSONObject.fromObject(str);
		saveFileData.saveFile(jsonObject);
	}

}
