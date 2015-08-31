package rpg.server.module.account;

import java.util.HashMap;
import java.util.Map;

import rpg.server.core.MsgHandler;
import rpg.server.db.entity.PlayerDB;
import rpg.server.gen.proto.Account.C_LOGIN;
import rpg.server.gen.proto.Common;
import rpg.server.gen.proto.Common.D_PLAYER;
import rpg.server.gen.proto.MsgUtil;
import rpg.server.net.ConnectionStatus;
import rpg.server.net.NetHandler;

import com.google.protobuf.GeneratedMessage;

/**
 * 玩家账号相关信息<br>
 * 
 */
public class Account {
	/** 网络连接 */
	private NetHandler net;

	/** 角色列表 */
	private Map<Long, PlayerDB> playerMap = new HashMap<Long, PlayerDB>();

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
		if (net == null || ConnectionStatus.LOGIN != net.getState()) {
			return true;
		}
		GeneratedMessage msg = net.getMsg();
		if (msg != null) {
			int msgId = MsgUtil.getIdByClass(msg.getClass());
			switch (msgId) {
			case MsgUtil.C_LOGIN:
				break;
			}
			// TODO 处理协议
		}
		return false;
	}

	@MsgHandler(C_LOGIN.class)
	public void accountLogin(C_LOGIN msg) {
		PlayerDB pdb = new PlayerDB();
		pdb.setId(10086);
		pdb.setGender(1);
		pdb.setLevel(1);
		pdb.setName("test");
		pdb.setProfession(1);
		this.playerMap.put(pdb.getId(), pdb);
		D_PLAYER dpmsg = this.playerDBToMsg(pdb);
		net.sendMsg(MsgUtil.getIdByClass(dpmsg.getClass()), dpmsg.toByteArray());
	}

	private void delPlayer(long PlayerId) {

	}

	private void createPlayer(long PlayerId) {

	}

	private void login(long PlayerId) {

	}

	private D_PLAYER playerDBToMsg(PlayerDB pdb) {
		Common.D_PLAYER.Builder pb = Common.D_PLAYER.newBuilder();
		pb.setId(pdb.getId());
		pb.setName(pdb.getName());
		pb.setLevel(pdb.getLevel());
		pb.setGender(pdb.getGender());
		pb.setProfession(pdb.getGender());
		return pb.build();
	}
}
