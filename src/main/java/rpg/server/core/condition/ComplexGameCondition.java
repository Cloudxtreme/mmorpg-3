package rpg.server.core.condition;

import java.util.Map;

import rpg.server.core.obj.GameObject;

import org.w3c.dom.Element;

import rpg.server.util.io.XmlUtils;

/**
 * 复合条件 <br/>
 * 至少应有两个子句，否则应退化为SimpleGameCondition<br/>
 * 资源文件格式:<br/>
 * &lt;condition mode="指明模式(目前支持AND,OR)"&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;以下依次列出子条件，子条件个数至少应为2，否则，应退化为简单条件<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;condition
 * mode="子条件的模式,可以是简单条件,也可以继续嵌套复合条件,但最内层必须是一个简单条件"&gt;子条件内容&lt;/condition&gt;<br/>
 * &lt;/condition&gt;
 */
public class ComplexGameCondition extends GameCondition {

	/** 条件子句，数组长度至少应为2 */
	private GameCondition[] conditions;

	public ComplexGameCondition(ConditionMode mode) {
		super(mode);
	}

	@Override
	void load(Element e) throws Exception {
		// 载入条件子句
		Element[] list = XmlUtils.getChildrenByName(e, "condition");
		conditions = new GameCondition[list.length];
		for (int i = 0; i < list.length; i++) {
			conditions[i] = GameCondition.parseCondition(list[i]);
		}
	}

	@Override
	public boolean check(GameObject sob, Map<String, Object> vars) {
		switch (getMode()) {
		case AND:
			for (GameCondition c : conditions) {
				if (!c.check(sob, vars))
					return false;
			}
			return true;
		case OR:
			for (GameCondition c : conditions) {
				if (c.check(sob, vars))
					return true;
			}
			return false;
		default:
			return false;
		}
	}

	@Override
	public String getUI() {
		StringBuilder sb = new StringBuilder();
		String con = (getMode() == ConditionMode.AND) ? "&" : "|| ";
		for (int i = 0; i < conditions.length - 1; i++)
			sb.append(conditions[i].getUI()).append(con);
		sb.append(conditions[conditions.length - 1].getUI());
		return sb.toString();
	}

}
