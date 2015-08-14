package rpg.server.core.relation;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.w3c.dom.Element;

import rpg.server.core.obj.GameObject;
import rpg.server.util.io.XmlUtils;

/**
 * 根据tag指定的关联关系
 * 
 * @see SOBRelationTag
 */
public class TagRelation extends AbstractRelation {

	/*
	 * 资源文件格式： <object mode="tag"> <tag>关系标识</tag> </object>
	 */

	@Override
	public Set<GameObject> getRelated(GameObject self) {
		Set<GameObject> set = new TreeSet<GameObject>();
		GameObject sob = self;
		outer: for (int i = 0; i < relations.length; i++) {
			for (int j = 0; j < relations[i].size() - 1; j++) { // 先处理关系标识链的前n-1项，保证取到一对一
				Set<GameObject> subset = sob.getRelatedSOBs(relations[i]
						.get(j));
				if (subset == null || subset.size() == 0) // 关系标识链中的某一项断掉，整条标识失效，直接进行下一条标识
					continue outer;
				sob = ((TreeSet<GameObject>) subset).first();
			}
			Set<GameObject> subset = sob.getRelatedSOBs(relations[i]
					.get(relations[i].size() - 1)); // 处理标识链的最后一项
			if (subset != null) {
				set.addAll(subset);
			}
		}
		return set;
	}

	@Override
	public void parse(Element e) {
		try {
			relations = SOBRelationTag.parse(XmlUtils.getChildText(e, "tag")
					.trim());
		} catch (Exception e1) {
			// TODO 记录LOG
		}
	}

	private List<SOBRelationTag>[] relations;
}
