package com.fire.mina;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import net.sf.json.JSONObject;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.fire.dao.FireManagerDao;

public class GasIoHandler extends IoHandlerAdapter {
	private FireManagerDao managerDao = new FireManagerDao();

	@Override
	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.inputClosed(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		String receive = message.toString();
		System.out.println("receive:" + receive);
		JSONObject jsonObject = null;
		String returnMessage = "";
		try {
			jsonObject = JSONObject.fromObject(receive);
			String state = jsonObject.getString("command");
			String type = jsonObject.getString("type");
			if (type.equals("node")) {
				String nodeID = jsonObject.getString("device");
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
		session.write(returnMessage);

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("send message:" + message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
		System.out.println("session close");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
		InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress();
		InetAddress address = socketAddress.getAddress();
		System.out.println("client ip:" + address.getHostAddress());
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
		session.closeNow();
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
		System.out.println(this + "---" + session + "------" + "session open");
	}

}
