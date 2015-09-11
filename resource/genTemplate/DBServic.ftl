package ${servicePackage};

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.session.SqlSession;

import rpg.server.db.DBOpCallback;
import rpg.server.db.DBServer;
import ${daoPackage}.${objName}Mapper;
import ${modelPackage}.${objName};
import ${modelPackage}.${objName}Example;

public class ${objName}Service {

	private static final ExecutorService es = Executors
			.newSingleThreadExecutor();

	<#if enableCountByExample>
	public static void countByExample(${objName}Example example,
			DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				${objName}Mapper mapper = session
						.getMapper(${objName}Mapper.class);
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
	</#if>

	<#if enableDeleteByExample>
	public static void deleteByExample(${objName}Example example,
			DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				${objName}Mapper mapper = session
						.getMapper(${objName}Mapper.class);
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
	</#if>
	
	<#if enableDeleteByPrimaryKey>
	public static void deleteByPrimaryKey(<#list primaryKeyColumns as pk>${pk.javaType} ${pk.name}<#if pk_has_next>,</#if></#list>, DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				${objName}Mapper mapper = session
						.getMapper(${objName}Mapper.class);
				int i = mapper.deleteByPrimaryKey(<#list primaryKeyColumns as pk>${pk.name}<#if pk_has_next>,</#if></#list>);
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
	</#if>

	<#if enableInsert>
	public static void insert(${objName} record, DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				${objName}Mapper mapper = session
						.getMapper(${objName}Mapper.class);
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

	public static void insertSelective(${objName} record,
			DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				${objName}Mapper mapper = session
						.getMapper(${objName}Mapper.class);
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
	</#if>

	<#if enableSelectByExample>
	public static void selectByExample(${objName}Example example,
			DBOpCallback<List<${objName}>> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				${objName}Mapper mapper = session
						.getMapper(${objName}Mapper.class);
				List<${objName}> list = mapper.selectByExample(example);
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
	</#if>

	<#if enableSelectByPrimaryKey>
	public static void selectByPrimaryKey(<#list primaryKeyColumns as pk>${pk.javaType} ${pk.name}<#if pk_has_next>,</#if></#list>,
			DBOpCallback<${objName}> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				${objName}Mapper mapper = session
						.getMapper(${objName}Mapper.class);
				${objName} result = mapper.selectByPrimaryKey(<#list primaryKeyColumns as pk>${pk.name}<#if pk_has_next>,</#if></#list>);
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
	</#if>

	<#if enableUpdateByExample>
	public static void updateByExampleSelective(${objName} record,
			${objName}Example example, DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				${objName}Mapper mapper = session
						.getMapper(${objName}Mapper.class);
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

	public static void updateByExample(${objName} record,
			${objName}Example example, DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				${objName}Mapper mapper = session
						.getMapper(${objName}Mapper.class);
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
	</#if>
	
	<#if enableUpdateByPrimaryKey>
	public static void updateByPrimaryKeySelective(${objName} record,
			DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				${objName}Mapper mapper = session
						.getMapper(${objName}Mapper.class);
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

	public static void updateByPrimaryKey(${objName} record,
			DBOpCallback<Integer> handler) {
		es.execute(() -> {
			SqlSession session = null;
			try {
				session = DBServer.getInstance().getSqlSessionFactory()
						.openSession();
				${objName}Mapper mapper = session
						.getMapper(${objName}Mapper.class);
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
	</#if>

}
