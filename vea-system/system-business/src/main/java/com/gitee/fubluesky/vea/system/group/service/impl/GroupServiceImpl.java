package com.gitee.fubluesky.vea.system.group.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gitee.fubluesky.kernel.core.util.ListUtils;
import com.gitee.fubluesky.kernel.db.api.pojo.page.PageResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageResultFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseServiceImpl;
import com.gitee.fubluesky.vea.system.api.GroupServiceApi;
import com.gitee.fubluesky.vea.system.api.RoleServiceApi;
import com.gitee.fubluesky.vea.system.api.domain.Group;
import com.gitee.fubluesky.vea.system.api.domain.GroupMenu;
import com.gitee.fubluesky.vea.system.api.exception.SystemException;
import com.gitee.fubluesky.vea.system.api.exception.enums.SystemExceptionEnum;
import com.gitee.fubluesky.vea.system.group.constants.GroupConstant;
import com.gitee.fubluesky.vea.system.group.mapper.GroupMapper;
import com.gitee.fubluesky.vea.system.group.service.IGroupMenuService;
import com.gitee.fubluesky.vea.system.group.service.IGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户组 服务实现类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@Service
public class GroupServiceImpl extends BaseServiceImpl<GroupMapper, Group> implements IGroupService, GroupServiceApi {

	@Resource
	private IGroupMenuService coreGroupMenuService;

	@Resource
	private RoleServiceApi roleServiceApi;

	/**
	 * 获取组
	 * @param userId 用户id
	 * @param type 组类别
	 * @return 组列表
	 */
	@Override
	public List<Group> findUserGroups(Long userId, int type) {
		return this.baseMapper.findUserGroups(userId, type);
	}

	@Override
	public List<Group> findGroups(Long ownerId, int type) {
		List<Group> groupList = this.list(new QueryWrapper<Group>().eq("owner_id", ownerId).eq("type", "3"));
		if (ListUtils.isEmpty(groupList)) {
			throw new SystemException(SystemExceptionEnum.GROUP_UN_EXIST);
		}
		return groupList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(Group domain) {
		super.save(domain);

		// 保存组菜单
		List<Long> groupMenuIdList = coreGroupMenuService.queryMenuIdList(0L);
		GroupMenu groupMenu = new GroupMenu();
		groupMenu.setGroupId(domain.getId());
		groupMenu.setMenuIdList(groupMenuIdList);
		coreGroupMenuService.saveGroupMenu(groupMenu);

		roleServiceApi.initGroupRoleData(domain.getId());
		return true;
	}

	@Override
	public PageResult findPage(Group domain) {
		LambdaQueryWrapper<Group> wrapper = Wrappers.lambdaQuery();

		wrapper.like(StringUtils.isNotBlank(domain.getName()), Group::getName, domain.getName());

		IPage<Group> page = super.page(PageFactory.defaultPage(), wrapper);
		return PageResultFactory.createPageResult(page);

	}

	@Override
	public Group saveGroup(String name, Long ownerId) {
		// 创建用户组
		Group group = new Group();
		group.setName(name);
		group.setOwnerId(ownerId);
		group.setType(GroupConstant.OTHER);
		group.setCreateDate(LocalDateTime.now());
		this.save(group);
		// 保存组菜单
		List<Long> groupMenuIdList = coreGroupMenuService.queryMenuIdList(0L);
		GroupMenu groupMenu = new GroupMenu();
		groupMenu.setGroupId(group.getId());
		groupMenu.setMenuIdList(groupMenuIdList);
		coreGroupMenuService.saveGroupMenu(groupMenu);
		return group;
	}

}
