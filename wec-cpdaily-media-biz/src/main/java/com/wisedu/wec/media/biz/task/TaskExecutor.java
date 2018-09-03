package com.wisedu.wec.media.biz.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程任务执行器
 * @author mdmo
 *
 */
public class TaskExecutor {
	private static final ExecutorService threadpool = Executors.newFixedThreadPool(10);
	public static void execute(Runnable runnable){
		threadpool.execute(runnable);
	}
}
