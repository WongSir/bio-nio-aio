package bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * @Description: BIO服务端源码——伪异步I/O 
 * @author hjd
 * @date 2016年12月26日 下午4:10:55 
 * 使用线程池来管理这些线程，实现1个或多个线程处理N个客户端的模型（但是底层还是使用的同步阻塞I/O），通常被称为“伪异步I/O模型” 
 */
public class ServerBetter {
	//默认的端口号
	private static int DEFAULT_PORT = 12345;
	//单例的ServerSocket
	private static ServerSocket server;
	//线程池，懒汉式的单例
	private static ExecutorService executorService = Executors.newFixedThreadPool(60);
	//根据传入参数设置监听端口，如果没有参数，调用以下方法并使用默认值
	public static void start() throws IOException{
		//使用默认值
		start(DEFAULT_PORT);
	}
	//这个方法仅用于启动服务器，并不会被大量并发访问，不太需要考虑效率，直接进行方法同步即可
	public synchronized static void start(int port) throws IOException{
		if(server != null) return; //server !=null,说明服务器已经启动，无需再次执行
		try{
			/**
			 * 通过构造函数创建ServerSocket
			 * 如果端口合法且空闲，服务端就监听成功
			 */
			server = new ServerSocket(port); //相当于服务器启动了，并且监听了端口port
			System.out.println("服务器已启动，端口号：" + port);
			Socket socket; //网络I/O接口：Socket
			/**
			 * 通过无限循环监听客户端连接，如果没有客户端接入，将阻塞在accept操作上
			 */
			while(true){
				socket = server.accept(); ;// 监听,等待连接,一旦有client端连接便创建socket实例，然后通过socket交互数据.
				/**
				 * 当有新的客户端接入时，会执行下面的代码，
				 * 然后创建一个新的线程处理这条Socket链路
				 */
				executorService.execute(new ServerHandler(socket));
			}
		}finally{
			//一些必要的清理工作
			if(server !=null){ //
				System.out.println("服务器已关闭。");
				server.close();
				server =null; //无具体含义，只是为了防止内存泄露
			}
		}
		
	}
}
