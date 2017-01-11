package nio;

import java.util.Scanner;

/** 
* @Description: 测试方法 
* @author hjd
* @date 2017年1月11日 下午5:24:22 
*  
*/
public class TestNIO {
	//测试主方法
	public static void main(String[] args) throws Exception{
		//运行服务器
		Server.start();
		//避免客户端先于服务器启动前执行代码
		Thread.sleep(100);
		//运行客户端
		Client.start();
		while(Client.sendMsg(new Scanner(System.in).nextLine()));
	}
}
