package rpg.server.core.task;

import static org.quartz.JobKey.jobKey;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

/**
 * 任务管理器<br>
 * 可以添加表达式任务和定时任务.<br>
 * 将在主线程中执行
 */
public class TaskManager {
	/** 任务队列 */
	private ConcurrentLinkedQueue<ScheduleTask> schedulerList = new ConcurrentLinkedQueue<>();
	// 任务队列调度器
	private Scheduler scheduler;
	private static TaskManager instance = new TaskManager();

	private TaskManager() {
	}

	public static TaskManager getInstance() {
		return instance;
	}

	public void init() throws SchedulerException {
		this.scheduler = new StdSchedulerFactory().getScheduler();
		this.scheduler.start();
	}

	/**
	 * 周期行任务
	 */
	public void tick() {
		for (ScheduleTask task = schedulerList.poll(); task != null;) {
			task.execute();
		}
	}

	/**
	 * 延迟delay毫秒之后执行任务
	 * 
	 * @param task
	 *            任务
	 * @param delay
	 *            延迟,毫秒
	 * @throws SchedulerException
	 */
	public void scheduleOnce(ScheduleTask task, long delay)
			throws SchedulerException {
		task.triggerAt = System.currentTimeMillis() + delay;
		// 定义trigger
		SimpleScheduleBuilder sche = SimpleScheduleBuilder
				.repeatSecondlyForever();
		// 循环次数 设置为0 代表不多余循环只执行一次
		sche.withRepeatCount(0);
		// 添加任务
		scheduleUtils(task, sche, delay);
	}

	/**
	 * 从指定的delay毫秒延迟之后，开始以重复的速率每period毫秒执行
	 * 
	 * @param task
	 *            任务
	 * @param delay
	 *            延迟,毫秒
	 * @param period
	 *            周期,毫秒
	 * @throws SchedulerException
	 */
	public void schedulePeriod(ScheduleTask task, long delay, long period)
			throws SchedulerException {
		task.triggerAt = System.currentTimeMillis() + delay;
		task.triggerPeriod = period;

		// 定义schedule
		SimpleScheduleBuilder sche = SimpleScheduleBuilder
				.repeatSecondlyForever();
		// 执行间隔
		sche.withIntervalInMilliseconds(period);
		// 添加任务
		scheduleUtils(task, sche, delay);
	}

	/**
	 * 从指定的delay毫秒延迟之后，开始以重复的速率每period毫秒执行，执行count次
	 * 
	 * @param task
	 *            任务
	 * @param delay
	 *            延迟,毫秒
	 * @param period
	 *            周期,毫秒
	 * @param count
	 *            次数
	 * @throws SchedulerException
	 */
	public void scheduleRepeatForTotalCount(ScheduleTask task, long delay,
			long period, int count) throws SchedulerException {
		task.triggerAt = System.currentTimeMillis() + delay;
		task.triggerPeriod = period;

		// 定义schedule
		SimpleScheduleBuilder sche = SimpleScheduleBuilder
				.repeatSecondlyForTotalCount(count);
		// 执行间隔
		sche.withIntervalInMilliseconds(period);

		// 添加任务
		scheduleUtils(task, sche, delay);
	}

	/**
	 * 添加时间任务队列 支持cron格式
	 * 
	 * @param task
	 *            任务
	 * @param cronStr
	 *            时间表达式
	 * @throws SchedulerException
	 */
	public void scheduleCron(ScheduleTask task, String cronStr)
			throws SchedulerException {
		task.setTriggerCronStr(cronStr);
		// 定义schedule
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
				.cronSchedule(cronStr);
		// 添加任务
		scheduleUtils(task, scheduleBuilder, 0);
	}

	/**
	 * @throws SchedulerException
	 */
	private void scheduleUtils(ScheduleTask task,
			ScheduleBuilder<?> scheduleBuilder, long delay)
			throws SchedulerException {
		// 开始执行时间
		Date startAt = new Date();
		if (delay > 0) {
			startAt = new Date(System.currentTimeMillis() + delay);
		}
		// 定义时间调度的job内容
		JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class)
				.withIdentity(task.getJobKey()).build();
		jobDetail.getJobDataMap().put("task", task);
		jobDetail.getJobDataMap().put("scheduler", schedulerList);
		// 创建最终trigger
		Trigger trigger = TriggerBuilder.newTrigger().startAt(startAt)
				.withSchedule(scheduleBuilder).build();
		// 绑定job和trigger
		scheduler.scheduleJob(jobDetail, trigger);

		// 设置任务信息
		task.trigger = trigger;
	}

	/**
	 * 删除指定名称的任务
	 * 
	 * @param jobName
	 *            任务名称
	 * @throws SchedulerException
	 */
	public void deleteSchedulerJob(String jobName) throws SchedulerException {
		scheduler.deleteJob(jobKey(jobName, null));
	}

	/**
	 * 删除指定任务组上任务
	 * 
	 * @param jobName
	 *            任务名称
	 * @param jobGroupName
	 *            任务组
	 * @throws SchedulerException
	 */
	public void deleteSchedulerJob(String jobName, String jobGroupName)
			throws SchedulerException {
		scheduler.deleteJob(jobKey(jobName, jobGroupName));
	}

	/**
	 * 删除任务组
	 * 
	 * @param jobGroupName
	 *            任务组
	 * @throws SchedulerException
	 */
	public void deleteSchedulerJobsByGroup(String jobGroupName)
			throws SchedulerException {
		for (String group : scheduler.getJobGroupNames()) {
			if (!group.equals(jobGroupName))
				continue;
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher
					.jobGroupEndsWith(group))) {
				scheduler.deleteJob(jobKey);
			}
		}
	}
}
