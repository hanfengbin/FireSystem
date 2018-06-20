package com.fire.test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fire.util.CacheDataBase;
import com.fire.util.Client;

public class CreateCurrentData {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Client.client = new Client();
		CacheDataBase.service.execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DecimalFormat df = new DecimalFormat("#.##");
				while (true) {
					JSONObject sendData = new JSONObject();
					JSONArray current = new JSONArray();
					Random random = new Random();
					for (int i = 0; i < 10; i++) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("current1", df.format(random.nextFloat()));
						jsonObject.put("current2", df.format(random.nextFloat()));
						jsonObject.put("current3", df.format(random.nextFloat()));
						jsonObject.put("current4", df.format(random.nextFloat()));
						jsonObject.put("temperature1", 10 + random.nextInt(10));
						jsonObject.put("temperature2", 10 + random.nextInt(10));
						jsonObject.put("temperature3", 10 + random.nextInt(10));
						jsonObject.put("temperature4", 10 + random.nextInt(10));
						jsonObject.put("faulttype", random.nextInt(5));
						jsonObject.put("device", "310101100" + (1 + random.nextInt(2)));
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						jsonObject.put("updatetime", simpleDateFormat.format(new Date()));
						current.add(jsonObject);
					}
					JSONArray mainArray = new JSONArray();
					for (int i = 0; i < 1; i++) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("main", "310101");
						jsonObject.put("faulttype", random.nextInt(2));
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						jsonObject.put("updatetime", simpleDateFormat.format(new Date()));
						mainArray.add(jsonObject);
					}
					sendData.put("len", "00000");
					sendData.put("command", "currentstate");
					sendData.put("devicestate", current);
					sendData.put("mainstate", mainArray);
					int len = sendData.toString().length();
					StringBuffer buffer = new StringBuffer();
					for (int i = 0; i < 5 - String.valueOf(len).length(); i++) {
						buffer.append("0");
					}
					buffer.append(len);
					sendData.put("len", buffer.toString());
					// System.out.println("current_data_content:" + sendData);
					try {
						Client.client.send(CacheDataBase.basicConfig.getString("host"), CacheDataBase.basicConfig.getInt("currentport"), sendData.toString()
								.getBytes());
						TimeUnit.SECONDS.sleep(2);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
}
