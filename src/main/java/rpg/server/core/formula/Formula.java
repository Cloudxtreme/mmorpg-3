/**
 *	Filename	Formula.java
 *
 * 	Author	rockszhang@qq.com
 *
 *	Version Information	v_1.0.0
 *
 *	Date	Created on  上午9:07:40 2012-5-15
 *
 *	Copyright notice
 *			Copyright (c) 2011 - ? by Beijing Ourpalm CO.,Ltd.All rights reserved.
 *
 */
package rpg.server.core.formula;

/**
 * 类功能描述<br/>
 * 
 * 表达式运算<br/>
 *
 * 暂不支持 表达式中使用自定义函数形式参数函数递归调用 例如：add(add(1,2),3)<br/>
 *
 * 
 */
public class Formula {

	/** 表达式 */
	Calculator expression;

	/** 运算结果 */
	float value;

	/**
	 * 构建表达式
	 * 
	 * @param expression
	 */
	public Formula(String expression) {
		this.expression = new Calculator(expression);
		try {
			this.evaluate(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构建表达式
	 * 
	 * @param expression
	 */
	public Formula(String expression, SymbolResolver symbol) {
		this.expression = new Calculator(expression);
		try {
			this.evaluate(symbol);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 属性计算
	 * 
	 * @param symbol
	 * @throws Exception
	 */
	public float evaluate(SymbolResolver symbol) throws Exception {
		value = this.expression.calculate(symbol);
		return value;
	}

	/**
	 * 浮点型数据值
	 * 
	 * @return
	 */
	public float getFloatValue() {
		return value;
	}

	/**
	 * 整形数值
	 * 
	 * @return
	 */
	public int getIntValue() {
		return (int) value;
	}

	/**
	 * 向下取整
	 * 
	 * @return
	 */
	public int getFloorValue() {
		return (int) Math.floor(value);
	}

	/**
	 * 向上取整
	 * 
	 * @return
	 */
	public int getCeilValue() {
		return (int) Math.ceil(value);
	}

	/**
	 * 四舍五入
	 * 
	 * @return
	 */
	public int getRoundValue() {
		return Math.round(value);
	}

	/**
	 * 表达式
	 */
	public String toString() {
		return this.expression.toString();
	}
}
