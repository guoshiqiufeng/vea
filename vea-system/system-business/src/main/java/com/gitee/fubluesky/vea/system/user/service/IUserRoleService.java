package com.gitee.fubluesky.vea.system.user.service;

import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.IBaseService;
import com.gitee.fubluesky.vea.system.api.domain.UserRole;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface IUserRoleService extends IBaseService<UserRole> {

	/**
	 * 保存/修改
	 * @param userId 用户id
	 * @param roleIdList 角色id列表
	 */
	void saveOrUpdate(Long userId, List<Long> roleIdList);

	/**
	 * 根据用户ID，获取角色ID列表
	 * @param userId 用户id
	 * @return 角色id列表
	 */
	List<Long> findRoleIdList(Long userId);

	/**
	 * 删除用户角色
	 * @param userId 用户id
	 * @param roleIdList 角色id列表
	 */
	void removeUserRole(Long userId, List<Long> roleIdList);

	/**
	 * 保存用户角色
	 * @param userId 用户id
	 * @param roleIdList 角色id列表
	 */
	void saveUserRole(Long userId, List<Long> roleIdList);

	/**
	 * 查询用户类型
	 * @param userId 用户id
	 * @return 用户类型列表
	 */
	List<String> findRoleTypesByUserId(Long userId);

}
