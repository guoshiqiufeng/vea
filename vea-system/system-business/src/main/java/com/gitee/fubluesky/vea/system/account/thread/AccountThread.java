package com.gitee.fubluesky.vea.system.account.thread;

import com.gitee.fubluesky.kernel.auth.utils.SpringContextUtils;
import com.gitee.fubluesky.vea.system.account.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author yanghq
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class AccountThread {

	@Async("otherExecutor")
	public void run(Long num) {
		if (num != null) {
			try {
				IAccountService accountService = SpringContextUtils.getBean(IAccountService.class);
				accountService.creator(num);
			}
			catch (Exception e) {
				log.error("" + e.getMessage());
			}
		}
	}

}
