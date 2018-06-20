package com.fire.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import com.fire.dao.FireManagerDao;

public class ReceiveDataServer {
	static ServerSocket serverSocket;
	static ExecutorService service = Executors.newCachedThreadPool();
	static FireManagerDao managerDao = new FireManagerDao();

	public static PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	public static BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn, "UTF-8"));
	}

	static class ServerLinkThread implements Runnable {
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					Thread handlerThread = new Thread(new HandlerServer(socket));
					service.execute(handlerThread);
					// handlerThread.start();
					System.out.println("连接开始");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	static class HandlerServer implements Runnable {
		Socket socket;
		BufferedReader br = null;
		PrintWriter out = null;

		HandlerServer(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				socket.setSoTimeout(10000);
				br = getReader(socket);
				out = getWriter(socket);
				String msg = "";
				while ((msg = br.readLine()) != null) {
					System.out.println(msg);
					String returnMessage = "";
					JSONObject jsonObject = JSONObject.fromObject(msg);
					String state = jsonObject.getString("command");
					String type = jsonObject.getString("type");
					try {
						if (type.equals("node")) {
							String nodeID = jsonObject.getString("nodeID");
							if (state.equals("add")) {
								if (managerDao.selectNodeById(nodeID)) {
									returnMessage = "existNode";
								} else {
									managerDao.addNode(jsonObject);
									returnMessage = "success";
								}
							} else if (state.equals("modify")) {
								if (!managerDao.selectNodeById(nodeID)) {
									returnMessage = "noNode";
								} else {
									managerDao.updateNode(jsonObject);
									returnMessage = "success";
								}
							} else if (state.equals("delete")) {
								if (!managerDao.selectNodeById(nodeID)) {
									returnMessage = "noNode";
								} else {
									managerDao.deleteNode(jsonObject);
									returnMessage = "success";
								}
							}
						} else if (type.equals("host")) {
							String hostID = jsonObject.getString("hostID");
							if (state.equals("add")) {
								if (managerDao.selectHostById(hostID)) {
									returnMessage = "existHost";
								} else {
									managerDao.addHost(jsonObject);
									returnMessage = "success";
								}
							} else if (state.equals("modify")) {
								if (!managerDao.selectHostById(hostID)) {
									returnMessage = "noHost";
								} else {
									managerDao.updateHost(jsonObject);
									returnMessage = "success";
								}
							} else if (state.equals("delete")) {
								if (!managerDao.selectHostById(hostID)) {
									returnMessage = "noHost";
								} else {
									managerDao.deleteHost(jsonObject);
									returnMessage = "success";
								}

							}

						} else if (type.equals("device")) {
							String deviceID = jsonObject.getString("deviceID");
							if (state.equals("add")) {
								if (managerDao.selectDeviceById(deviceID)) {
									returnMessage = "existDevice";
								} else {
									managerDao.addDevice(jsonObject);
									returnMessage = "success";
								}
							} else if (state.equals("modify")) {
								if (!managerDao.selectDeviceById(deviceID)) {
									returnMessage = "noDevice";
								} else {
									managerDao.updateDevice(jsonObject);
									returnMessage = "success";
								}
							} else if (state.equals("delete")) {
								if (!managerDao.selectDeviceById(deviceID)) {
									returnMessage = "noDevice";
								} else {
									managerDao.deleteDevice(jsonObject);
									returnMessage = "success";
								}
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						returnMessage = "error";
					}
					out.println(returnMessage);
					out.flush();
				}
			} catch (Exception e) {
				// TODO: handle exception=
				e.printStackTrace();
			} finally {
				try {
					System.out.println("socket断开");
					socket.close();
					br.close();
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] arg0) throws IOException {
		serverSocket = new ServerSocket(9010);
		Thread serverThread = new Thread(new ServerLinkThread());
		serverThread.start();
	}
}
