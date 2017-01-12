package aio.server;

/** 
* @Description: AIO����� 
* @author hjd
* @date 2017��1��12�� ����10:23:09 
*  
*/
public class Server {
	private static int DEFAULT_PORT = 12345;
	private static AsyncServerHandler serverHandler;
	/**
	 * ��volatile���εı������߳���ÿ��ʹ�ñ�����ʱ�򣬶����ȡ�����޸ĺ�����µ�ֵ��
	 * volatile�����ױ����ã���������ԭ���Բ���
	 */
	public volatile static long clientCount = 0;
	
	public static void start(){
		start(DEFAULT_PORT);
	}
	public static synchronized void start(int port){
		if(serverHandler !=null){
			return;
		}
		serverHandler = new AsyncServerHandler(port);
		new Thread(serverHandler,"Server").start();//
	}
	public static void main(String[] args){
		Server.start();
	}
}
