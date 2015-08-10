package rpg.server.util.task;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 任务管理器（单例）<br/>
 * 分类管理所有线程
 * 
 */
public class TaskManager {

	/**
	 * 任务类别
	 * 
	 * @author lincy
	 * 
	 */
	public enum TaskType {
		WORLD, // 主线程任务
		NET, // 网络任务
		DB, // 数据库任务
		OTHER,//其他任务
		REPORT,	//统计任务
	}

	public static final TaskManager getInstance() {
		return instance;
	}

	public final void shutdown() {
		main_thread.cancel();
		net_processors.shutdown();
		db_processors.shutdown();
		pool.shutdown();
		report_pool.shutdown();
	}

	/**
	 * 添加立即执行的任务
	 * 
	 * @param task
	 * @param type
	 *            任务类别
	 */
	public void addTask(Runnable task, TaskType type) {
		switch (type) {
		case NET:
			net_processors.execute(task);
			break;
		case DB:
			db_processors.execute(task);
			break;
		case OTHER:
			pool.execute(task);
			break;
		case REPORT:
			report_pool.execute(task);
			break;
		case WORLD:
			if(task instanceof TimerTask) {
				main_thread.schedule((TimerTask) task, 0);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 添加Sheduled任务
	 * 
	 * @param task
	 * @param type
	 *            任务类别
	 * @param initialDelay
	 */
	public void addScheduledTask(TaskForSchedule task, TaskType type,
			long initialDelay) {
		switch (type) {
		case WORLD:
			main_thread.schedule(new ScheduledTimerTask(task), initialDelay);
			break;
		case NET:
			task.setFuture(net_processors.schedule(
					new ScheduledTimerTask(task), initialDelay,
					TimeUnit.MILLISECONDS));
			break;
		case DB:
			task.setFuture(db_processors.schedule(new ScheduledTimerTask(task),
					initialDelay, TimeUnit.MILLISECONDS));
			break;
		case OTHER:
			task.setFuture(pool.schedule(new ScheduledTimerTask(task),
					initialDelay, TimeUnit.MILLISECONDS));
			break;
		case REPORT:
			task.setFuture(report_pool.schedule(new ScheduledTimerTask(task),
					initialDelay, TimeUnit.MILLISECONDS));
			break;
		default:
			break;
		}
	}

	/**
	 * 添加repeatable Sheduled任务
	 * 
	 * @param task
	 * @param type
	 *            任务类别
	 * @param initialDelay
	 * @param period
	 */
	public void addScheduledTask(TaskForSchedule task, TaskType type,
			long initialDelay, long period) {
		switch (type) {
		case WORLD:
			main_thread.scheduleAtFixedRate(new ScheduledTimerTask(task),
					initialDelay, period);
			break;
		case NET:
			task.setFuture(net_processors.scheduleAtFixedRate(
					new ScheduledTimerTask(task), initialDelay, period,
					TimeUnit.MILLISECONDS));
			break;
		case DB:
			task.setFuture(db_processors.scheduleAtFixedRate(
					new ScheduledTimerTask(task), initialDelay, period,
					TimeUnit.MILLISECONDS));
			break;
		case OTHER:
			task.setFuture(pool.scheduleAtFixedRate(
					new ScheduledTimerTask(task), initialDelay, period,
					TimeUnit.MILLISECONDS));
			break;
		case REPORT:
			task.setFuture(report_pool.scheduleAtFixedRate(
					new ScheduledTimerTask(task), initialDelay, period,
					TimeUnit.MILLISECONDS));
			break;
		default:
			break;
		}
	}

	private TaskManager() {
		// 初始化网络
		net_processors = Executors.newScheduledThreadPool(10);
		// 初始化数据库线程池
		db_processors = Executors.newScheduledThreadPool(100);
		// 初始化其它线程池
		pool = Executors.newScheduledThreadPool(1000);
		report_pool = Executors.newScheduledThreadPool(100);
		// 初始化主线程
		main_thread = new Timer("GameWorld");
	}

	// 本类单例
	private static final TaskManager instance = new TaskManager();

	// 主线程(单线程，通过Timer实现任务管理)
	private Timer main_thread;

	// 网络线程
	private ScheduledExecutorService net_processors;

	// 数据库线程池
	private ScheduledExecutorService db_processors;

	// 其它异步任务线程池
	private ScheduledExecutorService pool;

	// 其它异步任务线程池
	private ScheduledExecutorService report_pool;

	/**
	 * @return the main_thread
	 */
	public Timer getMain_thread() {
		return main_thread;
	}

	/**
	 * @return the net_processors
	 */
	public ScheduledExecutorService getNet_processors() {
		return net_processors;
	}

	/**
	 * @return the db_processors
	 */
	public ScheduledExecutorService getDb_processors() {
		return db_processors;
	}

	/**
	 * @return the pool
	 */
	public ScheduledExecutorService getPool() {
		return pool;
	}

	public ScheduledExecutorService getReport_pool() {
		return report_pool;
	}

}
