package aio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/** 
* @Description: �첽д��Ϣ����ͻ��˷�����Ϣ 
* @author hjd
* @date 2017��1��12�� ����2:02:54 
*  
*/
public class WriteHandler implements CompletionHandler<Integer,ByteBuffer>{
	private AsynchronousSocketChannel channel;
	private CountDownLatch latch;
	public WriteHandler(AsynchronousSocketChannel channel, CountDownLatch latch) {
		this.channel = channel;
		this.latch = latch;
	}

	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		//�����Ϣû�����꣬�ͼ�������ֱ�����
		if(buffer.hasRemaining()){
			channel.write(buffer,buffer,new WriteHandler(channel, latch));
		}else{
			//�����µ�Buffer
			ByteBuffer readBuffer = ByteBuffer.allocate(1024);
			//�첽��������������Ϊ������Ϣ�ص���ҵ��Handler
			channel.read(readBuffer,readBuffer,new ReadHandler(channel));
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer buffer) {
		try {
			channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
