/**
 *	Filename	CalculatorConfig.java
 *
 * 	Author	rockszhang@qq.com
 *
 *	Version Information	v_1.0.0
 *
 *	Date	Created on  上午09:10:24  2011-11-26
 *
 *	Copyright notice
 *			Copyright (c) 2011 - ? by Beijing Ourpalm CO.,Ltd.All rights reserved.
 *
 */
package rpg.server.core.formula;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.World;
import rpg.server.util.io.XmlUtils;

/**
 * 表达式运算配置数据
 */
public class CalculatorConfig {

	/** 配置文件 */
	private static final String RES = "formula" + File.separator
			+ "formulaConfig.xml";

	/** 单体实例 */
	private static final CalculatorConfig instance = new CalculatorConfig();

	/** 结束 */
	static final byte END = 3;

	/** 等于 */
	static final byte EQUAL = 2;

	/** 大于 */
	static final byte LARGE = 1;

	/** 小于 */
	static final byte SMALL = 0;

	/** 不存在 */
	static final byte ERROR = -1;

	/** 运算符优先级矩阵 */
	private static final byte operator_matrix[][] = {
			// stack # + - * / ( )
			/* e # */{ END, SMALL, SMALL, SMALL, SMALL, ERROR, ERROR },
			/* x + */{ LARGE, SMALL, SMALL, SMALL, SMALL, LARGE, ERROR },
			/* p - */{ LARGE, SMALL, SMALL, SMALL, SMALL, LARGE, ERROR },
			/* s * */{ LARGE, LARGE, LARGE, SMALL, SMALL, LARGE, ERROR },
			/* s / */{ LARGE, LARGE, LARGE, SMALL, SMALL, LARGE, ERROR },
			/* i ( */{ LARGE, LARGE, LARGE, LARGE, LARGE, LARGE, ERROR },
			/* o ) */{ ERROR, SMALL, SMALL, SMALL, SMALL, EQUAL, ERROR },
	/* n */
	};

	/** 运算类型 */
	private String operators = "+-*/";

	/** 运算符 */
	private String operator;

	/** 数字 */
	private String number;

	/** 英文字符 */
	private String english;

	/**
	 * 取得单体实例
	 * 
	 * @return
	 */
	public static CalculatorConfig getInstance() {
		return instance;
	}

	/**
	 * 判断字符是否是运算符
	 * 
	 * @param ch
	 * @return
	 */
	public boolean isOperator(char ch) {
		return this.operator.indexOf(ch) != -1;
	}

	/**
	 * 判断字符是否是数字
	 * 
	 * @param ch
	 * @return
	 */
	public boolean isNumber(char ch) {
		return this.number.indexOf(ch) != -1;
	}

	/**
	 * 判断字符是否是英文
	 * 
	 * @param ch
	 * @return
	 */
	public boolean isEnglish(char ch) {
		return this.english.indexOf(ch) != -1;
	}

	/**
	 * 取得当前运算符和栈顶运算符的优先级关系
	 * 
	 * @param exp
	 *            当前运算符
	 * @param stack
	 *            栈顶运算符
	 * @return
	 */
	public byte getPriority(char exp, char stack) {
		int expId = this.operator.indexOf(exp);
		int stackId = this.operator.indexOf(stack);
		return operator_matrix[expId][stackId];
	}

	/**
	 * 对a、b进行operator运算，并返回结果
	 * 
	 * @param a
	 * @param b
	 * @param operator
	 *            运算符
	 * @return 运算结果
	 */
	public float operate(float a, float b, char operator) {
		int id = this.operators.indexOf(operator);
		float result = 0f;
		switch (id) {
		case 0:
			// +
			result = a + b;
			break;
		case 1:
			// -
			result = a - b;
			break;
		case 2:
			// *
			result = a * b;
			break;
		case 3:
			// /
			result = a / b;
			break;
		}
		return result;
	}

	/**
	 * 取得运算符集合
	 * 
	 * @return
	 */
	public String getOperate() {
		return this.operator;
	}

	/**
	 * 载入配置文件
	 * 
	 * @throws Exception
	 */
	public void load() throws Exception {
		File file = new File(World.getInstance().getResPath(), RES);
		Document d = XmlUtils.load(file);
		Element root = d.getDocumentElement();
		this.english = XmlUtils.getChildText(root, "english");
		this.number = XmlUtils.getChildText(root, "number");
		this.operator = XmlUtils.getChildText(root, "operator");
	}

}
