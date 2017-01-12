package aio;

import java.util.Scanner;

import aio.client.Client;
import aio.server.Server;

/** 
* @Description: 测试方法 
* @author hjd
* @date 2017年1月12日 下午3:55:49 
*  
*/
public class TestAIO {
	//测试主方法
	public static void main(String[] args) throws Exception{
		//运行服务器
		Server.start();
		//睡眠100毫秒，避免客户端先于服务器启动前执行代码
		Thread.sleep(100);
		//运行客户端
		Client.start();
		System.out.println("请输入请求消息：");
        Scanner scanner = new Scanner(System.in);
        while(Client.sendMsg(scanner.nextLine()));
	}
}
