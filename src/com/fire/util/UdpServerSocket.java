package com.fire.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Copyright 2016 CQU. All right reserved.
 * 
 * @author li
 */
public class UdpServerSocket {
	private byte[] buffer = new byte[10240];
	private DatagramSocket ds = null;
	private DatagramPacket packet = null;
	private InetSocketAddress socketAddress = null;
	private String orgIp;

	public UdpServerSocket(String host, int port) throws Exception {
		socketAddress = new InetSocketAddress(host, port);
		ds = new DatagramSocket(socketAddress);
		System.out.println("Udp开启");
	}

	public final String getOrgIp() {
		return orgIp;
	}

	public final void setSoTimeout(int timeout) throws Exception {
		ds.setSoTimeout(timeout);
	}

	public final int getSoTimeout() throws Exception {
		return ds.getSoTimeout();
	}

	public final void bind(String host, int port) throws SocketException {
		socketAddress = new InetSocketAddress(host, port);
		ds = new DatagramSocket(socketAddress);
	}

	public synchronized final String receive() throws IOException {
		packet = new DatagramPacket(buffer, buffer.length);
		ds.receive(packet);

		orgIp = packet.getAddress().getHostAddress();
		String info = new String(packet.getData(), 0, packet.getLength());
		return info;
	}

	public final void setLength(int bufsize) {
		packet.setLength(bufsize);
	}

	public final InetAddress getResponseAddress() {
		return packet.getAddress();
	}

	public final int getResponsePort() {
		return packet.getPort();
	}

	public final void close() {
		try {
			ds.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] arg0) {

	}

}