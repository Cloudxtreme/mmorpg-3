package rpg.server.core.relation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.w3c.dom.Element;

import rpg.server.core.condition.GameCondition;
import rpg.server.core.obj.GameObject;
import rpg.server.core.obj.GameObject;
import rpg.server.module.player.PlayerCharacter;
import rpg.server.util.MathUtil;
import rpg.server.util.io.XmlUtils;


/**
 * 邻居范围中的若干个满足条件的对象
 */
public class GOBSomeNeighbourRelation extends AbstractRelation {

	/*
	 * 资源文件格式：
	 * <object mode="neighbour">
	 * 	<target>适用目标</target>	<!--false：自己（缺省），true：目标-->
	 * 	<range>距离范围</range>	<!--可缺省，表示所有邻居-->
	 *  <friend>敌友关系</friend>	<!--0友方，1敌方，2无限制（缺省）-->
	 * 	<condition/>	<!--详细格式见condition.xml-->
	 *  <count>个数</count>	<!--可缺省，取群体技能数量上限-->
	 *  <select>选取原则</select>	<!--0随机，1就近（缺省）-->
	 *  <include>是否包含自身</include>	<!--true包含（缺省），false不含-->
	 *  <playerOnly>仅仅选中player</playerOnly>	<!--true只选择邻居中的player（缺省），false包含npc等-->
	 * </object>
	 */
	
	public GOBSomeNeighbourRelation(boolean target, int range, byte friend,
			GameCondition cond, int count, byte select, boolean include, boolean playerOnly) {
		super();
		this.target = target;
		this.range = range;
		this.friend = friend;
		this.cond = cond;
		this.count = count;
		this.select = select;
		this.include = include;
		this.playerOnly = playerOnly;
	}
	
	public GOBSomeNeighbourRelation() {
	}

	public Set<GameObject> getRelated(GameObject self) {
		if (self instanceof GameObject) {
			final GameObject s = target ? ((GameObject) self).getTarget() : (GameObject) self;
			if (s == null)
				return null;
			Set<GameObject> set = s.getRelatedSOBs(SOBRelationTag.NEIGHBOR);
			Set<GameObject> gobset = new TreeSet<GameObject>(new Comparator<GameObject>() {

				
				public int compare(GameObject arg0, GameObject arg1) {
					int dd = MathUtil.distance(s.getX(), s.getY(), arg0.getX(), arg0.getY())
						- MathUtil.distance(s.getX(), s.getY(), arg1.getX(), arg1.getY());
					if (dd != 0)
						return dd;
					return (int) Math.signum(arg0.getId() - arg1.getId());
				}
				
			});
			Iterator<GameObject> it = set.iterator();
			while (it.hasNext()) {
				GameObject sob = it.next();
				if (sob instanceof GameObject) {
					GameObject gob = (GameObject) sob;
					if (playerOnly && !(gob instanceof PlayerCharacter)) {
						continue;
					}
					if (range > 0 && !MathUtil.isInRange(s.getX(), s.getY(), gob
							.getX(), gob.getY(), range)) {
						continue;
					}
					if (friend == 1 && ((GameObject) self).isFriend(gob) || friend == 0 && !((GameObject) self).isFriend(gob)) {
						continue;
					}
					if (cond != null) {
						Map<String, Object> map = new HashMap<String, Object>(0);
						map.put("source", self);
						map.put("target", s);
						if (!cond.check(gob, map))
							continue;
					}
					gobset.add(gob);
				}
			}
			set.clear();
			if (gobset.size() > count) {
				GameObject[] gobarr = gobset.toArray(new GameObject[0]);
				switch (select) {
				case 0:
					boolean[] chosen = MathUtil.randMOutofN(count, gobarr.length);
					for (int i = 0; i < gobarr.length; i++) {
						if (chosen[i])
							set.add(gobarr[i]);
					}
					break;
				case 1:
					for (int i = 0; i < count; i++)
						set.add(gobarr[i]);
					break;
				}
			} else {
				set.addAll(gobset);
			}
			if (include) {
				set.add(s);
			}
			return set;
		}
		return null;
	}

	public void parse(Element e) {
		if (XmlUtils.getChildByName(e, "target") != null)
			target = Boolean.parseBoolean(XmlUtils.getChildText(e, "target"));
		if (XmlUtils.getChildByName(e, "range") != null)
			range = Integer.parseInt(XmlUtils.getChildText(e, "range"));
		if (XmlUtils.getChildByName(e, "friend") != null)
			friend = Byte.parseByte(XmlUtils.getChildText(e, "friend"));
		if (XmlUtils.getChildByName(e, "condition") != null) {
			try {
				cond = GameCondition.parseCondition(XmlUtils.getChildByName(e, "condition"));
			} catch (Exception e1) {
				// TODO 记录LOG
			}
		}
		if (XmlUtils.getChildByName(e, "count") != null) {
			count = Integer.parseInt(XmlUtils.getChildText(e, "count"));
		} else {
//			count = SkillManager.AOE_MAX_COUNT;
		}
		if (XmlUtils.getChildByName(e, "select") != null) 
			select = Byte.parseByte(XmlUtils.getChildText(e, "select"));
		if (XmlUtils.getChildByName(e, "include") != null)
			include = Boolean.parseBoolean(XmlUtils.getChildText(e, "include"));
		if (XmlUtils.getChildByName(e, "playerOnly") != null)
			playerOnly = Boolean.parseBoolean(XmlUtils.getChildText(e, "playerOnly"));
	}

	private boolean target = false;
	private int range = -1;
	private byte friend = 2;
	private GameCondition cond = null;
	private int count = -1;
	private byte select = 1;
	private boolean include = true;
	private boolean playerOnly = false;
	
}
