package com.gitee.fubluesky.vea.system.department.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.fubluesky.kernel.db.api.pojo.page.PageResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageResultFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseServiceImpl;
import com.gitee.fubluesky.vea.system.api.domain.Department;
import com.gitee.fubluesky.vea.system.api.domain.Group;
import com.gitee.fubluesky.vea.system.department.mapper.DepartmentMapper;
import com.gitee.fubluesky.vea.system.department.service.IDepartmentService;
import com.gitee.fubluesky.vea.system.group.constants.GroupConstant;
import com.gitee.fubluesky.vea.system.group.service.IGroupService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author
 * @since 2020-07-06
 */
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

	private final IGroupService coreGroupService;

	@Override
	public boolean save(Department domain) {
		boolean flag = super.save(domain);

		if (flag) {
			// 创建组
			Group coreGroup = new Group();
			coreGroup.setOwnerId(domain.getId());
			coreGroup.setName(domain.getName());
			coreGroup.setType(GroupConstant.GROUP_TYPE_ORGANIZATION);
			return coreGroupService.save(coreGroup);
		}

		return false;
	}

	@Override
	public PageResult findPage(Department domain) {
		LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(StringUtils.isNotBlank(domain.getName()), Department::getName, domain.getName());

		Page<Department> page = this.page(PageFactory.defaultPage(), wrapper);
		return PageResultFactory.createPageResult(page);
	}

	/**
	 * 判断当前用户是否为部门用户
	 * @param userId
	 * @return
	 */
	@Override
	public Department getUserDepartment(Long userId) {
		List<Department> list = baseMapper.findDepartment(new QueryWrapper<Department>().eq("cu.user_id", userId));
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
