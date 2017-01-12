package aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/** 
* @Description: 异步服务器处理类 
* @author hjd
* @date 2017年1月12日 上午9:50:16 
*  
*/
public class AsyncServerHandler implements Runnable{
	/**
	 * CountDownLatch是一个同步工具类，
	 * 它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行。
	 */
	public CountDownLatch latch;
	/**
	 * 异步套接字通道
	 */
	public AsynchronousServerSocketChannel channel;
	/**
	 * 构造方法
	 * @param port
	 */
	public AsyncServerHandler(int port){
		try {
			//打开服务端通道
			channel = AsynchronousServerSocketChannel.open();
			/**
			 * 绑定端口
			 * 方法 bind() 将一个套接字地址作为其参数。
			 * 使用SocketAddress类来向connect方法传递服务器的IP和端口。
			 */
			channel.bind(new InetSocketAddress(port));
			System.out.println("服务器已启动，端口号：" + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		/**
		 * CountDownLatch初始化
		 * 它的作用是：在完成一组正在执行的操作之前，允许当前的现场一直阻塞
		 * 此处，它让现在在此阻塞，防止服务端执行完成后退出。也可以使用while(true)+sleep实现
		 * 但是生产环境就不需要担心这个问题，因为服务端是不会退出的。
		 */
		latch = new CountDownLatch(1);
		//用于接收客户端的连接
		channel.accept(this,new AcceptHandler());
		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
