package rpg.server.module.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rpg.server.core.MsgHandler;
import rpg.server.core.obj.GameObjectManager;
import rpg.server.gen.db.model.DemoHuman;
import rpg.server.gen.db.model.DemoHumanExample;
import rpg.server.gen.db.service.DemoHumanService;
import rpg.server.gen.proto.Account.C_LOGIN;
import rpg.server.gen.proto.Account.C_PLAYER_SEL;
import rpg.server.gen.proto.Account.S_LOGIN;
import rpg.server.gen.proto.Common;
import rpg.server.gen.proto.Common.D_PLAYER;
import rpg.server.gen.proto.MsgUtil;
import rpg.server.module.player.LoginStatus;
import rpg.server.net.NetHandler;
import rpg.server.util.log.Log;

import com.google.protobuf.GeneratedMessage;

/**
 * 玩家账号相关信息<br>
 * 
 */
public class Account {
	/** 网络连接 */
	private NetHandler net;

	/** 角色列表 */
	private Map<Long, DemoHuman> playerMap = new HashMap<Long, DemoHuman>();

	/**
	 * 构造方法,应只在AccountManager中调用<br>
	 * 
	 * @param net
	 *            网络连接
	 */
	Account(NetHandler net) {
		this.net = net;
	}

	/**
	 * 返回true表示玩家登录,或者被断开
	 * 
	 * @return true:在队列中移除,false:在队列中保留
	 */
	public boolean tick() {
		// 网络状态不对
		if (net == null) {
			return true;
		}
		GeneratedMessage msg = net.getMsg();
		if (msg != null) {
			int msgId = MsgUtil.getIdByClass(msg.getClass());
			switch (msgId) {
			case MsgUtil.C_LOGIN:// 客户端登录
				this.accountLogin((C_LOGIN) msg);
				break;
			case MsgUtil.C_PLAYER_SEL:// 选择角色
				long playerId = ((C_PLAYER_SEL) msg).getId();
				return this.login(playerId);
			}
			// TODO 处理协议
		}
		return false;
	}

	@MsgHandler(C_LOGIN.class)
	public void accountLogin(C_LOGIN msg) {
		DemoHumanExample example = new DemoHumanExample();
		example.createCriteria().andAccountEqualTo(msg.getAccount());
		DemoHumanService.selectByExample(example, (boolean flag,
				List<DemoHuman> humanList, Exception e) -> {
			if (flag) {
				S_LOGIN.Builder sLogin = S_LOGIN.newBuilder();
				for (DemoHuman human : humanList) {
					sLogin.addPlayerList(this.playerDBToMsg(human));
					this.playerMap.put(human.getId(), human);
				}
				net.sendMsg(sLogin.build());
			} else {
				Log.game.error("login error.", e);
			}
		});
	}

	private void delPlayer(long PlayerId) {

	}

	private void createPlayer(long PlayerId) {

	}

	private boolean login(long playerId) {
		DemoHuman dh = this.playerMap.get(playerId);
		if (dh != null) {
			GameObjectManager.getInstance().playerLogin(playerId, net);
			return true;
		}
		return false;
	}

	private D_PLAYER playerDBToMsg(DemoHuman human) {
		Common.D_PLAYER.Builder pb = Common.D_PLAYER.newBuilder();
		pb.setId(human.getId());
		pb.setName(human.getName());
		pb.setLevel(human.getLevel());
		pb.setGender(human.getSex());
		pb.setProfession(human.getProfession());
		return pb.build();
	}

}
