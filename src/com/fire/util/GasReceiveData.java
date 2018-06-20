package com.fire.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fire.dao.NodeManagerDao;
import com.fire.dao.SaveFaultDao;
import com.fire.dao.SaveFileData;

public class GasReceiveData {

	/**
	 * @param args
	 */
	static List<JSONObject> datapackJsonLists = new ArrayList<JSONObject>(); // 数据包缓存
	static List<Integer> datapackLengthList = new ArrayList<Integer>(); // 数据包长度缓存
	static List<JSONObject> saveJsonLists = new ArrayList<JSONObject>(); // 数据文件存储
	static List<JSONObject> judgefaultJsonLists = new ArrayList<JSONObject>(); // 异常更新数据缓存
	static List<JSONObject> faultHistoryJsonLists = new ArrayList<JSONObject>(); // 历史异常数据缓存（包括预警和故障）
	static List<JSONObject> faultBaseJsonLists = new ArrayList<JSONObject>(); // 实时异常存数据库数据缓存(包括预警、故障、报警)
	static List<JSONObject> HostHisrotyJsonLists = new ArrayList<JSONObject>(); // 历史报警信息的缓存
	public static volatile boolean unpackThreadKZ = false; // 确定是否需要启动解包
	public static boolean recieveBool = true; // 是否接收数据
	public static boolean saveBool = false; // 是否存储数据文件
	public static boolean saveBaseBool = false; // 是否存储数据库
	public static boolean refreashFault = false;
	static Thread refreashParamThread = null; // 更新实时参数
	static Thread unpackThread = null; // 解包线程
	static Thread recieveDataThread = null; // 接收包线程
	static Thread saveFileDataThread = null; // 存储数据文件线程
	static Thread saveBaseDataThread = null; // 存储数据库线程
	static Thread updateFaultThread = null; // 更新异常信息
	public static Object recievelock = new Object(); // 接收和解包缓存锁
	public static Object saveFilelock = new Object();
	public static Object saveBaselock = new Object();
	public static Object refreshParamLock = new Object();
	public static Object updateFaultLock = new Object();
	public static Object readFaultLock = new Object(); // 读取异常信息的锁
	public static Object readParaLock = new Object(); // 读取实时参数的锁
	static SaveFileData saveFileData = new SaveFileData();
	static SaveFaultDao saveFaultDao = new SaveFaultDao();
	static JSONArray nodeDataArray = new JSONArray();
	static JSONArray mainJsonArray = new JSONArray();
	static String systemName = "消防气体---";
	static String orgIp;
	static NodeManagerDao nodeDao = new NodeManagerDao();
	static Log gasReceive_log = LogFactory.getLog(GasReceiveData.class);
	public static String[] gasTableHeaderb = new String[] { "设备编号", "设备位置", "SF6浓度", "O2浓度", "温度", "湿度", "更新时间" };
	public static String[] gasTableHeadera = new String[] { "设备编号", "设备位置", "密度", "压力", "微水", "温度", "更新时间" };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Client.client = new Client();
			/* CacheDataBase.main(null); */
			// //System.out.println("所有设备配置:" + CacheDataBase.deviceData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recieveDataThread = new Thread(new recieveData());
		recieveDataThread.start();

		unpackThread = new Thread(new unpack());
		unpackThread.start();

		refreashParamThread = new Thread(new refreashParam());
		refreashParamThread.start();

		updateFaultThread = new Thread(new updateFault());
		updateFaultThread.start();

		saveBaseDataThread = new Thread(new saveFaultData());
		saveBaseDataThread.start();

