package com.fire.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ByteArrayDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		if (in.remaining() > 0) {
			byte[] bytes = new byte[in.limit()];
			in.get(bytes);
			out.write(bytes);
		}
		// 处理成功，让父类进行接收下个包
		return false;
	}
}
