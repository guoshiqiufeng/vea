package com.gitee.fubluesky.vea.system.user.controller;

import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginResponse;
import com.gitee.fubluesky.kernel.core.pojo.ResponseResult;
import com.gitee.fubluesky.vea.system.api.domain.Group;
import com.gitee.fubluesky.vea.system.group.service.IGroupService;
import com.gitee.fubluesky.vea.system.user.pojo.param.LoginParam;
import com.gitee.fubluesky.vea.system.user.pojo.vo.AppLoginVO;
import com.gitee.fubluesky.vea.system.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 登录
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-08-20 10:35
 */
@Api(tags = "登录")
@RequiredArgsConstructor
@RestController
public class LoginController {

	private final IUserService userService;

	private final IGroupService groupService;

	/**
	 * 登录
	 */
	@ApiOperation("登录")
	@PostMapping("/login")
	public ResponseResult login(@Valid @RequestBody LoginParam loginParam) throws IOException {
		LoginResponse response = userService.login(loginParam);

		return ResponseResult.success("登录成功", response.getToken());
	}

	/**
	 * 登录
	 */
	@PostMapping("/loginForApp")
	public ResponseResult loginForApp(@Valid @RequestBody LoginParam loginParam, HttpServletRequest request) {
		// 用户信息
		AppLoginVO result = new AppLoginVO();

		LoginResponse response = userService.login(loginParam);

		result.setToken(response.getToken());
		result.setId(response.getLoginUser().getUserId());
		result.setUserName(response.getLoginUser().getAccount());
		result.setNickName(response.getLoginUser().getNickname());
		result.setUserType(response.getLoginUser().getUserType());
		result.setUserIcon(response.getLoginUser().getUserIcon());

		// 查询用户学校信息
		List<Group> list = groupService.findUserGroups(response.getLoginUser().getUserId(), 3);
		if (list != null && list.size() > 0) {
			result.setSchoolId(list.get(0).getOwnerId());
			result.setSchoolName(list.get(0).getName());
		}

		return ResponseResult.success("登录成功", result);
	}

	/**
	 * 退出
	 */
	@ApiOperation("退出登录")
	@PostMapping("/logout")
	public ResponseResult logout() {
		userService.logout();
		return ResponseResult.success();
	}

}
