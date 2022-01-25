package com.gitee.fubluesky.vea.system.api;

import com.gitee.fubluesky.vea.system.api.domain.User;
import com.gitee.fubluesky.vea.system.api.enums.RoleEnum;
import com.gitee.fubluesky.vea.system.api.enums.UserStatusEnum;

import java.util.List;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-10-26 15:24
 */
public interface UserServiceApi {

	/**
	 * 获取角色下 用户列表
	 * @param id ownerId
	 * @param roleCode 角色编码
	 * @return 用户列表
	 */
	List<User> findUserList(Long id, String roleCode);

	/**
	 * 创建用户 角色默认为教师
	 * @param ownerId 第三方id
	 * @param userId 用户id
	 * @param mobile 手机号
	 * @param name 姓名
	 * @param userIcon 用户头像
	 * @return 用户
	 */
	User createUser(Long ownerId, Long userId, String mobile, String name, String userIcon);

	/**
	 * 创建用户
	 * @param ownerId 第三方id
	 * @param userId 用户id
	 * @param role 角色
	 * @param mobile 手机号
	 * @param name 姓名
	 * @param userIcon 用户头像
	 * @return 用户
	 */
	User createUser(Long ownerId, Long userId, RoleEnum role, String mobile, String name, String userIcon);

	/**
	 * 更新用户信息
	 * @param userId 用户id
	 * @param mobile 手机号
	 * @param statusEnum 状态
	 * @param nickName 昵称
	 * @param userIcon 用户头像
	 */
	void updateUserData(Long userId, String mobile, UserStatusEnum statusEnum, String nickName, String userIcon);

	/**
	 * 更新用户状态
	 * @param userId 用户id
	 * @param statusEnum 状态
	 */
	void updateUserStatus(Long userId, UserStatusEnum statusEnum);

	/**
	 * 获取用户头像
	 * @param userIcon 用户id
	 * @return 用户头像
	 */
	String getUserIcon(String userIcon);

	/**
	 * 获取用户头像
	 * @param userId 用户id
	 * @return 用户头像
	 */
	String getUserIcon(Long userId);

}
