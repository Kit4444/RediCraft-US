package at.mlps.rc.api;

import java.lang.management.ManagementFactory;

public class CPUStats {
	
	private final long threadId;
	private long lastCpuTime = 0;
	private long lastPoll = 0;
	
	public CPUStats(long threadId) {
		this.threadId = threadId;
		lastCpuTime = getTotalTime();
		lastPoll = System.nanoTime();
	}
	
	public CPUStats() {
		threadId = -1;
		lastCpuTime = getTotalTime();
		lastPoll = System.nanoTime();
	}
	
	private long getRelativeTime() {
		long currentCpuTime = getTotalTime();
		long ret = currentCpuTime - lastCpuTime;
		lastCpuTime = currentCpuTime;
		return ret;
	}
	
	public double getUsage() {
		long timeBefore = this.lastPoll;
		lastPoll = System.nanoTime();
		long relTime = getRelativeTime();
		return Math.max((double)relTime / (double)(lastPoll - timeBefore), 0.0);
	}
	
	private long getTotalTime() {
		if(threadId == -1) {
			long cpuTime = 0;
			for(long id : ManagementFactory.getThreadMXBean().getAllThreadIds()) {
				cpuTime += ManagementFactory.getThreadMXBean().getThreadCpuTime(id);
			}
			return cpuTime;
		}else {
			return ManagementFactory.getThreadMXBean().getThreadCpuTime(threadId);
		}
	}
}