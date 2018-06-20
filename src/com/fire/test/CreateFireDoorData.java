package com.fire.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fire.util.CacheDataBase;
import com.fire.util.Client;

public class CreateFireDoorData {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Client.client = new Client();
		CacheDataBase.service.execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<String> list = new ArrayList<String>();
				list.add("01");
				list.add("10");
				list.add("00");
				while (true) {
					JSONObject sendData = new JSONObject();
					JSONArray current = new JSONArray();
					Random random = new Random();
					for (int i = 0; i < 5; i++) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("Close1", random.nextInt(3));
						jsonObject.put("Close2", random.nextInt(3));
						jsonObject.put("Magnetic1", random.nextInt(3));
						jsonObject.put("Magnetic2", random.nextInt(3));
						jsonObject.put("Release1", random.nextInt(3));
						jsonObject.put("Release2", random.nextInt(3));
						jsonObject.put("Status", list.get(random.nextInt(3)));
						jsonObject.put("Temper", random.nextInt(20));
						jsonObject.put("device",
								"410101100" + (1 + random.nextInt(2)));
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd hh:mm:ss");
						jsonObject.put("Updatetime",
								simpleDateFormat.format(new Date()));
						current.add(jsonObject);
					}
					JSONArray mainArray = new JSONArray();

					JSONObject jsonObject = new JSONObject();
					jsonObject.put("main", "410101");
					jsonObject.put("faulttype", random.nextInt(2));
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					jsonObject.put("Updatetime",
							simpleDateFormat.format(new Date()));
					mainArray.add(jsonObject);

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
				//	System.out.println(len);
					//System.out.println(sendData);
					try {
						Client.client.send(CacheDataBase.basicConfig.getString("host"), CacheDataBase.basicConfig.getInt("doorport"), sendData
								.toString().getBytes());
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
