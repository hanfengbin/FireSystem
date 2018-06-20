package com.fire.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	/**
	 * @param args
	 */

	private DatagramSocket ds = null;
	static Thread recieveThread = null;
	public static Client client = null;
	static UdpServerSocket udpServerSocket = null;

	/**
	 * 
	 * 
	 * @throws Exception
	 */
	public Client() throws Exception {
		ds = new DatagramSocket();
		// socket=dChannel.socket();
	}

	/**
	 * 
	 * 
	 * @param timeout
	 * 
	 * @throws Exception
	 */
	public final void setSoTimeout(final int timeout) throws Exception {
		ds.setSoTimeout(timeout);
	}

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public final int getSoTimeout() throws Exception {
		return ds.getSoTimeout();
	}

	public final DatagramSocket getSocket() {
		return ds;
	}

	/**
	 * 
	 * 
	 * @param host
	 * 
	 * @param port
	 * 
	 * @param bytes
	 * 
	 * @return
	 * @throws IOException
	 */
	public final DatagramPacket send(final String host, final int port, final byte[] bytes) throws IOException {
		DatagramPacket dp = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(host), port);
		ds.send(dp);
		return dp;
	}

	/**
	 * 
	 */
	public final void close() {
		try {
			ds.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		/*
		 * Client.client=new Client(); String str=
		 * "{'101':2.02,'211':[0.0333,0.0242,27.2,0,0],'102':2.0875,'201':[0.035,0.034,26.6,0,0],'212':[0.0332,0.0242,27.2,0,0],'202':[0.033,1.1514,27.9,0,0],'301':[0.06,0],'302':[0.07,0.065],'401':0,'303':[0.1845,0.0853],'304':[0.5948,0.5969],'305':[0.8066,0.8077],'306':[0.4558,0.5211],'307':[0.5102,0.4543],'308':[0.4517,0.4552],'309':[0.1983,0.1552]}"
		 * ; while (true) { Client.client.send("172.20.33.237", 3366,
		 * str.getBytes()); System.out.println(str); Thread.sleep(100); }
		 */
		/*
		 * // long begin = System.currentTimeMillis(); // for (int i = 0; i <
		 * 100000; i++) { // int len = Integer.MAX_VALUE; //
		 * Convert.getLengthbyInt(len); // } long mid =
		 * System.currentTimeMillis(); System.out.println("直接转耗时：" + (mid -
		 * begin)); for (int i = 0; i < 100000; i++) { int len =
		 * Integer.MAX_VALUE; String.valueOf(len).length(); } long end =
		 * System.currentTimeMillis(); System.out.println("字符串转耗时：" + (end -
		 * mid));
		 */

	}
}
