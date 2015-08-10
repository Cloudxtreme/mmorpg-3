package rpg.server.core.relation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import rpg.server.core.condition.GameCondition;
import rpg.server.core.obj.SimulateObject;
import rpg.server.util.io.XmlUtils;

/**
 * 在TagRelation基础上加强的一种关系 <br/>
 * 即根据Tag找到一堆对象之后，还应满足一定的条件才被选中 <br/>
 * 
 */
public class CondTagRelation extends TagRelation {

	/*
	 * 资源文件格式： <object mode="condtag"> <tag>关系标识</tag> <condition/>
	 * <!--详细格式见condition.xml--> </object>
	 */

	@Override
	public Set<SimulateObject> getRelated(SimulateObject self) {
		Set<SimulateObject> set = super.getRelated(self);
		Map<String, Object> tempvar = new HashMap<String, Object>(0);
		tempvar.put("theOther", self); // 用于双方关系类的条件检测（例如：距离范围、仇恨关系等）
		Iterator<SimulateObject> it = set.iterator();
		while (it.hasNext()) {
			SimulateObject sob = it.next();
			if (!cond.check(sob, tempvar))
				it.remove();
		}
		return set;
	}

	@Override
	public void parse(Element e) {
		super.parse(e);
		try {
			cond = GameCondition.parseCondition(XmlUtils.getChildByName(e,
					"condition"));
		} catch (Exception e1) {
			// TODO 记录LOG
		}
	}

	private GameCondition cond;
}
