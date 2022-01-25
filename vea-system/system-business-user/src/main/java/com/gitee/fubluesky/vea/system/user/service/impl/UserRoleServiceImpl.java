package com.gitee.fubluesky.vea.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gitee.fubluesky.kernel.core.util.ListUtils;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseServiceImpl;
import com.gitee.fubluesky.vea.system.user.service.IUserRoleService;
import com.google.common.collect.Maps;
import com.gitee.fubluesky.vea.system.api.domain.UserRole;
import com.gitee.fubluesky.vea.system.user.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		// 先删除用户与角色关系
		this.remove(new QueryWrapper<UserRole>().eq("user_id", userId));

		if (ListUtils.isEmpty(roleIdList)) {
			return;
		}

		// 保存用户与角色关系
		for (Long roleId : roleIdList) {
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);

			this.save(userRole);
		}
	}

	/**
	 * 根据用户ID，获取角色ID列表
	 * @param userId 用户id
	 * @return 角色id列表
	 */
	@Override
	public List<Long> findRoleIdList(Long userId) {
		return this.baseMapper.findRoleIdList(userId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeUserRole(Long userId, List<Long> roleIdList) {
		if (ListUtils.isEmpty(roleIdList)) {
			return;
		}

		for (Long roleId : roleIdList) {
			HashMap<String, Object> map = Maps.newHashMap();
			map.put("user_id", userId);
			map.put("role_id", roleId);
			this.removeByMap(map);
		}
	}

	/**
	 * 保存用户角色
	 * @param userId 用户id
	 * @param roleIdList 角色id列表
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUserRole(Long userId, List<Long> roleIdList) {
		if (ListUtils.isEmpty(roleIdList)) {
			return;
		}

		// 保存用户与角色关系
		for (Long roleId : roleIdList) {
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);

			this.save(userRole);
		}
	}

	/**
	 * 查询用户类型
	 * @param userId 用户id
	 * @return 用户类型列表
	 */
	@Override
	public List<String> findRoleTypesByUserId(Long userId) {
		return baseMapper.findRoleTypesByUserId(userId);
	}

}
