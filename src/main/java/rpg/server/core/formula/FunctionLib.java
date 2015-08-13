/**
 *	Filename	FunctionLib.java
 *
 * 	Author	rockszhang@qq.com
 *
 *	Version Information	v_1.0.0
 *
 *	Date	Created on  上午10:10:24  2011-11-26
 *
 *	Copyright notice
 *			Copyright (c) 2011 - ? by Beijing Ourpalm CO.,Ltd.All rights reserved.
 *
 */
package rpg.server.core.formula;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.util.io.ResourceListener;
import rpg.server.util.io.XmlUtils;


/**
 * 
 * 自定义函数库
 *
 */
public class FunctionLib implements ResourceListener {
	
	/**函数库文件名称*/
	private static String RES="formula/customFunctionlib.xml";
	
	/**自定义函数库*/
	private Map<String,Function> functions = new HashMap<String,Function>();
	
	/**自定义函数库单体实例*/
	private static FunctionLib instance = new FunctionLib();
	
	/**函数正则表达式*/
	private String regx = "\\([^)]*[)]*\\)";
	
	/**监听函数库配置文件*/
	private File file;
	
	/**
	 * 取得函数库单体实例
	 * @return
	 */
	public static FunctionLib getInstance(){
		return instance;
	}
	
	/**
	 * 向函数库注册函数
	 * @param name 函数全名（包括变量，例如：add(a,b)）
	 * @param expression 函数表达式
	 */
	public void registerFunction(String name,String expression){
		
	}
	
	/**
	 * 替换公式中的自定义函数
	 * @param formula 公式
	 * @return
	 * @throws Exception 
	 */
	public void replaceFunction(StringBuilder formula) throws Exception{
		if(formula == null || formula.length() == 0 || formula.equals("null")){
			return;
		}
		Iterator<Function> it = this.functions.values().iterator();
		int start = 0;
		int end = 0;
		String function = "";
		while(it.hasNext()){
			//替换自定义函数
			Function func = it.next();
			Pattern p = Pattern.compile(func.name+regx);
			Matcher m = p.matcher(formula);
			while(m.find()){
				start = m.start();
				end = m.end();
				function = m.group();
				int left = function.indexOf('(');
				int right = function.length()-1;
				//函数实参
				StringBuilder param = new StringBuilder(function.substring(left+1,right));				
				//判断实参中是否有函数，如果有则递归替换
//				Pattern temp = Pattern.compile(regx);
//				Matcher mat = temp.matcher(param);
//				if(mat.find()){
////					this.replaceFunction(param);
//					throw new Exception("自定义函数暂不支持递归调用");
//				}else{
					String params[] = param.toString().split(",");
					if(params != null && func.param != null && params.length != func.param.length){
						continue;
					}
					String funcexp = new String(func.expression);
					for(int i=0;i<params.length;i++){
						String str = CalculatorConfig.getInstance().getOperate();
						boolean oper = false;
						for(int j=0;j<str.length();j++){
							if(params[i].indexOf(str.charAt(j)) != -1){
								oper = true;
								break;
							}
						}
						StringBuilder sbs = new StringBuilder();
						if(oper){
							sbs.append("(").append(params[i]).append(")");
						}else{
							sbs.append(params[i]);
						}
						String arg = sbs.toString();
						String para = func.param[i];
						funcexp = funcexp.replace(para, arg);						
					}
					function = funcexp;
//				}
				formula.replace(start, end, function);
			}
		}
//		System.out.println("formula---->"+formula.toString());
	}

	/**
	 * 载入自定义函数库
	 * @param root
	 */
	public void load(String root){
		this.file = new File(root+RES);
		this.load(file);
	}
	
	/**
	 * 载入函数库
	 * @param file
	 */
	private void load(File file){
		try{
			Document d = XmlUtils.load(file);
			Element root = d.getDocumentElement();
			Element []funcs = XmlUtils.getChildrenByName(root, "function");
			if(funcs != null){
				for(Element func:funcs){
					String function = FunctionLib.trim(XmlUtils.getAttribute(func, "id"));
					String expression = FunctionLib.trim(XmlUtils.getText(func));
					//分析 变量
					int start = function.indexOf('(');
					String param = function.substring(start+1, function.indexOf(')'));
					String params[] = param.split("\\W");
					String name = function.substring(0, start);
					String key = name+params.length;					
//					System.out.println("载入自定义函数-->"+function+":"+expression);
					if(this.functions.containsKey(key)){
						this.functions.get(key).assign(name,expression,params);
					}else{
						this.functions.put(key,new Function(name,expression,params,key));
					}
				}
			}
		}catch(Exception e){
			System.out.println("载入自定义函数异常");
			e.printStackTrace();
		}
	}

	@Override
	public File listenedFile() {
		return file;
	}

	@Override
	public void onResourceChange(File file) {
		this.load(file);
	}
	
	/**
	 * 将函数库导出为系统文件
	 */
	public void export(){
		
	}
	
	/**
	 * 
	 * 类功能描述<br/>
	 * 
	 *	自定义函数
	 * 
	 * @author rocks
	 *
	 */
	class Function{
		
		/**函数名*/
		String name;
		
		/**表达式*/
		String expression;
		
		/**变量*/
		String param[];
		
		/**函数键码=函数名+变量个数*/
		String key;
		
		/**
		 * 构造函数
		 * @param name 函数名
		 * @param exp 函数表达式
		 * @param param 函数变量
		 */
		Function(String name,String exp,String param[],String key){
			this.name = name;
			this.expression = exp;
			this.param = param;
			this.key = key;
		}
		
		void assign(String name,String exp,String param[]){
			this.name = name;
			this.expression = exp;
			this.param = param;
		}
	}
	
	/**
	 * 消除字符串中的全部空格
	 * @param str
	 * @return
	 */
	public static String trim(String str){
		if(str == null){
			return null;
		}
		return str.replace(" ", "");
	}
}
