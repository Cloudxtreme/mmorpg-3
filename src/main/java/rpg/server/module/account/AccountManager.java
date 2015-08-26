package rpg.server.module.account;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rpg.server.net.NetHandler;

public class AccountManager {
	private static AccountManager instance = new AccountManager();
	/** 队列用户 */
	private List<Account> accounts = new ArrayList<Account>();

	private AccountManager() {
	}

	public static AccountManager getInstance() {
		return instance;
	}

	public void tick() {
		synchronized (accounts) {
			Iterator<Account> iter = accounts.iterator();
			while (iter.hasNext()) {
				if (iter.next().tick()) {
					iter.remove();
				}
			}
		}
	}

	/**
	 * 客户端建立连接<br>
	 * 
	 * @param handler
	 *            连接
	 */
	public void onAccept(NetHandler handler) {
		Account ac = new Account(handler);
		synchronized (accounts) {
			accounts.add(ac);
		}
	}
}
