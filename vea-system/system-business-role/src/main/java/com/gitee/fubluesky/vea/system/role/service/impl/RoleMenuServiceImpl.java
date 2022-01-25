package com.gitee.fubluesky.vea.system.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseServiceImpl;
import com.gitee.fubluesky.vea.system.api.domain.RoleMenu;
import com.gitee.fubluesky.vea.system.role.mapper.RoleMenuMapper;
import com.gitee.fubluesky.vea.system.role.service.IRoleMenuService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务实现类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

	/**
	 * 根据角色id 获取菜单ID列表
	 * @param id 角色id
	 * @return 菜单ID列表
	 */
	@Override
	public List<Long> queryMenuIdList(Long id) {
		return this.baseMapper.queryMenuIdList(id);
	}

	/**
	 * 保存/修改
	 * @param roleId 角色id
	 * @param menuIdList 菜单ID列表
	 */
	@Override
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		// 先删除角色与菜单关系
		deleteBatch(new Long[] { roleId });

		if (menuIdList.size() == 0) {
			return;
		}

		// 保存角色与菜单关系
		List<RoleMenu> addList = Lists.newArrayList();
		for (Long menuId : menuIdList) {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setMenuId(menuId);
			roleMenu.setRoleId(roleId);

			addList.add(roleMenu);
		}

		this.saveBatch(addList);
	}

	/**
	 * 根据角色ID数组，批量删除
	 * @param roleIds 角色ID数组
	 * @return {@code Boolean} 是否成功
	 */
	@Override
	public Boolean deleteBatch(Long[] roleIds) {
		if (roleIds == null || roleIds.length == 0) {
			return false;
		}
		return this.remove(new QueryWrapper<RoleMenu>().in("role_id", (Object[]) roleIds));
	}

	/**
	 * 删除角色下菜单
	 * @param roleId 角色ID
	 * @param menuIds 菜单ID列表
	 */
	@Override
	public void removeRoleMenu(Long roleId, List<Long> menuIds) {
		this.remove(new QueryWrapper<RoleMenu>().eq("role_id", roleId).in("menu_id", menuIds));
	}

}
