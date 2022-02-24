package com.gitee.fubluesky.vea.system.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.core.util.ListUtils;
import com.gitee.fubluesky.kernel.db.api.pojo.page.PageResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageResultFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseServiceImpl;
import com.gitee.fubluesky.vea.system.api.RoleServiceApi;
import com.gitee.fubluesky.vea.system.api.constants.SystemConstants;
import com.gitee.fubluesky.vea.system.api.domain.Role;
import com.gitee.fubluesky.vea.system.api.enums.RoleEnum;
import com.gitee.fubluesky.vea.system.api.exception.SystemException;
import com.gitee.fubluesky.vea.system.api.exception.enums.SystemExceptionEnum;
import com.gitee.fubluesky.vea.system.menu.service.IMenuService;
import com.gitee.fubluesky.vea.system.role.mapper.RoleMapper;
import com.gitee.fubluesky.vea.system.role.service.IRoleMenuService;
import com.gitee.fubluesky.vea.system.role.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService, RoleServiceApi {

	@Autowired
	private IRoleMenuService roleMenuService;

	@Autowired
	private IMenuService menuService;

	@Autowired
	private LoginApi loginApi;

	/**
	 * 获取用户角色列表
	 * @param userId
	 */
	@Override
	public List<Role> findListByUser(Long userId) {
		return this.baseMapper.findRoleList(
				new QueryWrapper<Role>().eq(userId != null, "cr.create_user_id", userId).orderByAsc("group_id"));
	}

	/**
	 * 获取部门角色列表
	 * @param groupId
	 * @param type
	 */
	@Override
	public List<Role> findRoleList(Long groupId, Integer type) {
		return this.baseMapper.findRoleList(
				new QueryWrapper<Role>().eq(groupId != null, "cg.owner_id", groupId).eq(type != null, "cg.type", type));
	}

	/**
	 * 获取用户角色列表
	 * @param groupId 组id
	 * @return 角色列表
	 */
	@Override
	public List<Role> findListByGroupId(Long groupId) {
		return this.baseMapper.findRoleList(new QueryWrapper<Role>().eq("cg.id", groupId).orderByAsc("group_id"));
	}

	@Override
	public List<Role> findGroupRoles(Long groupId, RoleEnum roleEnum) {
		List<Role> roleList = this
				.list(new QueryWrapper<Role>().eq("group_id", groupId).eq("code", roleEnum.getCode()));
		if (ListUtils.isEmpty(roleList)) {
			String message = MessageFormat.format(SystemExceptionEnum.ROLE_UN_EXIST.getMessage(), roleEnum.getName());
			throw new SystemException(SystemExceptionEnum.ROLE_UN_EXIST, message);
		}
		return roleList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(Role domain) {
		try {
			super.save(domain);

			// 检查权限是否越权
			checkPrem(domain);

			// 更新角色与菜单关系
			roleMenuService.saveOrUpdate(domain.getRoleId(), domain.getMenuIdList());

			return true;
		}
		catch (Exception e) {
			log.error("role save error.", e);
			throw new SystemException(SystemExceptionEnum.ROLE_SAVE_ERROR);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean update(Role domain) {
		try {
			if (checkId(domain.getRoleId())) {
				super.updateById(domain);

				// 检查权限是否越权
				checkPrem(domain);

				// 更新角色与菜单关系
				roleMenuService.saveOrUpdate(domain.getRoleId(), domain.getMenuIdList());

				return true;
			}
			return false;
		}
		catch (Exception e) {
			log.error("role update error.", e);
			throw new SystemException(SystemExceptionEnum.ROLE_UPDATE_ERROR);
		}
	}

	@Override
	public PageResult findPage(Role domain) {
		QueryWrapper<Role> queryWrapper = Wrappers.query(domain);

		queryWrapper.like(StringUtils.isNotBlank(domain.getName()), "cr.name", domain.getName());
		queryWrapper.eq(domain.getGroupId() != null, "cr.group_id", domain.getGroupId());
		queryWrapper.eq(domain.getCreateUserId() != null, "cr.create_user_id", domain.getCreateUserId());

		IPage<Role> page = baseMapper.queryPage(PageFactory.defaultPage(), queryWrapper);
		return PageResultFactory.createPageResult(page);
	}

	/**
	 * 检查权限是否越权
	 */
	private void checkPrem(Role role) {
		// 如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if (SystemConstants.SUPER_ADMIN_ID.equals(role.getCreateUserId())) {
			return;
		}

		// 查询用户所拥有的菜单列表
		List<Long> menuIdList = menuService.queryUserAllMenuId(role.getCreateUserId());

		// 判断是否越权
		if (!menuIdList.containsAll(role.getMenuIdList())) {
			throw new SystemException(SystemExceptionEnum.ROLE_PERMISSION_OUT);
		}
	}

	/**
	 * 初始化组角色数据
	 * @param groupId 组id
	 */
	@Override
	public void initGroupRoleData(Long groupId) {
		// 获取角色
		List<Role> roleList = this.list(new QueryWrapper<Role>().eq("group_id", 0));
		for (Role role : roleList) {
			// 获取角色菜单
			List<Long> menuIdList = roleMenuService.queryMenuIdList(role.getRoleId());
			// 创建角色
			Role newRole = new Role();
			newRole.setCode(role.getCode());
			newRole.setGroupId(groupId);
			newRole.setName(role.getName());
			newRole.setRemark(role.getName());
			newRole.setMenuIdList(menuIdList);
			newRole.setCreateUserId(loginApi.getLoginUser().getUserId());
			newRole.setUserType(role.getUserType());
			this.save(newRole);
		}
	}

	/**
	 * 删除组下组 角色菜单数据
	 * @param groupId 组id
	 */
	@Override
	public void deleteRoleMenuData(Long groupId, List<Long> menuIds) {
		List<Role> roleList = this.list(new QueryWrapper<Role>().eq("group_id", groupId));
		for (Role sysRoleEntity : roleList) {
			roleMenuService.removeRoleMenu(sysRoleEntity.getRoleId(), menuIds);
		}
	}

}
