package rpg.server.gen.db.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.session.SqlSession;

import rpg.server.db.DBOpCallback;
import rpg.server.db.DBServer;
import rpg.server.gen.db.dao.DemoHumanMapper;
import rpg.server.gen.db.model.DemoHuman;
import rpg.server.gen.db.model.DemoHumanExample;

public class DemoHumanService {

	private static final ExecutorService es = Executors
			.newSingleThreadExecutor();

	public static void countByExample(DemoHumanExample example,
			DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				DemoHumanMapper mapper = session
						.getMapper(DemoHumanMapper.class);
				int i = mapper.countByExample(example);
				if (handler != null) {// 执行成功
					DBServer.getInstance().addDBResult(handler, true, i, null);
				}
			} catch (Exception e) {// 执行失败
				DBServer.getInstance().addDBResult(handler, false, null, e);
			} finally {
				if (session != null)
					session.close();
			}
		});
	}

	public static void deleteByExample(DemoHumanExample example,
			DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				DemoHumanMapper mapper = session
						.getMapper(DemoHumanMapper.class);
				int i = mapper.deleteByExample(example);
				if (handler != null) {// 执行成功
					DBServer.getInstance().addDBResult(handler, true, i, null);
				}
			} catch (Exception e) {// 执行失败
				DBServer.getInstance().addDBResult(handler, false, null, e);
			} finally {
				if (session != null)
					session.close();
			}
		});
	}
	
	public static void deleteByPrimaryKey(Long id, DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				DemoHumanMapper mapper = session
						.getMapper(DemoHumanMapper.class);
				int i = mapper.deleteByPrimaryKey(id);
				if (handler != null) {// 执行成功
					DBServer.getInstance().addDBResult(handler, true, i, null);
				}
			} catch (Exception e) {// 执行失败
				DBServer.getInstance().addDBResult(handler, false, null, e);
			} finally {
				if (session != null)
					session.close();
			}
		});
	}

	public static void insert(DemoHuman record, DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				DemoHumanMapper mapper = session
						.getMapper(DemoHumanMapper.class);
				int i = mapper.insert(record);
				if (handler != null) {// 执行成功
					DBServer.getInstance().addDBResult(handler, true, i, null);
				}
			} catch (Exception e) {// 执行失败
				DBServer.getInstance().addDBResult(handler, false, null, e);
			} finally {
				if (session != null)
					session.close();
			}
		});
	}

	public static void insertSelective(DemoHuman record,
			DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				DemoHumanMapper mapper = session
						.getMapper(DemoHumanMapper.class);
				int i = mapper.insertSelective(record);
				if (handler != null) {// 执行成功
					DBServer.getInstance().addDBResult(handler, true, i, null);
				}
			} catch (Exception e) {// 执行失败
				DBServer.getInstance().addDBResult(handler, false, null, e);
			} finally {
				if (session != null)
					session.close();
			}
		});
	}

	public static void selectByExample(DemoHumanExample example,
			DBOpCallback<List<DemoHuman>> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				DemoHumanMapper mapper = session
						.getMapper(DemoHumanMapper.class);
				List<DemoHuman> list = mapper.selectByExample(example);
				if (handler != null) {// 执行成功
					DBServer.getInstance().addDBResult(handler, true, list,
							null);
				}
			} catch (Exception e) {// 执行失败
				DBServer.getInstance().addDBResult(handler, false, null, e);
			} finally {
				if (session != null)
					session.close();
			}
		});
	}

	public static void selectByPrimaryKey(Long id,
			DBOpCallback<DemoHuman> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				DemoHumanMapper mapper = session
						.getMapper(DemoHumanMapper.class);
				DemoHuman result = mapper.selectByPrimaryKey(id);
				if (handler != null) {// 执行成功
					DBServer.getInstance().addDBResult(handler, true, result,
							null);
				}
			} catch (Exception e) {// 执行失败
				DBServer.getInstance().addDBResult(handler, false, null, e);
			} finally {
				if (session != null)
					session.close();
			}
		});
	}

	public static void updateByExampleSelective(DemoHuman record,
			DemoHumanExample example, DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				DemoHumanMapper mapper = session
						.getMapper(DemoHumanMapper.class);
				int i = mapper.updateByExampleSelective(record, example);
				if (handler != null) {// 执行成功
					DBServer.getInstance().addDBResult(handler, true, i, null);
				}
			} catch (Exception e) {// 执行失败
				DBServer.getInstance().addDBResult(handler, false, null, e);
			} finally {
				if (session != null)
					session.close();
			}
		});
	}

	public static void updateByExample(DemoHuman record,
			DemoHumanExample example, DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				DemoHumanMapper mapper = session
						.getMapper(DemoHumanMapper.class);
				int i = mapper.updateByExample(record, example);
				if (handler != null) {// 执行成功
					DBServer.getInstance().addDBResult(handler, true, i, null);
				}
			} catch (Exception e) {// 执行失败
				DBServer.getInstance().addDBResult(handler, false, null, e);
			} finally {
				if (session != null)
					session.close();
			}
		});
	}
	
	public static void updateByPrimaryKeySelective(DemoHuman record,
			DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				DemoHumanMapper mapper = session
						.getMapper(DemoHumanMapper.class);
				int i = mapper.updateByPrimaryKeySelective(record);
				if (handler != null) {// 执行成功
					DBServer.getInstance().addDBResult(handler, true, i, null);
				}
			} catch (Exception e) {// 执行失败
				DBServer.getInstance().addDBResult(handler, false, null, e);
			} finally {
				if (session != null)
					session.close();
			}
		});
	}

	public static void updateByPrimaryKey(DemoHuman record,
			DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				DemoHumanMapper mapper = session
						.getMapper(DemoHumanMapper.class);
				int i = mapper.updateByPrimaryKey(record);
				if (handler != null) {// 执行成功
					DBServer.getInstance().addDBResult(handler, true, i, null);
				}
			} catch (Exception e) {// 执行失败
				DBServer.getInstance().addDBResult(handler, false, null, e);
			} finally {
				if (session != null)
					session.close();
			}
		});
	}

}
