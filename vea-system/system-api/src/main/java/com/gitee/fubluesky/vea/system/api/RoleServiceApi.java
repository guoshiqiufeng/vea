package com.gitee.fubluesky.vea.system.api;

/**
 * 角色
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-08-14 14:46
 */
public interface RoleServiceApi {

	/**
	 * 初始化组角色数据
	 * @param groupId 组id
	 */
	void initGroupRoleData(Long groupId);

}
