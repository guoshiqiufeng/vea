package com.gitee.fubluesky.vea.system.department.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gitee.fubluesky.kernel.db.api.pojo.page.PageResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageResultFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseServiceImpl;
import com.gitee.fubluesky.vea.system.account.service.IAccountService;
import com.gitee.fubluesky.vea.system.api.domain.Account;
import com.gitee.fubluesky.vea.system.api.domain.DepartmentUser;
import com.gitee.fubluesky.vea.system.api.domain.User;
import com.gitee.fubluesky.vea.system.department.mapper.DepartmentUserMapper;
import com.gitee.fubluesky.vea.system.department.service.IDepartmentUserService;
import com.gitee.fubluesky.vea.system.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 部门用户表 服务实现类
 * </p>
 *
 * @author
 * @since 2020-07-06
 */
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentUserServiceImpl extends BaseServiceImpl<DepartmentUserMapper, DepartmentUser>
		implements IDepartmentUserService {

	private final IAccountService accountService;

	private final IUserService coreUserService;

	@Override
	public boolean save(DepartmentUser domain) {
		// 获取账号库用户
		Account coreAccount = accountService.getNewAccount();

		domain.setUserName(coreAccount.getUserName());

		// 校验手机号是否存在
		if (StringUtils.isNotBlank(domain.getMobile())) {
			coreUserService.checkMobile(null, domain.getMobile());
		}

		User user = new User();
		BeanUtils.copyProperties(domain, user);
		user.setUsername(domain.getUserName());
		user.setNickname(domain.getUserNickName());
		coreUserService.save(user);

		domain.setUserId(user.getUserId());
		domain.setIsAdmin(false);
		return super.save(domain);
	}

	@Override
	public boolean update(DepartmentUser domain) {
		// 校验手机号是否存在
		if (StringUtils.isNotBlank(domain.getMobile())) {
			coreUserService.checkMobile(domain.getUserId(), domain.getMobile());
		}
		User user = new User();
		BeanUtils.copyProperties(domain, user);
		user.setUsername(domain.getUserName());
		user.setNickname(domain.getUserNickName());
		return coreUserService.update(user);
	}

	@Override
	public PageResult findPage(DepartmentUser domain) {
		QueryWrapper<DepartmentUser> wrapper = new QueryWrapper<>();
		wrapper.like(StringUtils.isNotBlank(domain.getUserName()), "cu.username", domain.getUserName());
		wrapper.eq(domain.getDepartmentId() != null, "cdu.department_id", domain.getDepartmentId());
		IPage<DepartmentUser> page = this.baseMapper.findPage(PageFactory.defaultPage(), wrapper);
		return PageResultFactory.createPageResult(page);
	}

}
