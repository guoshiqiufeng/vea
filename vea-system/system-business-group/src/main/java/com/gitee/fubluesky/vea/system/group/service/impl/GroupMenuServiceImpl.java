package com.gitee.fubluesky.vea.system.group.service.impl;

import com.gitee.fubluesky.kernel.core.util.ListUtils;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseServiceImpl;
import com.gitee.fubluesky.vea.system.api.domain.GroupMenu;
import com.gitee.fubluesky.vea.system.group.mapper.GroupMenuMapper;
import com.gitee.fubluesky.vea.system.group.service.IGroupMenuService;
import com.gitee.fubluesky.vea.system.menu.service.IMenuService;
import com.gitee.fubluesky.vea.system.role.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组菜单 服务实现类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@Service
public class GroupMenuServiceImpl extends BaseServiceImpl<GroupMenuMapper, GroupMenu> implements IGroupMenuService {

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IMenuService menuService;

	/**
	 * 获取菜单列表
	 * @param groupId 组id
	 * @return 菜单id数组
	 */
	@Override
	public List<Long> queryMenuIdList(Long groupId) {
		return this.baseMapper.queryMenuIdList(groupId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveGroupMenu(GroupMenu groupMenu) {
		// 查询组菜单
		List<Long> ids = this.queryMenuIdList(groupMenu.getGroupId());
		// 先删除角色与菜单关系
		deleteBatch(new Long[] { groupMenu.getGroupId() });
		// 删除缓存
		menuService.deleteGroupMenuListCache(groupMenu.getGroupId());

		List<Long> menuIdList = groupMenu.getMenuIdList();
		if (groupMenu.getMenuIdList().size() == 0) {
			return true;
		}

		// 保存角色与菜单关系
		List<GroupMenu> saveList = new ArrayList<>();
		for (Long menuId : menuIdList) {
			GroupMenu sysGroupMenuEntity = new GroupMenu();
			sysGroupMenuEntity.setMenuId(menuId);
			sysGroupMenuEntity.setGroupId(groupMenu.getGroupId());
			sysGroupMenuEntity.setCreateDate(LocalDateTime.now());
			sysGroupMenuEntity.setModifyDate(LocalDateTime.now());
			saveList.add(sysGroupMenuEntity);
		}
		this.saveBatch(saveList);

		// 获取已删除菜单
		List<Long> deleteIds = new ArrayList<>();
		for (Long id : ids) {
			boolean deleteFlag = true;
			for (Long menuId : menuIdList) {
				if (menuId.compareTo(id) == 0) {
					deleteFlag = false;
					break;
				}
			}
			if (deleteFlag) {
				deleteIds.add(id);
			}
		}
		if (ListUtils.isNotEmpty(deleteIds)) {
			// 删除组下角色菜单关联关系
			roleService.deleteRoleMenuData(groupMenu.getGroupId(), deleteIds);
		}
		return true;
	}

	@Transactional(rollbackFor = Exception.class)
	public int deleteBatch(Long[] groupId) {
		return this.baseMapper.deleteBatch(groupId);
	}

}
