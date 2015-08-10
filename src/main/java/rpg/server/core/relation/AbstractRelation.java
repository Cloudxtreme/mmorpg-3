package rpg.server.core.relation;

import java.util.Set;

import org.w3c.dom.Element;

import rpg.server.core.obj.SimulateObject;
import rpg.server.util.io.XmlUtils;


/**
 * 仿真对象关系抽象类		<br/>
 * 理论上仿真对象提供任何获取其它对象的接口，都可以作为一种关系
 *
 */
public abstract class AbstractRelation {

	/**
	 * 获取与仿真对象self满足本关系的仿真对象集合
	 * @param self
	 * @return
	 */
	public abstract Set<SimulateObject> getRelated(SimulateObject self);
	
	/**
	 * 从xml节点解析关系
	 * @param e
	 */
	public abstract void parse(Element e);
	
	/**
	 * 工厂方法	<br/>
	 * 从xml节点解析子类
	 * @param e
	 * @return
	 */
	public static final AbstractRelation parseRel(Element e) {
		AbstractRelation rel = null;
		String mode = XmlUtils.getAttribute(e, "mode");
		if (mode.equalsIgnoreCase("tag"))
			rel = new TagRelation();
		else if (mode.equalsIgnoreCase("condtag"))
			rel = new CondTagRelation();
		else if (mode.equalsIgnoreCase("neighbour"))
			rel = new GOBSomeNeighbourRelation();
		// more sub classes here, if any
		rel.parse(e);
		return rel;
	}
}
