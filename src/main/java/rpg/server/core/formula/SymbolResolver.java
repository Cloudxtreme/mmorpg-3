/**
 *	Filename	SymbolResolver.java
 *
 * 	Author	rockszhang@qq.com
 *
 *	Version Information	v_1.0.0
 *
 *	Date	Created on  上午09:11:24  2011-11-26
 *
 *	Copyright notice
 *			Copyright (c) 2011 - ? by Beijing Ourpalm CO.,Ltd.All rights reserved.
 *
 */
package rpg.server.core.formula;

/**
 * 表达式变量解析
 * 
 */
public interface SymbolResolver {
	
	public float getValue(String name);
	
}
