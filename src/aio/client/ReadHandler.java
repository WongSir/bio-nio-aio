package aio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/** 
* @Description: 用于读取服务器返回的消息 
* @author hjd
* @date 2017年1月12日 下午3:41:42 
*  
*/
public class ReadHandler implements CompletionHandler<Integer,ByteBuffer>{
	private AsynchronousSocketChannel clientChannel;
	private CountDownLatch latch;
	
	public ReadHandler(AsynchronousSocketChannel clientChannel, CountDownLatch latch) {
		this.clientChannel = clientChannel;
		this.latch = latch;
	}

	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		buffer.flip();
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		String resp;
		try {
			resp = new String(bytes,"UTF-8");
			System.out.println("客户端收到结果：" + resp);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		System.out.println("数据读取失败...");
		try {
			clientChannel.close();
			latch.countDown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