		saveFileDataThread = new Thread(new saveFileData());
		saveFileDataThread.start();
	}

	static synchronized boolean unpackPackage(JSONObject currentPackJsonObject, Integer len) {
		if (currentPackJsonObject.getInt("len") != len) {
			// System.out.println(systemName + "数据包格式不对");
			return false;
		} else {
			String state = currentPackJsonObject.getString("command");
			if (state.equals("currentstate")) {
				JSONObject saveJsonObject = new JSONObject();
				JSONObject judgefaultJsonObject = new JSONObject();
				nodeDataArray = currentPackJsonObject.getJSONArray("devicestate");
				mainJsonArray = currentPackJsonObject.getJSONArray("mainstate");
				saveJsonObject.put("data", nodeDataArray);
				judgefaultJsonObject.put("data", nodeDataArray);
				judgefaultJsonObject.put("host", mainJsonArray);
				saveBool = true;
				synchronized (saveFilelock) {
					saveJsonLists.add(saveJsonObject);
					if (saveFileDataThread.getState().toString().equals("TERMINATED")) {
						saveFileDataThread = new Thread(new saveFileData());
						saveFileDataThread.start();
					}
					saveFilelock.notify();
				}
				refreashFault = true;
				synchronized (updateFaultLock) {
					judgefaultJsonLists.add(judgefaultJsonObject);
					if (updateFaultThread.getState().toString().equals("TERMINATED")) {
						updateFaultThread = new Thread(new updateFault());
						updateFaultThread.start();
					}
					updateFaultLock.notify();
				}
				synchronized (refreshParamLock) {
					if (refreashParamThread.getState().toString().equals("TERMINATED")) {
						refreashParamThread = new Thread(new refreashParam());
						refreashParamThread.start();
					}
					if (!refreashParamThread.isAlive()) {
						refreashParamThread.start();
					} else {
						if (refreashParamThread.getState().toString().equals("WAITING")) {
							refreshParamLock.notify();
						}
					}
				}
			}
			return true;
		}
	}

	static class recieveData implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			/*
			 * 单播方式获得数据包，后期在考虑组播
			 */
			UdpServerSocket serverSocket = null;
			try {
				serverSocket = new UdpServerSocket(CacheDataBase.basicConfig.getString("host"), CacheDataBase.basicConfig.getInt("gasport"));
				while (true) {
					String dataString = serverSocket.receive();
					// //System.out.println(dataString);
					orgIp = serverSocket.getOrgIp();
					JSONObject datajsonObject = JSONObject.fromObject(dataString);
					int len = datajsonObject.toString().length();
					if (!recieveBool) {
						synchronized (recievelock) {
							recievelock.wait();
						}
					} else {
						synchronized (recievelock) {
							datapackJsonLists.add(datajsonObject);
							datapackLengthList.add(len);
							unpackThreadKZ = true;
							// System.out.println(systemName + "数据缓存大小：" +
							// datapackJsonLists.size());
							if (unpackThread.getState().toString().equals("TERMINATED")) {
								unpackThread = new Thread(new unpack());
								unpackThread.start();
							}
							recievelock.notify();
						}
					}

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}

	static class unpack implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				JSONObject currentData = new JSONObject();
				Integer len = 0;
				if (!unpackThreadKZ) {
					synchronized (recievelock) {
						try {
							// System.out.println(systemName + "解包线程未开启");
							recievelock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					if (datapackJsonLists.size() > 0) {
						synchronized (recievelock) {
							currentData = datapackJsonLists.get(0);
							len = datapackLengthList.get(0);
							datapackJsonLists.remove(0);
							datapackLengthList.remove(0);
							// System.out.println(systemName + "缓存剩余数据包：" +
							// datapackJsonLists.size());
						}
					}
					unpackPackage(currentData, len);
					synchronized (recievelock) {
						if (datapackJsonLists.size() <= 0) {
							try {
								// System.out.println(systemName +
								// "无数据包,解包线程挂起");
								recievelock.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}

	}

	static class refreashParam implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				synchronized (refreshParamLock) {
					try {
						refreshParamLock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// System.out.println("node"+nodeDataArray);
					for (int i = 0; i < nodeDataArray.size(); i++) {
						JSONObject jsonObject = nodeDataArray.getJSONObject(i);
						String nodeId = jsonObject.getString("device");
						if (!CacheDataBase.subSystemNodeData.containsKey(nodeId)) {
							// gasReceive_log.error("数据库中无此节点:" + nodeId);
							break;
						}
						String buildId = nodeId.substring(1, 5);
						String canNo = nodeId.substring(0, 7);
						String flag = nodeId.substring(nodeId.length() - 1, nodeId.length());
						List<String> list = new ArrayList<String>();
						if (flag.equals("b")) {
							list.add(nodeId);
							list.add(CacheDataBase.subSystemNodeData.get(nodeId).getString("nodeLocation"));
							list.add(jsonObject.getString("wdens"));
							list.add(jsonObject.getString("wpres"));
							list.add(jsonObject.getString("wsf6"));
							list.add(jsonObject.getString("wtemp"));
							list.add(jsonObject.getString("updatetime").replace("T", " "));
							list.add(jsonObject.getString("faulttype"));
							jsonObject.remove("faulttype");
							if (CacheDataBase.gasMainValues.containsKey(buildId)) {
								if (CacheDataBase.gasMainValues.get(buildId).containsKey(canNo)) {
									CacheDataBase.gasMainValues.get(buildId).get(canNo).put(nodeId, list);
								} else {
									JSONObject jObject = new JSONObject();
									jObject.put(nodeId, list);
									CacheDataBase.gasMainValues.get(buildId).put(canNo, jObject);
								}
							} else {
								JSONObject jObject = new JSONObject();
								jObject.put(nodeId, list);
								ConcurrentHashMap<String, JSONObject> map = new ConcurrentHashMap<String, JSONObject>();
								map.put(canNo, jObject);
								CacheDataBase.gasMainValues.put(buildId, map);
							}
						} else if (flag.equals("a")) {
							list.add(nodeId);
							list.add(CacheDataBase.subSystemNodeData.get(nodeId).getString("nodeLocation"));
							list.add(jsonObject.getString("sf6"));
							list.add(jsonObject.getString("o2"));
							list.add(jsonObject.getString("temp"));
							list.add(jsonObject.getString("humi"));
							list.add(jsonObject.getString("updatetime").replace("T", " "));
							list.add(jsonObject.getString("faulttype"));
							jsonObject.remove("faulttype");
							if (CacheDataBase.gasMainValues.containsKey(buildId)) {
								if (CacheDataBase.gasMainValues.get(buildId).containsKey(canNo)) {
									CacheDataBase.gasMainValues.get(buildId).get(canNo).put(nodeId, list);
								} else {
									JSONObject jObject = new JSONObject();
									jObject.put(nodeId, list);
									CacheDataBase.gasMainValues.get(buildId).put(canNo, jObject);
								}
							} else {
								JSONObject jObject = new JSONObject();
								jObject.put(nodeId, list);
								ConcurrentHashMap<String, JSONObject> map = new ConcurrentHashMap<String, JSONObject>();
								map.put(canNo, jObject);
								CacheDataBase.gasMainValues.put(buildId, map);
							}
						}
					}
				}
			}

		}
	}

	static class saveFileData implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				JSONObject saveCurrent = new JSONObject();
				if (!saveBool) {
					synchronized (saveFilelock) {
						try {
							// System.out.println(systemName + "存数据文件未开启");
							saveFilelock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				synchronized (saveFilelock) {
					if (saveJsonLists.size() <= 0) {
						try {
							// System.out.println(systemName + "存数据文件挂起");
							saveFilelock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						saveCurrent = saveJsonLists.get(0);
						saveJsonLists.remove(0);
						// System.out.println(systemName + "数据文件剩余包：" +
						// saveJsonLists.size());
					}
				}
				try {
					saveFileData.saveFile(saveCurrent.getJSONArray("data"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				synchronized (saveFilelock) {
					if (saveJsonLists.size() <= 0) {
						try {
							// System.out.println(systemName + "存数据文件挂起");
							saveFilelock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (saveJsonLists.size() > 10000) {
						saveJsonLists.clear();
						try {
							saveFilelock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

	static class saveFaultData implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				JSONObject saveFaultCurrent = null;
				JSONObject saveFaultHistory = null;
				JSONObject saveHostHistory = null;
				if (!saveBaseBool) {
					synchronized (saveBaselock) {
						try {
							// System.out.println(systemName + "存数据库线程未开启。。。");
							saveBaselock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				synchronized (saveBaselock) {
					if (faultBaseJsonLists.size() > 0) {
						saveFaultCurrent = faultBaseJsonLists.get(0);
						faultBaseJsonLists.remove(0);
						// System.out.println(systemName + "存所有实时异常剩余数据包" +
						// faultBaseJsonLists.size());
					}
					if (faultHistoryJsonLists.size() > 0) {
						saveFaultHistory = faultHistoryJsonLists.get(0);
						faultHistoryJsonLists.remove(0);
						// System.out.println(systemName + "存历史预警、故障剩余数据包：" +
						// faultHistoryJsonLists.size());
					}
					if (HostHisrotyJsonLists.size() > 0) {
						saveHostHistory = HostHisrotyJsonLists.get(0);
						HostHisrotyJsonLists.remove(0);
					}
					if (faultBaseJsonLists.size() <= 0 && faultHistoryJsonLists.size() <= 0 && HostHisrotyJsonLists.size() <= 0) {
						try {
							saveBaselock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				saveFileData.saveNodeFaultRecord(saveFaultHistory);
				saveFileData.saveHostFaultRecord(saveHostHistory);
				saveFaultDao.saveFaultCurrent(saveFaultCurrent);
				synchronized (saveBaselock) {
					if (faultBaseJsonLists.size() <= 0 && faultHistoryJsonLists.size() <= 0 && HostHisrotyJsonLists.size() <= 0) {
						try {
							// System.out.println(systemName + "存数据库线程挂起。。。");
							saveBaselock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (faultBaseJsonLists.size() > 10000) {
						faultBaseJsonLists.clear();
					} else if (faultHistoryJsonLists.size() > 10000) {
						faultHistoryJsonLists.clear();
					} else if (HostHisrotyJsonLists.size() > 10000) {
						HostHisrotyJsonLists.clear();
					}
				}

			}
		}
	}

	static class updateFault implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {

				JSONObject judgeFaultJsonObject = null;
				if (!refreashFault) {
					synchronized (updateFaultLock) {
						try {
							// System.out.println(systemName + "异常更新线程未开启。。。");
							updateFaultLock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				synchronized (updateFaultLock) {
					if (judgefaultJsonLists.size() <= 0) {
						try {
							// System.out.println(systemName + "异常更新线程挂起。。。");
							updateFaultLock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						judgeFaultJsonObject = judgefaultJsonLists.get(0);
						judgefaultJsonLists.remove(0);
						// System.out.println(systemName + "判断异常剩余包：" +
						// judgefaultJsonLists.size());
					}
				}
				judgeFaultParam(judgeFaultJsonObject);
				synchronized (updateFaultLock) {
					if (judgefaultJsonLists.size() <= 0) {
						try {
							// System.out.println(systemName + "异常更新线程挂起。。。");
							updateFaultLock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (judgefaultJsonLists.size() > 10000) {
						judgefaultJsonLists.clear();
						try {
							updateFaultLock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
		}

	}

	static void judgeFaultParam(JSONObject jsonObject) {
		JSONArray nodeArray = jsonObject.getJSONArray("data");
		JSONArray mainArray = jsonObject.getJSONArray("host");
		for (int i = 0; i < nodeArray.size(); i++) {
			JSONObject dataJsonObject = nodeArray.getJSONObject(i);
			if (!dataJsonObject.getString("faulttype").equals("0")) {
				String faultType = dataJsonObject.getString("faulttype");
				String deviceID = dataJsonObject.getString("device");
				String time = dataJsonObject.getString("updatetime").replace("T", " ");
				String buildId = deviceID.substring(1, 5);
				StringBuffer faultId = new StringBuffer();
				faultId.append(faultType);
				faultId.append("w");
				faultId.append(deviceID);
				JSONObject faultjsonObject = new JSONObject();
				String faultID = faultId.toString();
				faultjsonObject.put("faultID", faultID);
				faultjsonObject.put("deviceID", deviceID);
				faultjsonObject.put("faultCategory", "预警");
				faultjsonObject.put("faultType", faultType);
				faultjsonObject.put("faultTime", time);
				faultjsonObject.put("faultLocation", CacheDataBase.subSystemNodeData.get(deviceID).getString("nodeLocation"));
				// faultjsonObject.put("faultLocation",
				// CacheDataBase.currentNodeData.get(deviceID).get("nodeLocation"));
				// faultjsonObject.put("faultClass", "n"); // 节点异常
				CacheDataBase.subSystemNodeState.put(deviceID, 1);
				// CacheDataBase.subSystemHostState.put(deviceID.substring(0,
				// 6), 1);
				saveBaseBool = true;
				synchronized (saveBaselock) {
					faultHistoryJsonLists.add(faultjsonObject);
					if (saveBaseDataThread.getState().toString().equals("TERMINATED")) {
						saveBaseDataThread = new Thread(new saveFaultData());
						saveBaseDataThread.start();
					}
					saveBaselock.notify();
				}
				if (CacheDataBase.warningParam.containsKey(buildId)) {
					JSONObject warningJsonObject = CacheDataBase.warningParam.get(buildId);
					if (!warningJsonObject.containsKey(faultID)) {
						warningJsonObject.put(faultID, faultjsonObject);
						JSONObject temp = new JSONObject();
						temp.put(faultID, faultjsonObject);
						synchronized (saveBaselock) {
							faultBaseJsonLists.add(temp);
							saveBaselock.notify();
						}
						synchronized (readFaultLock) {
							CacheDataBase.warningParam.put(buildId, warningJsonObject);
						}
					}
				} else {
					JSONObject warningJsonObject = new JSONObject();
					warningJsonObject.put(faultID, faultjsonObject);
					synchronized (saveBaselock) {
						JSONObject temp = new JSONObject();
						temp.put(faultID, faultjsonObject);
						faultBaseJsonLists.add(temp);
						saveBaselock.notify();
					}
					synchronized (readFaultLock) {
						CacheDataBase.warningParam.put(buildId, warningJsonObject);
					}
				}

			} else {
				String deviceID = dataJsonObject.getString("device");
				CacheDataBase.subSystemNodeState.put(deviceID, 0);
				// CacheDataBase.subSystemHostState.put(deviceID.substring(0,
				// 6), 0);
			}
		}
		for (int i = 0; i < mainArray.size(); i++) {
			JSONObject hostJsonObject = mainArray.getJSONObject(i);
			String faultType = hostJsonObject.getString("faulttype");
			if (!faultType.equals("0")) {
				String hostID = hostJsonObject.getString("main");
				String time = hostJsonObject.getString("updatetime").replace("T", " ");
				String buildId = hostID.substring(1, 5);
				StringBuffer faultId = new StringBuffer();
				faultId.append(faultType);
				faultId.append("h");
				faultId.append(hostID);
				JSONObject hostfaultjsonObject = new JSONObject();
				String faultID = faultId.toString();
				hostfaultjsonObject.put("faultID", faultID);
				hostfaultjsonObject.put("deviceID", hostID);
				hostfaultjsonObject.put("faultCategory", "主机异常");
				hostfaultjsonObject.put("faultType", faultType);
				hostfaultjsonObject.put("faultTime", time);
				/*
				 * hostfaultjsonObject.put("faultLocation",CacheDataBase.
				 * currentHostData.get(hostID).get("nodeLocation"));
				 */
				hostfaultjsonObject.put("faultLocation", CacheDataBase.subSystemHostData.get(hostID).getString("hostLocation"));
				// hostfaultjsonObject.put("faultLocation", "测试");
				// hostfaultjsonObject.put("faultClass", "h"); // 主机异常
				// System.out.print("异常的主机信息:" + hostfaultjsonObject);
				CacheDataBase.subSystemHostState.put(hostID, 1);
				saveBaseBool = true;
				synchronized (saveBaselock) {
					HostHisrotyJsonLists.add(hostfaultjsonObject);
					if (saveBaseDataThread.getState().toString().equals("TERMINATED")) {
						saveBaseDataThread = new Thread(new saveFaultData());
						saveBaseDataThread.start();
					}
					saveBaselock.notify();
				}
				if (CacheDataBase.hostFaultData.containsKey(buildId)) {
					JSONObject hostFaultJson = CacheDataBase.hostFaultData.get(buildId);
					if (!hostFaultJson.containsKey(faultID)) {
						hostFaultJson.put(faultID, hostfaultjsonObject);
						JSONObject temp = new JSONObject();
						temp.put(faultID, hostfaultjsonObject);
						synchronized (saveBaselock) {
							faultBaseJsonLists.add(temp);
							saveBaselock.notify();
						}
						synchronized (readFaultLock) {
							CacheDataBase.hostFaultData.put(buildId, hostFaultJson);
						}
					}
				} else {
					JSONObject hostFaultJsonObject = new JSONObject();
					hostFaultJsonObject.put(faultID, hostfaultjsonObject);
					synchronized (saveBaselock) {
						JSONObject temp = new JSONObject();
						temp.put(faultID, hostfaultjsonObject);
						faultBaseJsonLists.add(temp);
						saveBaselock.notify();
					}
					synchronized (readFaultLock) {
						CacheDataBase.hostFaultData.put(buildId, hostFaultJsonObject);
					}
				}
			} else {
				String hostID = hostJsonObject.getString("main");
				CacheDataBase.subSystemHostState.put(hostID, 0);
			}
		}
	}
}
