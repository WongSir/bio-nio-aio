package nio;

/** 
* @Description: TODO 
* @author hjd
* @date 2017年1月11日 下午1:52:19 
*  
*/
public class Server {
	private static int DEFAULT_PORT = 12345;
	private static ServerHandle serverHandle;
	public static void start(){
		start(DEFAULT_PORT);
	}
	public static synchronized void start(int port){
		if(serverHandle!=null){
			serverHandle.stop();
		}
		serverHandle = new ServerHandle(port);
		new Thread(serverHandle,"Server").start();
	}
	public static void main(String[] args){
		start();
	}
}
