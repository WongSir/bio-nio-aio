package utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/** 
 * @Description: �������ʹ�õ������ڼ���Ĺ�����
 * @author hjd
 * @date 2016��12��26�� ����1:41:32 
 *  
 */
public final class Calculator {
	private final static ScriptEngine jse =  new ScriptEngineManager().getEngineByName("JavaScript");
	public static Object cal(String expression) throws ScriptException{
		return jse.eval(expression); //ʹ��JavaScript�ű��е�eval()�����������ַ������ʽ���� 
	}
}
