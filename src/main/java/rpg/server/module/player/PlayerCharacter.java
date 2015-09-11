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
import rpg.server.gen.agent.AgentUtil;
import rpg.server.gen.db.model.DemoHuman;
import rpg.server.gen.db.service.DemoHumanService;
import rpg.server.gen.proto.MsgUtil;
import rpg.server.net.NetHandler;
import rpg.server.util.log.Log;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

public class PlayerCharacter extends GameObjectAdapter {
	/** 网络连接 */
	private NetHandler net;

	/** 登录状态 */
	private LoginStatus loginStatus = LoginStatus.PRE_LOGIN;

	/** 角色基础数据 */
	private DemoHuman playerData;

	/**
	 * 登录<br>
	 * 
	 * @param id
	 *            角色ID
	 * @param netHandler
	 *            网络连接
	 */
	public void login(long id, NetHandler net) {
		setId(id);
		this.net = net;
		AgentUtil.init(this);
		this.loadData();
	}

	private void loadData() {
		DemoHumanService.selectByPrimaryKey(getId(), (boolean flag,
				DemoHuman demoHuman, Exception e) -> {
			if (flag) {
				this.playerData = demoHuman;
				// TODO agent load
			} else {
				Log.game.error("load player data error.id:" + this.getId(), e);
			}
		});
	}

	private boolean isLoadFinish() {
		return false;
	}

	@Override
	public void tick() {
		switch (loginStatus) {
		case PRE_LOGIN:
			break;
		case DB_SUCESS:
			break;
		case DB_FAILED:
			break;
		case LOGIN_SUCCESS:
			this.loginSuccessTick();
			break;
		case PRE_LOGOUT:
			break;
		case LOGOUT:
			break;
		default:
		}
	}

	/**
	 * 登录成功后的tick
	 * 
	 */
	private void loginSuccessTick() {
		if (net != null) {
			GeneratedMessage msg = null;
			while ((msg = net.getMsg()) != null) {
				// 处理协议
			}
		}
	}

	/**
	 * 登出
	 * 
	 * @param immediate
	 *            是否立即注销
	 */
	public void logout(boolean immediate) {

	}

	/**
	 * 发送协议到客户端
	 * 
	 * @param msg
	 *            协议
	 * 
	 */
	public void sendMsg(Message msg) {
		net.sendMsg(MsgUtil.getIdByClass(msg.getClass()), msg.toByteArray());
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
