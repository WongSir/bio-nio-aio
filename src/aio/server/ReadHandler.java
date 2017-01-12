package aio.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import javax.script.ScriptException;

import utils.Calculator;

/** 
* @Description: 用于读取半包消息和发送内容 
* @author hjd
* @date 2017年1月12日 上午11:26:41 
*  
*/
public class ReadHandler implements CompletionHandler<Integer,ByteBuffer> {
	private AsynchronousSocketChannel channel;
	public ReadHandler(AsynchronousSocketChannel channel){
		this.channel = channel;
	}
	/**
	 * 读取到消息后的处理
	 */
	@Override
	public void completed(Integer result, ByteBuffer byteBuffer) {
		//flip操作
		byteBuffer.flip();
		//根据消息的字节长度创建数组
		byte[] message = new byte[byteBuffer.remaining()];
		byteBuffer.get(message);
		try {
			String expression = new String(message,"UTF-8");
			System.out.println("服务器收到消息：" + expression);
			String calrResult = null;
			try {
				calrResult = Calculator.cal(expression).toString();
			} catch (ScriptException e) {
				e.printStackTrace();
				calrResult = "计算错误：" + e.getMessage();
			}
			//向客户端发送消息
//			doWrite(calrResult);
			//向客户端发送处理后的结果
			sendMsg(calrResult);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 向客户端发送消息,调用WriteHandler
	 * @param msg
	 */
	public void sendMsg(String msg){
		byte[] bytes = msg.getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
		writeBuffer.put(bytes);
		writeBuffer.flip();
		channel.write(writeBuffer,writeBuffer,new WriteHandler(channel, null));
	}
	
	/**
	 * 向客户端发送消息，在这里是以匿名内部类的形式实现WriteHandler
	 * @param calrResult
	 */
	private void doWrite(String calrResult) {
		byte[] bytes = calrResult.getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
		writeBuffer.put(bytes);
		writeBuffer.flip();
		//异步写数据，参数与前面的read方法一样
		channel.write(writeBuffer,writeBuffer,new CompletionHandler<Integer,ByteBuffer>(){
			@Override
			public void completed(Integer result, ByteBuffer buffer) {
				//如果没有发送完，就继续发送直到完成
				if(buffer.hasRemaining()){
					channel.write(buffer,buffer,this);
				}else{
					//创建新的Buffer
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					//异步读，第三个参数为接收消息回调的业务Handler
					channel.read(readBuffer,readBuffer,new ReadHandler(channel));
					
				}
			}
			@Override
			public void failed(Throwable exc, ByteBuffer buffer) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		
	}	
	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		try {
			this.channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
