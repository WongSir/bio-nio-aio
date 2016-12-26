package bio;

import java.io.IOException;
import java.net.ServerSocket;

/** 
 * @Description: BIO服务端（同步阻塞式I/O） 
 * @author hjd
 * @date 2016年12月24日 下午5:15:45 
 *  
 */
public class Server {

	//默认的端口号
	private static int DEFAULT_PORT =12345;
	/**
	 * 传统的同步阻塞模型中，ServerSocket负责绑定IP地址，启动监听端口
	 * Socket负责发起连接操作
	 */
	//单例的ServerSocket
	private static ServerSocket server;
	//根据传入参数设置监听端口，如果没有参数调用以下方法并使用默认值
	public static void start() throws IOException{
		//使用默认值
		start(DEFAULT_PORT);
	}
	//这个方法不会被大量并发访问，不太需要考虑效率，直接进行方法同步就行了
	public synchronized static void start(int port) throws IOException{
		if(server !=null) return;
	}
}
