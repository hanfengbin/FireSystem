package com.fire.mina;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.fire.dao.NodeManagerDao;
import com.fire.util.CacheDataBase;

public class NodeOrHostHandler extends IoHandlerAdapter {
	private static final Log inHandler_log = LogFactory.getLog(NodeOrHostHandler.class);
	private NodeManagerDao nodeManagerDao = new NodeManagerDao();

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
		InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress();
		InetAddress address = socketAddress.getAddress();
		System.out.println("client ip:" + address.getHostAddress());
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
		System.out.println("io session:" + session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
		session.closeNow();
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);

	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		byte[] arr = (byte[]) message;
		String receive = new String(arr, "UTF-8");
		JSONObject jsonObject = null;
		JSONObject resultJsonObject = new JSONObject();
		try {
			jsonObject = JSONObject.fromObject(receive);
		} catch (Exception e) {
			// TODO: handle exception
			inHandler_log.error("数据包格式错误");
			resultJsonObject.put("code", "1");
		}
		if (jsonObject.getInt("len") != receive.length()) {
			inHandler_log.error("数据包长度不一致");
			resultJsonObject.put("code", "2");
		} else {
			resultJsonObject.put("code", "3");
			JSONArray array = jsonObject.getJSONArray("nodenum");
			for (int i = 0; i < array.size(); i++) {
				JSONObject nodeUpdate = array.getJSONObject(i);
				String hostID = nodeUpdate.getString("unitID");
				String buildingID = hostID.substring(1, 5);
				String updateTime = nodeUpdate.getString("updatetime").replace("T", " ");
				long[] cans = new long[] { nodeUpdate.getLong("can1"), nodeUpdate.getLong("can2"), nodeUpdate.getLong("can3"), nodeUpdate.getLong("can4"),
						nodeUpdate.getLong("can5"), nodeUpdate.getLong("can6") };
				long[] nums = nodeManagerDao.getHostCanNums(hostID);
				for (int j = 0; j < nums.length; j++) {
					if (cans[j] > nums[j]) {
						resultJsonObject.put(j + 1, "success");
						for (long j2 = nums[j] + 1; j2 <= cans[j]; j2++) {
							String nodeID = hostID + (j + 1) + appendNodeId(j2);
							try {
								nodeManagerDao.insertNodeBatch(nodeID, buildingID, hostID, j + 1, updateTime);
							} catch (Exception e) {
								// TODO: handle exception
								inHandler_log.error(e);
								resultJsonObject.put(j + 1, "fail");
							}
						}
					} else if (cans[j] < nums[i]) {
						inHandler_log.error("错误的删除或者数据不匹配" + hostID + j + ":" + cans[j] + "-" + nums[j]);
					}
				}
			}
			CacheDataBase.loadSubSystemDevice();
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		inHandler_log.debug("send message to " + session + ":" + message);
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.inputClosed(session);
	}

	public String appendNodeId(long num) {
		if (num < 10) {
			return "00" + num;
		} else if (num < 100) {
			return "0" + num;
		} else {
			return String.valueOf(num);
		}
	}
}
