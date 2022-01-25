package com.gitee.fubluesky.vea.system.user.service;

import com.gitee.fubluesky.kernel.auth.api.UserServiceApi;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginResponse;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.IBaseService;
import com.gitee.fubluesky.vea.system.api.domain.User;
import com.gitee.fubluesky.vea.system.user.pojo.param.LoginParam;

import java.util.Set;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface IUserService extends IBaseService<User>, UserServiceApi {

	/**
	 * 用户登录，获取token
	 * @param loginParam 登录参数
	 * @return token
	 */
	LoginResponse login(LoginParam loginParam);

	/**
	 * 通过用户名获取
	 * @param username 用户名
	 * @return 用户
	 */
	User getByUserName(String username);

	/**
	 * 通过手机号获取
	 * @param mobile 手机号
	 * @return 用户
	 */
	User getByMobile(String mobile);

	/**
	 * 获取用户角色列表
	 * @param userId 用户id
	 * @return 角色列表
	 */
	Set<String> getUserRole(Long userId);

	/**
	 * 退出登录
	 */
	void logout();

	/**
	 * 重置用户密码
	 * @param userId 用户id
	 */
	void resetPassword(Long userId);

	/**
	 * 校验手机号时候存在
	 * @param userId 用户id
	 * @param mobile 手机号
	 */
	void checkMobile(Long userId, String mobile);

	/**
	 * 修改
	 * @param user 查询条件
	 */
	void updatePart(User user);

	/**
	 * 修改密码
	 * @param userId 用户id
	 * @param password 密码
	 * @param newPassword 新密码
	 */
	void updatePassword(Long userId, String password, String newPassword);

}
