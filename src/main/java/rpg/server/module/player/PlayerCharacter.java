package rpg.server.module.player;

import java.util.Map;
import java.util.Set;

import rpg.server.core.action.ActionHandler;
import rpg.server.core.action.SimpleGameAction;
import rpg.server.core.condition.ConditionHandler;
import rpg.server.core.condition.SimpleGameCondition;
import rpg.server.core.event.EventHandler;
import rpg.server.core.event.GameEvent;
import rpg.server.core.event.GameEvent.Channel;
import rpg.server.core.event.GameEvent.Type;
import rpg.server.core.obj.GameObject;
import rpg.server.core.obj.SimulateObject;
import rpg.server.core.relation.AbstractRelation;
import rpg.server.core.relation.SOBRelationTag;
import rpg.server.core.script.GameScriptConfig;

public class PlayerCharacter implements GameObject {

	@Override
	public Set<SimulateObject> getRelatedSOBs(AbstractRelation relation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<SimulateObject> getRelatedSOBs(SOBRelationTag relation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerEventHandler(Type type, Channel source,
			EventHandler agent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEventHandler(Type type, Channel source, EventHandler agent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerEventHandlers(Type[] types, Channel channel,
			EventHandler handler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEventHandlers(Type[] types, Channel channel,
			EventHandler handler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveEvent(Channel source, GameEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyEvent(String related, GameEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerConditionChecker(String classify, ConditionHandler agent) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkSimpleCondition(SimpleGameCondition condition,
			Map<String, Object> vars) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerActionProcessor(String classify, ActionHandler agent) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean doSimpleAction(SimpleGameAction action) {
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
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public GameObject getTarget() {
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

}
