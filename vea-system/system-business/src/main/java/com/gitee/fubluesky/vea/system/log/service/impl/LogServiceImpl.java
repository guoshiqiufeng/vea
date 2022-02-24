package com.gitee.fubluesky.vea.system.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.fubluesky.kernel.db.api.pojo.page.PageResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageResultFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseServiceImpl;
import com.gitee.fubluesky.vea.system.api.domain.LogDomain;
import com.gitee.fubluesky.vea.system.log.mapper.LogMapper;
import com.gitee.fubluesky.vea.system.log.service.ILogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-06
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, LogDomain> implements ILogService {

	@Override
	public PageResult findPage(LogDomain domain) {
		LambdaQueryWrapper<LogDomain> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(domain.getSchoolId() != null, LogDomain::getSchoolId, domain.getSchoolId());
		wrapper.like(StringUtils.isNotBlank(domain.getUsername()), LogDomain::getUsername, domain.getUsername());
		wrapper.like(StringUtils.isNotBlank(domain.getNickname()), LogDomain::getNickname, domain.getNickname());
		wrapper.eq(domain.getType() != null, LogDomain::getType, domain.getType());
		wrapper.ge(domain.getStartTime() != null, LogDomain::getCreateDate, domain.getStartTime());
		wrapper.le(domain.getEndTime() != null, LogDomain::getCreateDate, domain.getEndTime());
		Page<LogDomain> page = this.page(PageFactory.defaultPage(), wrapper);

		return PageResultFactory.createPageResult(page);
	}

}
