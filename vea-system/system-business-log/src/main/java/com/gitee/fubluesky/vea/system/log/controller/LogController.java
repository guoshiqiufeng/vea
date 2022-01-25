package com.gitee.fubluesky.vea.system.log.controller;

import com.gitee.fubluesky.kernel.core.pojo.ResponseResult;
import com.gitee.fubluesky.kernel.core.util.ListUtils;
import com.gitee.fubluesky.kernel.db.api.pojo.page.PageResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseRestController;
import com.gitee.fubluesky.vea.system.api.domain.LogDomain;
import com.gitee.fubluesky.vea.system.log.constant.LogNameConstant;
import com.gitee.fubluesky.vea.system.log.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author
 * @since 2020-07-06
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class LogController extends BaseRestController<LogDomain, ILogService> {

	/**
	 * 分页
	 */
	@Override
	public ResponseResult page(@ModelAttribute LogDomain domain) {
		PageResult pageResult = service.findPage(domain);
		List<LogDomain> logs = ListUtils.castList(pageResult.getList(), LogDomain.class);
		if (logs != null && logs.size() > 0) {
			for (LogDomain logDomain : logs) {
				if (StringUtils.isNotBlank(logDomain.getModule())) {
					try {
						String module = (String) LogNameConstant.class.getMethod("get" + logDomain.getModule())
								.invoke(new LogNameConstant());
						if (StringUtils.isNotBlank(module)) {
							logDomain.setModule(module);
						}
					}
					catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}
		}
		return ResponseResult.success(pageResult);
	}

}
