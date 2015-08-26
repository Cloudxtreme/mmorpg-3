package rpg.server.core.task;

import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.utils.Key;

import rpg.server.util.Params;
import rpg.server.util.StringUtil;

public abstract class ScheduleTask {
	protected JobKey jobKey;
	protected Trigger trigger;
	protected long triggerAt; // 触发时间,cron类型为0
	protected long triggerPeriod; // 触发间隔,cron类型为0
	/** 任务名称 */
	protected String jobName;
	/** 任务组 */
	protected String jobGroup;
	/** 参数 */
	protected Params params;
	protected String triggerCronStr; // cron时间串，""表示非cron触发

	public ScheduleTask(Params params) {
		if (params == null) {
			this.params = new Params();
		}
		jobName = params.get("jobName");
		jobGroup = params.get("jobGroup");
		// 设置jobKey
		if (getJobName() == null) {
			jobKey = new JobKey(Key.createUniqueName(getJobGroup()),
					getJobGroup());
		} else {
			jobKey = JobKey.jobKey(getJobName(), getJobGroup());
		}
	}

	/**
	 * 执行调度任务
	 */
	public abstract void execute();

	/**
	 * 下次执行时间
	 * 
	 * @return 下一次执行时间
	 */
	public long getNextExecuteTime() {
		// cron调度返回下次执行时间
		if (StringUtil.isEmpty(triggerCronStr)) {
			return trigger.getNextFireTime().getTime();
		} else {
			// 一般调度返回执行时间
			return triggerAt;
		}
	}

	public String getJobName() {
		return jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public Params getParams() {
		return params;
	}

	public String getTriggerCronStr() {
		return triggerCronStr;
	}

	public void setTriggerCronStr(String triggerCronStr) {
		this.triggerCronStr = triggerCronStr;
	}

	public JobKey getJobKey() {
		return jobKey;
	}

}
