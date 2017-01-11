package bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 * @Description: BIO����ˣ�ͬ������ʽI/O�� 
 * @author hjd
 * @date 2016��12��24�� ����5:15:45 
 *  
 */
public class ServerNormal {

	//Ĭ�ϵĶ˿ں�
	private static int DEFAULT_PORT =12345;
	/**
	 * ��ͳ��ͬ������ģ���У�ServerSocket�����IP��ַ�����������˿�
	 * Socket���������Ӳ���
	 */
	//������ServerSocket
	private static ServerSocket server;
	//���ݴ���������ü����˿ڣ����û�в����������·�����ʹ��Ĭ��ֵ
	public static void start() throws IOException{
		//ʹ��Ĭ��ֵ
		start(DEFAULT_PORT);
	}
	//����������ᱻ�����������ʣ���̫��Ҫ����Ч�ʣ�ֱ�ӽ��з���ͬ��������(ͨ��synchronized�ؼ���ʵ��)
	public synchronized static void start(int port) throws IOException{
		if(server !=null) return;  //server��Ϊ�գ�˵���������Ѿ�������
		try{
			//ͨ�����캯������ServerSocket
			//����˿ںϷ��ҿ��У�����˾ͼ����ɹ�
			server = new ServerSocket(port);
			System.out.println("���������������˿ںţ�" + port);
			Socket socket;
			/**
			 * ͨ������ѭ�������ͻ�������
			 * ���û�пͻ��˽��룬��������accept������
			 */
			while(true){
				socket = server.accept();
				/**
				 * �����µĿͻ��˽���ʱ����ִ������Ĵ���
				 * Ȼ�󴴽�һ���µ��̴߳�������Socket��·
				 */
				new Thread(new ServerHandler(socket)).start();
			}
		}finally{
			//һЩ��Ҫ��������
			if(server !=null){
				System.out.println("�������ѹرա�");
				server.close();
				server =null;
			}
		}
	}
}
