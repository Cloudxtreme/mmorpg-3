package rpg.server.core;

/**
 * ID生成<br>
 * 毫秒级时间+数据中心ID+生成器ID+毫秒内序列
 */
public class IdWorker {
	/** 生成器ID */
	private long workerId;
	/** 数据中心ID */
	private long datacenterId;
	/** 自增量 */
	private long sequence = 0L;

	private long twepoch = 1288834974657L;
	/** 机器标识位数 */
	private long workerIdBits = 5L;
	/** 数据中心标识位数 */
	private long datacenterIdBits = 5L;
	/** 机器ID最大值 */
	private long maxWorkerId = -1L ^ (-1L << workerIdBits);
	/** 数据中心ID最大值 */
	private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	/** 毫秒内自增位 */
	private long sequenceBits = 12L;
	/** 机器ID偏左移位 */
	private long workerIdShift = sequenceBits;
	/** 数据中心ID左移位 */
	private long datacenterIdShift = sequenceBits + workerIdBits;
	/** 时间毫秒左移位 */
	private long timestampLeftShift = sequenceBits + workerIdBits
			+ datacenterIdBits;
	/** 自增最大值 */
	private long sequenceMask = -1L ^ (-1L << sequenceBits);
	/***/
	private long lastTimestamp = -1L;

	public IdWorker(long workerId, long datacenterId) {
		// sanity check for workerId
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format(
					"worker Id can't be greater than %d or less than 0",
					maxWorkerId));
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(String.format(
					"datacenter Id can't be greater than %d or less than 0",
					maxDatacenterId));
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	public long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(
					String.format(
							"Clock moved backwards.  Refusing to generate id for %d milliseconds",
							lastTimestamp - timestamp));
		}
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}
		lastTimestamp = timestamp;
		return ((timestamp - twepoch) << timestampLeftShift)
				| (datacenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;
	}

	protected long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	protected long timeGen() {
		return System.currentTimeMillis();
	}
}
