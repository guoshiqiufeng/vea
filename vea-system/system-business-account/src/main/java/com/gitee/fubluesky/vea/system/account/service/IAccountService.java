package com.gitee.fubluesky.vea.system.account.service;

import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.IBaseService;
import com.gitee.fubluesky.vea.system.api.domain.Account;

/**
 * <p>
 * 账号库 服务类
 * </p>
 *
 * @author yanghq
 * @since 2021-07-01
 */
public interface IAccountService extends IBaseService<Account> {

	/**
	 * 通过用户名获取
	 * @param userName 用户名
	 * @return {@link Account} 账号
	 */
	Account findByUserName(String userName);

	/**
	 * 创建账号
	 * @param count 账号个数
	 */
	void creator(Long count);

	/**
	 * 创建获取新账号
	 * @return
	 */
	Account getNewAccount();

}
