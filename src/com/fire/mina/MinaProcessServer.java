package com.fire.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaProcessServer {
	public static void init() throws IOException {
		MinaProcessServer server = new MinaProcessServer();
		server.start();
	}

	private void start() throws IOException {
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 180);
		acceptor.setHandler(new NodeOrHostHandler());
		// 设置日志记录器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ByteArrayCodecFactory()));
		acceptor.bind(new InetSocketAddress(9009));
	}
}
