package aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/** 
* @Description: �첽������������ 
* @author hjd
* @date 2017��1��12�� ����9:50:16 
*  
*/
public class AsyncServerHandler implements Runnable{
	/**
	 * CountDownLatch��һ��ͬ�������࣬
	 * ������һ�������߳�һֱ�ȴ���ֱ�������̵߳Ĳ���ִ�������ִ�С�
	 */
	public CountDownLatch latch;
	/**
	 * �첽�׽���ͨ��
	 */
	public AsynchronousServerSocketChannel channel;
	/**
	 * ���췽��
	 * @param port
	 */
	public AsyncServerHandler(int port){
		try {
			//�򿪷����ͨ��
			channel = AsynchronousServerSocketChannel.open();
			/**
			 * �󶨶˿�
			 * ���� bind() ��һ���׽��ֵ�ַ��Ϊ�������
			 * ʹ��SocketAddress������connect�������ݷ�������IP�Ͷ˿ڡ�
			 */
			channel.bind(new InetSocketAddress(port));
			System.out.println("���������������˿ںţ�" + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		/**
		 * CountDownLatch��ʼ��
		 * ���������ǣ������һ������ִ�еĲ���֮ǰ������ǰ���ֳ�һֱ����
		 * �˴������������ڴ���������ֹ�����ִ����ɺ��˳���Ҳ����ʹ��while(true)+sleepʵ��
		 * �������������Ͳ���Ҫ����������⣬��Ϊ������ǲ����˳��ġ�
		 */
		latch = new CountDownLatch(1);
		//���ڽ��տͻ��˵�����
		channel.accept(this,new AcceptHandler());
		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
