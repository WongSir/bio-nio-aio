package bio;

import java.io.IOException;
import java.net.ServerSocket;

/** 
 * @Description: BIO����ˣ�ͬ������ʽI/O�� 
 * @author hjd
 * @date 2016��12��24�� ����5:15:45 
 *  
 */
public class Server {

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
	//����������ᱻ�����������ʣ���̫��Ҫ����Ч�ʣ�ֱ�ӽ��з���ͬ��������
	public synchronized static void start(int port) throws IOException{
		if(server !=null) return;
	}
}
