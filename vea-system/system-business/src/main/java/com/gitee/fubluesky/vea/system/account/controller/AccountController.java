package com.gitee.fubluesky.vea.system.account.controller;

import com.gitee.fubluesky.kernel.core.pojo.ResponseResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseRestController;
import com.gitee.fubluesky.vea.system.account.service.IAccountService;
import com.gitee.fubluesky.vea.system.api.domain.Account;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 账号库 前端控制器
 * </p>
 *
 * @author
 * @since 2020-07-01
 */
@RestController
@RequestMapping("/system/account")
public class AccountController extends BaseRestController<Account, IAccountService> {

	/**
	 * 创建
	 */
	@PostMapping("/creator")
	public ResponseResult creator(@RequestBody Long count) {
		service.creator(count);
		return ResponseResult.success();
	}

}
