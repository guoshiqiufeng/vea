package com.gitee.fubluesky.vea.server.system.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 多线程配置
 * </p>
 *
 * @author
 * @since 2020-07-06
 */
@Configuration
@EnableAsync
@Slf4j
public class ThreadPoolTaskExecutorConfig {

	/**
	 * 核心线程数（默认线程数）
	 */
	@Value("${thread-pool.core-pool-size: 10}")
	private int corePoolSize;

	/**
	 * 最大线程数
	 */
	@Value("${thread-pool.max-pool-size: 100}")
	private int maxPoolSize;

	/**
	 * 允许线程空闲时间（单位：默认为秒）
	 */
	@Value("${thread-pool.keep-alive-time: 10}")
	private int keepAliveTime;

	/**
	 * 缓冲队列数
	 */
	@Value("${thread-pool.queue-capacity: 200}")
	private int queueCapacity;

	/**
	 * @return ThreadPoolTaskExecutor
	 * @author jinhaoxun
	 * @description 线程池配置，bean的名称，默认为首字母小写的方法名taskExecutor
	 */
	@Bean("pushTaskExecutor")
	public Executor pushExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		executor.setCorePoolSize(corePoolSize);
		// 设置最大线程数
		executor.setMaxPoolSize(maxPoolSize);
		// 线程池所使用的缓冲队列
		executor.setQueueCapacity(queueCapacity);
		// 等待任务在关机时完成--表明等待所有线程执行完
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
		executor.setKeepAliveSeconds(keepAliveTime);
		// 线程名称前缀
		executor.setThreadNamePrefix("push-executor-");
		// 线程池对拒绝任务的处理策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 初始化
		executor.initialize();
		return executor;
	}

	@Bean("logTaskExecutor")
	public Executor logExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		executor.setCorePoolSize(corePoolSize);
		// 设置最大线程数
		executor.setMaxPoolSize(maxPoolSize);
		// 线程池所使用的缓冲队列
		executor.setQueueCapacity(queueCapacity);
		// 等待任务在关机时完成--表明等待所有线程执行完
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
		executor.setKeepAliveSeconds(keepAliveTime);
		// 线程名称前缀
		executor.setThreadNamePrefix("log-executor-");
		// 线程池对拒绝任务的处理策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 初始化
		executor.initialize();
		return executor;
	}

	@Bean("otherExecutor")
	public Executor otherExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		executor.setCorePoolSize(corePoolSize);
		// 设置最大线程数
		executor.setMaxPoolSize(maxPoolSize);
		// 线程池所使用的缓冲队列
		executor.setQueueCapacity(queueCapacity);
		// 等待任务在关机时完成--表明等待所有线程执行完
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
		executor.setKeepAliveSeconds(keepAliveTime);
		// 线程名称前缀
		executor.setThreadNamePrefix("other-executor-");
		// 线程池对拒绝任务的处理策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 初始化
		executor.initialize();
		return executor;
	}

}
