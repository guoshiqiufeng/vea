package com.gitee.fubluesky.vea.system.log.thread;

import com.gitee.fubluesky.kernel.auth.utils.SpringContextUtils;
import com.gitee.fubluesky.vea.system.api.domain.LogDomain;
import com.gitee.fubluesky.vea.system.log.service.ILogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author yanghq
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogThread {

	@Async("logTaskExecutor")
	public void run(LogDomain logDomainEntity) {
		if (logDomainEntity != null) {
			try {
				ILogService logService = SpringContextUtils.getBean(ILogService.class);
				logService.save(logDomainEntity);
			}
			catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

}
