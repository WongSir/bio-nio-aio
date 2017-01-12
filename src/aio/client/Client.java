package aio.client;

import java.util.Scanner;

/** 
* @Description: AIO�ͻ��� 
* @author hjd
* @date 2017��1��12�� ����2:55:26 
*  
*/
public class Client {
	private static String DEFAULT_HOST = "127.0.0.1";
	private static int DEFAULT_PORT = 12345;
	private static AsyncClientHandler clientHandler;
	public static void start(){
		start(DEFAULT_HOST,DEFAULT_PORT);
	}
	public static synchronized void start(String ip,int port){
		if(clientHandler !=null){
			return ;
		}
		clientHandler = new AsyncClientHandler(ip, port);
		new Thread(clientHandler,"Client").start();
	}
	//�������������Ϣ
	public static boolean sendMsg(String msg) throws Exception{
		if(msg.equals("q")) return false;
		clientHandler.sendMsg(msg);
		return true;
	}
	
	public static void clientRun(int sum){
		for(int i=0;i<=sum;i++){
			Client.start();
			System.out.println(i);
		}
	}
	
	public static void main(String[] args) throws Exception{
//		clientRun(1000000);
		Client.start();
		System.out.println("������������Ϣ��");
		Scanner scanner = new Scanner(System.in);
		while(Client.sendMsg(scanner.nextLine()));
	}
	
}
