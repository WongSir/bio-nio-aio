package aio.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import javax.script.ScriptException;

import utils.Calculator;

/** 
* @Description: ���ڶ�ȡ�����Ϣ�ͷ������� 
* @author hjd
* @date 2017��1��12�� ����11:26:41 
*  
*/
public class ReadHandler implements CompletionHandler<Integer,ByteBuffer> {
	private AsynchronousSocketChannel channel;
	public ReadHandler(AsynchronousSocketChannel channel){
		this.channel = channel;
	}
	/**
	 * ��ȡ����Ϣ��Ĵ���
	 */
	@Override
	public void completed(Integer result, ByteBuffer byteBuffer) {
		//flip����
		byteBuffer.flip();
		//������Ϣ���ֽڳ��ȴ�������
		byte[] message = new byte[byteBuffer.remaining()];
		byteBuffer.get(message);
		try {
			String expression = new String(message,"UTF-8");
			System.out.println("�������յ���Ϣ��" + expression);
			String calrResult = null;
			try {
				calrResult = Calculator.cal(expression).toString();
			} catch (ScriptException e) {
				e.printStackTrace();
				calrResult = "�������" + e.getMessage();
			}
			//��ͻ��˷�����Ϣ
//			doWrite(calrResult);
			//��ͻ��˷��ʹ����Ľ��
			sendMsg(calrResult);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ��ͻ��˷�����Ϣ,����WriteHandler
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
	 * ��ͻ��˷�����Ϣ�����������������ڲ������ʽʵ��WriteHandler
	 * @param calrResult
	 */
	private void doWrite(String calrResult) {
		byte[] bytes = calrResult.getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
		writeBuffer.put(bytes);
		writeBuffer.flip();
		//�첽д���ݣ�������ǰ���read����һ��
		channel.write(writeBuffer,writeBuffer,new CompletionHandler<Integer,ByteBuffer>(){
			@Override
			public void completed(Integer result, ByteBuffer buffer) {
				//���û�з����꣬�ͼ�������ֱ�����
				if(buffer.hasRemaining()){
					channel.write(buffer,buffer,this);
				}else{
					//�����µ�Buffer
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					//�첽��������������Ϊ������Ϣ�ص���ҵ��Handler
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
