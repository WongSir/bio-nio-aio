package aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/** 
* @Description: ��Ϊhandler���տͻ������� 
* @author hjd
* @date 2017��1��12�� ����10:20:24 
*  
*/
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncServerHandler>{
	private AsynchronousSocketChannel serverChannel;
	@Override
	public void completed(AsynchronousSocketChannel channel, AsyncServerHandler serverHandler) {
		//�������������ͻ��˵�����
		Server.clientCount++;
		System.out.println("���ӵĿͻ�������" + Server.clientCount);
		serverHandler.channel.accept(serverHandler,this);
		//�����µ�Buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		//�첽��������������Ϊ������Ϣ�ص���ҵ��Handler
		channel.read(buffer,buffer,new ReadHandler(channel)	);
	}
	
	@Override
	public void failed(Throwable exc, AsyncServerHandler serverHandler) {
		exc.printStackTrace();
		serverHandler.latch.countDown();
	}

}
