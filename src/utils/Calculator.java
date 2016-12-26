package utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/** 
 * @Description: 服务端中使用到的用于计算的工具类
 * @author hjd
 * @date 2016年12月26日 下午1:41:32 
 *  
 */
public final class Calculator {
	private final static ScriptEngine jse =  new ScriptEngineManager().getEngineByName("JavaScript");
	public static Object cal(String expression) throws ScriptException{
		return jse.eval(expression); //使用JavaScript脚本中的eval()函数来进行字符串表达式计算 
	}
}
