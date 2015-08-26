package rpg.server.module.player;

import java.util.Map;
import java.util.Set;

import rpg.server.core.action.SimpleGameAction;
import rpg.server.core.attribute.FormulaAttrConfig;
import rpg.server.core.condition.SimpleGameCondition;
import rpg.server.core.event.GameEvent;
import rpg.server.core.event.GameEventChannel;
import rpg.server.core.obj.GameObject;
import rpg.server.core.obj.GameObjectAdapter;
import rpg.server.core.relation.AbstractRelation;
import rpg.server.core.relation.SOBRelationTag;
import rpg.server.core.script.GameScriptConfig;
import rpg.server.gen.proto.MsgUtil;
import rpg.server.net.NetHandler;

import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

public class PlayerCharacter extends GameObjectAdapter {
	/**
	 * 网络连接
	 */
	private NetHandler netHandler;

	/**
	 * 发送协议到客户端
	 * 
	 * @param msg
	 *            协议
	 * 
	 */
	public void sendMsg(Message msg) {
		netHandler.sendMsg(MsgUtil.getIdByClass(msg.getClass()),
				msg.toByteArray());
	}

	/**
	 * 发送协议到客户端
	 * 
	 * @param builder
	 *            协议
	 * 
	 */
	public void sendMsg(Builder builder) {
		this.sendMsg(builder.build());
	}

	/**
	 * 处理客户端上行协议<br>
	 */
	private void handleMsg() {
	}

	@Override
	public GameObject getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFriend(GameObject obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void tick() {
		// TODO 处理上行协议

	}

	@Override
	public void receiveEvent(GameEventChannel source, GameEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyEvent(String related, GameEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkSimpleCondition(SimpleGameCondition condition,
			Map<String, Object> vars) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doSimpleAction(SimpleGameAction action,
			Map<String, Object> vars) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Long> addScript(GameScriptConfig script) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Long> addScript(GameScriptConfig script, Map<String, Object> vars) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<GameObject> getRelatedSOBs(AbstractRelation relation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<GameObject> getRelatedSOBs(SOBRelationTag relation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FormulaAttrConfig getAttrConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}
