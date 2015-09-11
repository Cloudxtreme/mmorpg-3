package rpg.server.db;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import rpg.server.core.World;

public class DBServer {
	private static DBServer instance = new DBServer();
	private static final String RES = "dbconfig.xml";
	private SqlSessionFactory sqlSessionFactory;
	/** 处理回调相关 */
	private ConcurrentLinkedQueue<DBResultHandler<?>> dbResultQueue = new ConcurrentLinkedQueue<DBResultHandler<?>>();

	private DBServer() {
	}

	public static DBServer getInstance() {
		return instance;
	}

	public void startup() throws Exception {
		File file = new File(World.getInstance().getResPath(), RES);
		sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(new FileInputStream(file));
	}

	public void shutdown() {
	}

	/**
	 * 是否已经完全关闭
	 * 
	 * @return true:已经关闭
	 */
	public boolean isShutdown() {
		return false;
	}

	public void tick() {
		DBResultHandler<?> rh;
		while ((rh = dbResultQueue.poll()) != null) {
			rh.doAction();
		}
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return this.sqlSessionFactory;
	}

	public <T> void addDBResult(DBOpCallback<T> callback, boolean flag,
			T result, Exception e) {
		this.dbResultQueue
				.add(new DBResultHandler<T>(callback, flag, result, e));
	}

}
