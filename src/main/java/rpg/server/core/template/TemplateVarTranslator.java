package rpg.server.core.template;

/**
 * 模板变量翻译器，将服务器中变量字符串翻译成用户可读的一些文字或者链接<br/>
 * 增加这个接口以后，模板可以支持更为丰富的变量形式。 模板中的变量以{translator:key:value}的形式组成，其中<br />
 * translator:负责解析此变量的模块，比如item模块，或者horse模块等，模块需要在UITemplate中注册<br />
 * key:解析的变量ID，供translator识别需要翻译的变量是什么<br />
 * value:变量值，这个值是从java对象中读取的值，比如马的品质值<br />
 * 以下是几个例子：<br />
 * {item:link:ArmorTTx}:翻译成物品ArmorTTx的连接UI，物品点击UI可以直接查看物品<br />
 * {camp:name:tj}:翻译成对应的阵营名称<br />
 * {horse:quality:var_quality}:翻译成马的品质名称<br />
 * 
 */
public interface TemplateVarTranslator {
	/**
	 * 翻译变量
	 * 
	 * @param key
	 *            变量名
	 * @param value
	 *            变量值(从java对象中获得)
	 * @return
	 */
	String translate(String key, String value);

}
