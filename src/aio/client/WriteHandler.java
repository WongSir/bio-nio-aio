package aio.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/** 
* @Description: 异步写数据Handler 
* @author hjd
* @date 2017年1月12日 下午3:33:20 
*  
*/
public class WriteHandler implements CompletionHandler<Integer,ByteBuffer>{
	private AsynchronousSocketChannel clientChannel;
	private CountDownLatch latch;
	public WriteHandler(AsynchronousSocketChannel clientChannel, CountDownLatch latch) {
		this.clientChannel = clientChannel;
		this.latch = latch;
	}

	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		//完成全部数据的写入
		if(buffer.hasRemaining()){
			clientChannel.write(buffer,buffer,this);
		}else{
			//创建新的Buffer
			ByteBuffer readBuffer = ByteBuffer.allocate(1024);
			clientChannel.read(readBuffer, readBuffer, new ReadHandler(clientChannel,latch));
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		System.out.println("数据发送失败...");
		try {
			clientChannel.close();
			latch.countDown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
