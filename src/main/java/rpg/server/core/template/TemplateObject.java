package rpg.server.core.template;

/**
 * 在模板中提供变量解析支持 <br/>
 * 给出变量名，返回变量值
 * 
 */
@FunctionalInterface
public interface TemplateObject {
	/**
	 * 获取属性
	 * 
	 * @param attr
	 * @return
	 */
	String getAttributeString(String attr);
}
