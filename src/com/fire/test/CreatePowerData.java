package com.fire.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fire.util.CacheDataBase;
import com.fire.util.Client;

public class CreatePowerData {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Client.client = new Client();
		CacheDataBase.service.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					JSONObject sendData = new JSONObject();
					JSONArray current = new JSONArray();
					Random random = new Random();
					for (int i = 0; i < 10; i++) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("Channelnum", 1 + random.nextInt(2));
						jsonObject.put("Threevoltagex", random.nextInt(2));
						jsonObject.put("Threevoltagey", random.nextInt(2));
						jsonObject.put("Threevoltagez", random.nextInt(2));
						jsonObject.put("Threecurrentx", random.nextInt(2));
						jsonObject.put("Threecurrenty", random.nextInt(2));
						jsonObject.put("Threecurrentz", random.nextInt(2));
						jsonObject.put("Activepower", random.nextInt(2));
						jsonObject.put("Reactivepower", random.nextInt(2));
						jsonObject.put("Powerfactor", random.nextInt(2));
						jsonObject.put("faulttype", random.nextInt(2));
						jsonObject.put("device", "510101100" + (1 + random.nextInt(2)));
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						jsonObject.put("updatetime", simpleDateFormat.format(new Date()));
						current.add(jsonObject);
					}
					JSONArray mainArray = new JSONArray();
					for (int i = 0; i < 5; i++) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("main", "510101");
						jsonObject.put("faulttype", random.nextInt(2));
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						jsonObject.put("updatetime", simpleDateFormat.format(new Date()));
						mainArray.add(jsonObject);
					}
					sendData.put("len", "00000");
					sendData.put("command", "powerstate");
					sendData.put("devicestate", current);
					sendData.put("mainstate", mainArray);
					int len = sendData.toString().length();
					StringBuffer buffer = new StringBuffer();
					for (int i = 0; i < 5 - String.valueOf(len).length(); i++) {
						buffer.append("0");
					}
					buffer.append(len);
					sendData.put("len", buffer.toString());
					//System.out.println(len);
					//System.out.println(sendData);
					try {
						Client.client.send(CacheDataBase.basicConfig.getString("host"), CacheDataBase.basicConfig.getInt("powerport"), sendData.toString().getBytes());
						TimeUnit.SECONDS.sleep(2);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}});
	}
}
