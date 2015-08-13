/**
 *	Filename	Calculator.java
 *
 * 	Author	rockszhang@qq.com
 *
 *	Version Information	v_1.0.0
 *
 *	Date	Created on  上午09:09:24  2011-11-26
 *
 *	Copyright notice
 *			Copyright (c) 2011 - ? by Beijing Ourpalm CO.,Ltd.All rights reserved.
 *
 */
package rpg.server.core.formula;

import java.util.List;
import java.util.Stack;

/**
 * 表达式运算
 *
 */
public class Calculator {
	
	
	/**存储计算单元序列*/
	private List<CalUnit> units = new Stack<CalUnit>();
	
	/**运算使用的运算符栈*/
	private Stack<Character> operator = new Stack<Character>();
	
	/**运算使用的数据栈*/
	private Stack<Float> data = new Stack<Float>();

	/**表达式*/
//	String expression;
	
	/**
	 * 构造计算器
	 * @param formula
	 */
	public Calculator(String formula){
		this.parse(formula);
	}
	
	/**
	 * 分析公式字符串
	 * @param formula
	 */
	private void parse(String formula){
		if(formula == null || formula.equals("null") || formula.length() == 0){
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(FunctionLib.trim(formula)).append("#");
		//兼容首字符为 - 表达式
		if(sb.charAt(0) == '-'){
			sb.insert(0, '0');
		}
		//替换自定义函数为表达式
		//解析表达式
		try{
//			System.out.println("--"+sb.toString()+"--");
			FunctionLib.getInstance().replaceFunction(sb);
//			this.expression = sb.toString();
			this.parseExpression(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 计算无变量表达式 
	 * @return 结果
	 */
	public float calulate() throws Exception{
		return this.calculate(null);
	}
	
	/**
	 * 计算表达式
	 * @return 结果
	 * @throws Exception 
	 */
	public float calculate(SymbolResolver object) throws Exception{
		try{
			this.data.clear();
			this.operator.clear();
			int id = 0;

			this.operator.push('#');

			while(id < this.units.size()){
				CalUnit cu = this.units.get(id);
				if(cu.type == CalUnit.OPERATOR){
					//运算符
					char operStack = this.operator.peek();
					byte priority = CalculatorConfig.getInstance().getPriority(cu.operator, operStack);
					if(priority == CalculatorConfig.SMALL){
						//数据栈出栈2个数据进行运算
						float data1 = this.data.pop();
						float data2 = this.data.pop();
						char operate = this.operator.pop();
						float result = CalculatorConfig.getInstance().operate(data2, data1, operate);
						this.data.push(result);
						continue;
					}else if(priority == CalculatorConfig.LARGE){
						//运算符入栈
						this.operator.push(cu.operator);
					}else if(priority == CalculatorConfig.EQUAL){
						//左右括号 相消
						this.operator.pop();
					}else if(priority == CalculatorConfig.END){
						//运算结束
						this.operator.pop();
						break;
					}else{
						//表达式结构异常
						throwException("表达式结构异常");
						break;
					}
				}else if(cu.type == CalUnit.DATA){
					//数值入栈
					this.data.push(cu.data);
				}else{
					//变量，取得变量值入栈
					this.data.push(this.getValue(object, cu.param));
				}
				id ++;
			}
			//运算过程结束，如果运算符栈未空 或 数据栈大小不为1，则运算表达式结构异常
			if(!this.operator.isEmpty() || this.data.size() != 1){
				throwException("表达式结构异常"+this.toString());
			}
			return this.data.pop();
		}catch(Exception e){
			throwException("cocoerror-->"+this.toString());
			return 0f;
		}
	}
	
	/**
	 * 抛出异常
	 * @param exception
	 * @throws Exception 
	 */
	private void throwException(String exception) throws Exception{
		System.out.println("---"+exception+"---");
		throw new Exception(exception);
	}
	
	/**
	 * 取得变量值
	 * @param object
	 * @param param
	 * @return
	 */
	private float getValue(SymbolResolver object,String param){
		if(object == null){
			return 0f;
		}
		float result = 0f;
		try{
			result = object.getValue(param);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 解析运算表达式
	 * @param formula
	 * @throws Exception 
	 */
	private void parseExpression(String formula) throws Exception{
		int id = 0;
		int startId = 0;	
		char tempCh;
		this.units.clear();
		
		while(id < formula.length()){
			char ch = formula.charAt(id);
			if(CalculatorConfig.getInstance().isOperator(ch)){
				//运算符
				CalUnit oper = new CalUnit(CalUnit.OPERATOR);
				oper.operator = ch;
				this.units.add(oper);
				id ++;
			}else if(CalculatorConfig.getInstance().isNumber(ch)){
				//数字
				startId = id;
				while(id < formula.length()){
					id++;
					if(id == formula.length()){
						break;
					}
					tempCh = formula.charAt(id);
					if(!CalculatorConfig.getInstance().isNumber(tempCh)){
						break;
					}
				}				
				//数字入栈
				float data = Float.parseFloat(formula.substring(startId,id));
				CalUnit oper = new CalUnit(CalUnit.DATA);
				oper.data = data;
				this.units.add(oper);
			}else if(CalculatorConfig.getInstance().isEnglish(ch)){
				//变量
				startId = id;
				while(id < formula.length()){
					id++;
					if(id == formula.length()){
						break;
					}
					tempCh = formula.charAt(id);
					if(!CalculatorConfig.getInstance().isEnglish(tempCh)){
						break;
					}
				}
				//变量入栈
				String param = formula.substring(startId,id);
				CalUnit oper = new CalUnit(CalUnit.PARAM);
				oper.param = param;
				this.units.add(oper);
			}else{
				//表达式含有异常字符
				throwException("表达式中含有非法字符 --> "+formula);
			}
		}
	}
	
	
	/**
	 * @author 张建中
	 *
	 */
	private class CalUnit {

		static final byte DATA = 0;
		static final byte OPERATOR = 1;
		static final byte PARAM = 2;
		/**数据类型 0：数据 1：运算符 2：变量*/
		byte type;

		/**数据*/
		float data;

		/**运算符*/
		char operator;

		/**运算变量*/
		String param;

		CalUnit(byte type){
			this.type = type;
		}

		/**
		 * 运算符
		 */
		public String toString(){
			if(type == 0){
				return String.valueOf(data);
			}
			if(type == 1){
				return String.valueOf(operator);
			}
			return param;
		}
	}

	/**
	 * 表达式
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(CalUnit cu:this.units){
			sb.append(cu.toString());
		}
		return sb.toString();
	}
	
	public static void main(String args[]){
		String root = "../../../../resource/";
		FunctionLib.getInstance().load(root);
		CalculatorConfig.getInstance().load(root);
		Calculator cc = new Calculator("1000+100.0*99-(600-3*15)/(((68-9)-3)*2-100)+10000/7*71");
		try{
			long l = System.currentTimeMillis();
			System.out.println(cc.calulate());
			System.out.println(System.currentTimeMillis() - l);
		}catch(Exception e){
			e.printStackTrace();
		}	

	}

}
