package nio;

import java.util.Scanner;

/** 
* @Description: ���Է��� 
* @author hjd
* @date 2017��1��11�� ����5:24:22 
*  
*/
public class TestNIO {
	//����������
	public static void main(String[] args) throws Exception{
		//���з�����
		Server.start();
		//����ͻ������ڷ���������ǰִ�д���
		Thread.sleep(100);
		//���пͻ���
		Client.start();
		while(Client.sendMsg(new Scanner(System.in).nextLine()));
	}
}
