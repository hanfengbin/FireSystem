package com.fire.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fire.dao.FireManagerDao;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
	private static final Log severHandler_log = LogFactory.getLog(DiscardServerHandler.class);
	private FireManagerDao managerDao = new FireManagerDao();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println("receive:" + msg.toString());
		/*
		 * ByteBuf in = (ByteBuf) msg; byte[] arr = null; while
		 * (in.isReadable()) { arr = new byte[in.readableBytes()];
		 * in.readBytes(arr); }
		 */
		String receive = new String(msg.toString().getBytes(), "UTF-8");
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
			severHandler_log.error(e);
			e.printStackTrace();
			returnMessage = "error";
		}
		ctx.writeAndFlush(returnMessage);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("-------------连接关闭-------------");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("-------------连接开启-------------" + ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println("send message");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}

}
