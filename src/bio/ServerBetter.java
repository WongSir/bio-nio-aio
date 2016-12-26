package bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * @Description: BIO�����Դ�롪��α�첽I/O 
 * @author hjd
 * @date 2016��12��26�� ����4:10:55 
 * ʹ���̳߳���������Щ�̣߳�ʵ��1�������̴߳���N���ͻ��˵�ģ�ͣ����ǵײ㻹��ʹ�õ�ͬ������I/O����ͨ������Ϊ��α�첽I/Oģ�͡� 
 */
public class ServerBetter {
	//Ĭ�ϵĶ˿ں�
	private static int DEFAULT_PORT = 12345;
	//������ServerSocket
	private static ServerSocket server;
	//�̳߳أ�����ʽ�ĵ���
	private static ExecutorService executorService = Executors.newFixedThreadPool(60);
	//���ݴ���������ü����˿ڣ����û�в������������·�����ʹ��Ĭ��ֵ
	public static void start() throws IOException{
		//ʹ��Ĭ��ֵ
		start(DEFAULT_PORT);
	}
	//������������������������������ᱻ�����������ʣ���̫��Ҫ����Ч�ʣ�ֱ�ӽ��з���ͬ������
	public synchronized static void start(int port) throws IOException{
		if(server != null) return; //server !=null,˵���������Ѿ������������ٴ�ִ��
		try{
			/**
			 * ͨ�����캯������ServerSocket
			 * ����˿ںϷ��ҿ��У�����˾ͼ����ɹ�
			 */
			server = new ServerSocket(port); //�൱�ڷ����������ˣ����Ҽ����˶˿�port
			System.out.println("���������������˿ںţ�" + port);
			Socket socket; //����I/O�ӿڣ�Socket
			/**
			 * ͨ������ѭ�������ͻ������ӣ����û�пͻ��˽��룬��������accept������
			 */
			while(true){
				socket = server.accept(); ;// ����,�ȴ�����,һ����client�����ӱ㴴��socketʵ����Ȼ��ͨ��socket��������.
				/**
				 * �����µĿͻ��˽���ʱ����ִ������Ĵ��룬
				 * Ȼ�󴴽�һ���µ��̴߳�������Socket��·
				 */
				executorService.execute(new ServerHandler(socket));
			}
		}finally{
			//һЩ��Ҫ��������
			if(server !=null){ //
				System.out.println("�������ѹرա�");
				server.close();
				server =null; //�޾��庬�壬ֻ��Ϊ�˷�ֹ�ڴ�й¶
			}
		}
		
	}
}
