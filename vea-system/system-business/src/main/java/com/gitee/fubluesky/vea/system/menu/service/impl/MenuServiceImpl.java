package com.gitee.fubluesky.vea.system.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.auth.api.UserServiceApi;
import com.gitee.fubluesky.kernel.core.util.ListUtils;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseServiceImpl;
import com.gitee.fubluesky.vea.system.api.constants.SystemConstants;
import com.gitee.fubluesky.vea.system.api.domain.Menu;
import com.gitee.fubluesky.vea.system.api.enums.MenuTypeEnum;
import com.gitee.fubluesky.vea.system.api.exception.SystemException;
import com.gitee.fubluesky.vea.system.api.exception.enums.SystemExceptionEnum;
import com.gitee.fubluesky.vea.system.menu.cache.GroupMenuRedisCache;
import com.gitee.fubluesky.vea.system.menu.cache.MenuNavRedisCache;
import com.gitee.fubluesky.vea.system.menu.mapper.MenuMapper;
import com.gitee.fubluesky.vea.system.menu.service.IMenuService;
import com.gitee.fubluesky.vea.system.menu.vo.MenuNavVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

	@Autowired
	private GroupMenuRedisCache groupMenuRedisCache;

	@Autowired
	private MenuNavRedisCache menuNavRedisCache;

	@Autowired
	private LoginApi loginApi;

	@Autowired
	private UserServiceApi userServiceApi;

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @return 菜单列表
	 */
	@Override
	public List<Menu> queryListParentId(Long parentId) {
		return super.list(new QueryWrapper<Menu>().eq("parent_id", parentId).orderByAsc("order_num"));
	}

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList 用户菜单ID
	 * @return 菜单列表
	 */
	@Override
	public List<Menu> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<Menu> menuList = queryListParentId(parentId);
		if (menuIdList == null) {
			return menuList.stream().filter(menu -> menu.getType().compareTo(3) != 0).collect(Collectors.toList());
		}

		List<Menu> userMenuList = new ArrayList<>();
		for (Menu menu : menuList) {
			if (menu.getType().compareTo(3) != 0 && menuIdList.contains(menu.getMenuId())) {
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	/**
	 * 获取用户菜单
	 * @param userId 用户id
	 * @return 菜单列表
	 */
	@Override
	public List<Menu> getUserMenuList(Long userId) {
		// 系统管理员，拥有最高权限
		if (SystemConstants.SUPER_ADMIN_ID.equals(userId)) {
			return getAllMenuList(null);
		}

		// 用户菜单列表
		List<Long> menuIdList = baseMapper.queryUserAllMenuIdList(userId);
		return getAllMenuList(menuIdList);
	}

	/**
	 * 获取不包含按钮的菜单列表
	 * @return 菜单列表
	 */
	@Override
	public List<Menu> queryNotButtonList() {
		QueryWrapper<Menu> queryWrapper = Wrappers.query();
		queryWrapper.ne("type", "2");
		queryWrapper.orderByAsc("order_num");
		return this.list(queryWrapper);
	}

	/**
	 * 获取组菜单列表
	 * @param groupId 组id
	 * @return 菜单列表
	 */
	@Override
	public List<Menu> queryMenuList(Long groupId) {
		return this.baseMapper.queryMenuList(groupId);
	}

	/**
	 * 获取组菜单列表
	 * @param groupId 组id
	 * @return 菜单列表
	 */
	@Override
	public List<Menu> getGroupMenuList(Long groupId) {
		List<Menu> menuList = groupMenuRedisCache.get(groupId + "");
		if (ListUtils.isEmpty(menuList)) {
			menuList = this.queryMenuList(groupId);
			groupMenuRedisCache.add(groupId + "", menuList);
		}
		return menuList;
	}

	/**
	 * 获取导航数据
	 * @return 导航数据
	 */
	@Override
	public MenuNavVO getNavData() {
		Long userId = loginApi.getLoginUser().getUserId();

		MenuNavVO result = menuNavRedisCache.get(userId + "");
		if (result != null) {
			return result;
		}
		result = new MenuNavVO();
		List<Menu> menuList = this.getUserMenuList(userId);
		Set<String> permissions = userServiceApi.getUserPermissions(userId);
		result.setMenuList(menuList);
		result.setPermissions(permissions);

		menuNavRedisCache.add(userId + "", result);

		return result;
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<Menu> getAllMenuList(List<Long> menuIdList) {
		// 查询根菜单列表
		List<Menu> menuList = queryListParentId(0L, menuIdList);
		// 递归获取子菜单
		getMenuTreeList(menuList, menuIdList);

		return menuList;
	}

	/**
	 * 递归
	 */
	private List<Menu> getMenuTreeList(List<Menu> menuList, List<Long> menuIdList) {
		List<Menu> subMenuList = new ArrayList<>();

		for (Menu entity : menuList) {
			// 目录
			if (entity.getType() == MenuTypeEnum.CATALOG.getValue()) {
				entity.setChildren(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}

		return subMenuList;
	}

	@Override
	public boolean update(Menu domain) {
		try {
			if (checkId(domain.getMenuId())) {
				return this.updateById(domain);
			}
			return false;
		}
		catch (Exception e) {
			log.error("menu update error:", e);
			throw new SystemException(SystemExceptionEnum.MENU_UPDATE_ERROR);
		}
	}

	/**
	 * 获取所有权限列表
	 * @return 权限列表
	 */
	@Override
	public List<String> queryAllPerm() {
		QueryWrapper<Menu> queryWrapper = Wrappers.query();
		queryWrapper.select("perms");

		List<Menu> list = this.list(queryWrapper);

		return list.stream().filter(menu -> menu != null && StringUtils.isNotEmpty(menu.getPerms()))
				.collect(Collectors.toList()).stream().map(Menu::getPerms).collect(Collectors.toList());
	}

	/**
	 * 获取用户所有权限列表
	 * @param userId 用户id
	 * @return 权限列表
	 */
	@Override
	public List<String> queryUserAllPerm(Long userId) {
		return baseMapper.queryUserAllPerms(userId);
	}

	/**
	 * 查询用户的所有菜单ID
	 * @param userId 用户id
	 * @return 菜单id数组
	 */
	@Override
	public List<Long> queryUserAllMenuId(Long userId) {
		return baseMapper.queryUserAllMenuId(userId);
	}

	/**
	 * 删除组菜单缓存
	 * @param groupId 组id
	 */
	@Override
	public void deleteGroupMenuListCache(Long groupId) {
		groupMenuRedisCache.delete(groupId + "");
	}

	/**
	 * 删除菜单导航缓存
	 */
	@Override
	public void deleteMenuNavCache() {
		Long userId = loginApi.getLoginUser().getUserId();
		menuNavRedisCache.delete(userId + "");
	}

}
